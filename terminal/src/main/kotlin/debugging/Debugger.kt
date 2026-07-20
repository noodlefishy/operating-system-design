package io.cuttlefish.debug

import io.cuttlefish.*
import io.cuttlefish.backend.*
import io.cuttlefish.components.*

class Debugger(val cpu: Cpu, val memory: MemoryBus) {
    val symbolMap: Map<String, UShort> = mapOf()
    val labelMap: Map<UShort, String> = symbolMap.map { it.value to it.key }.toMap()
    val history = ArrayDeque<String>(50)
    val breakPoints = mutableSetOf<UShort>()

    data class CpuState(
        val pcCurrent: UShort,
        val memoryCurrent: Short,
        val instructionCurrent: Instruction,
        val registersCurrent: Map<RegisterType, Short>,
        //
        val pcNext: UShort,
        val memoryNext: Short,
        val instructionNext: Instruction,

        )

    private suspend fun executeStep(): CpuState {
        cpu.tick()
        val pcCurrent = cpu.pc
        val memoryCurrent = memory.ram.internals[pcCurrent.toInt()]
        val instructionCurrent = Backend.decode(memoryCurrent.toUShort())
        val registersCurrent =
            cpu.registers.registerData.copyOf().mapIndexed { index, sh -> RegisterType.entries[index] to sh }.toMap()

        val pcNext = (pcCurrent + 1u).toUShort()
        val memoryNext = memory.ram.internals[pcNext.toInt()]
        val instructionNext = Backend.decode(memoryNext.toUShort())
        return CpuState(
            pcCurrent = pcCurrent,
            memoryCurrent = memoryCurrent,
            instructionCurrent = instructionCurrent,
            registersCurrent = registersCurrent,
            pcNext = pcNext,
            memoryNext = memoryNext,
            instructionNext = instructionNext
        )

    }
}