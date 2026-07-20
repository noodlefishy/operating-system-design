package io.cuttlefish.debugging

import io.cuttlefish.*

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