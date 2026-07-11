package io.cuttlefish

object MemoryMapRanges { // 64 KB
    val vectorRange: IntRange   by lazy { return@lazy 0x0000.rangeTo(other = 0x003F) } // 0,0625  kb | 64w
    val kernelRange: IntRange   by lazy { return@lazy 0x0040.rangeTo(other = 0x2FFF) } // 11,9375 kb | 12 224w
    val userLandRange: IntRange by lazy { return@lazy 0x3000.rangeTo(other = 0xFDFF) } // 51,5    kb | 52 736w
    val mmioRange: IntRange     by lazy { return@lazy 0xFE00.rangeTo(other = 0xFFFF) } // 0,5     kb | 512w
}