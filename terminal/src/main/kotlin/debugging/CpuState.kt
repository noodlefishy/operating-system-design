package io.cuttlefish.debugging

data class CpuState(
    var pcCurrent: UShort,
    var memoryCurrent: Short,
//    var instruction: Instruction,
)