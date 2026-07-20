package io.cuttlefish.debug

import io.cuttlefish.backend.*
import io.cuttlefish.components.*
import io.cuttlefish.debugging.*

class Debugger(val cpu: Cpu, val memory: MemoryBus) {
    val symbolMap: Map<String, UShort> = mapOf()
    val labelMap: Map<UShort, String> = symbolMap.map { it.value to it.key }.toMap()
    val history = ArrayDeque<String>(50)
    val breakPoints = mutableSetOf<UShort>()

    private suspend fun executeStep(): CpuState {
        cpu.tick()
        val pcCurrent = cpu.pc
        val memoryCurrent = memory.ram.internals[cpu.pc.toInt()]
        val instructionCurrent = Backend.decode(memoryCurrent.toUShort())


    }
}