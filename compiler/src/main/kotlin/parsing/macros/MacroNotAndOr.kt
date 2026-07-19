package io.cuttlefish.parsing.macros

import io.cuttlefish.*
import io.cuttlefish.parsing.syntaxTree.*


class MacroNot(val rA: RegisterType, line: Int, val rB: RegisterType, col: Int) : Statement(line, col) {
    override val size = 1
    override fun generate(context: ParserContext, address: Short): List<Instruction> =
        listOf(Instruction.Nand(rA, rB, rB))
}

class MacroAnd(val rA: RegisterType, val rB: RegisterType, val rC: RegisterType, line: Int, col: Int) :
    Statement(line, col) {
    override val size = 2
    override fun generate(context: ParserContext, address: Short): List<Instruction> =
        listOf(Instruction.Nand(rA, rB, rC), Instruction.Nand(rA, rA, rA))
}

class MacroOr(val rA: RegisterType, val rB: RegisterType, val rC: RegisterType, line: Int, col: Int) :
    Statement(line, col) {
    override val size = 5
    override fun generate(context: ParserContext, address: Short): List<Instruction> = listOf(
        Instruction.Nand(rB, rB, rB),
        Instruction.Nand(rA, rC, rC),
        Instruction.Nand(rA, rA, rB),
        Instruction.Nand(rB, rB, rB)
    )
}

//nand rB, rB, rB      // 1. rB = ~rB      (Temporarily negated)
//nand rA, rC, rC      // 2. rA = ~rC      (Dest is used as a scratchpad)
//nand rA, rA, rB      // 3. rA = NAND(~rC, ~rB) -> rA = rC | rB
//nand rB, rB, rB      // 4. rB = ~~rB     (Perfected restored!)