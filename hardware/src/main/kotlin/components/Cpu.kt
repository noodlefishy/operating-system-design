package io.cuttlefish.components

import io.cuttlefish.Instruction
import io.cuttlefish.MemoryManagement

class Cpu(val mmu: MemoryManagement, val onSyscall: (Cpu, Instruction.Syscall) -> Unit) {
    val registers = Registers()
    val stack = FixedStack()
    var pc: Long = 0

    fun tick(instruction: Instruction) {
        when (instruction) {
            is Instruction.Add -> {}
            is Instruction.Syscall -> onSyscall(this, instruction)
            is Instruction.Halt -> { /* Handled by OS */ }
        }
        pc++
    }
}