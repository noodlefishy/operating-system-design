package io.cuttlefish.devices


class Display : Device {
    override val deviceId: UShort = 2u
    val dimensions = 8 to 8
    override val memoryUsed: IntRange = 0xFE00..0xFE00
    override suspend fun read(address: Short): Short {
        TODO("Not yet implemented")
    }

    override suspend fun write(address: Short, value: Short) {
        TODO("Not yet implemented")
    }
}