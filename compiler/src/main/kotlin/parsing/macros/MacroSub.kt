package io.cuttlefish.parsing.macros

import io.cuttlefish.Instruction
import io.cuttlefish.RegisterType
import io.cuttlefish.parsing.syntaxTree.ParserContext
import io.cuttlefish.parsing.syntaxTree.Statement

class MacroSub(
    val rA: RegisterType, val rB: RegisterType, val rC: RegisterType, line: Int, col: Int
) : Statement(line, col) {

    // The compiler automatically calculates the exact binary size!
    override val size = if (rA != rB) 3 else 5

    override fun generate(context: ParserContext, address: Short): List<Instruction> {
        return if (rA != rB) {
            // Optimised non-destructive
            listOf(
                Instruction.Nand(rA, rC, rC),
                Instruction.Add(rA, rA, rB),
                Instruction.Addi(rA, rA, 1)
            )
        } else {
            // Safe 5-instruction self-restoring sequence (rA == rB)
            listOf(
                Instruction.Nand(rC, rC, rC),
                Instruction.Addi(rC, rC, 1),
                Instruction.Add(rA, rA, rC),
                Instruction.Addi(rC, rC, -1),
                Instruction.Nand(rC, rC, rC)
            )
        }
    }
}