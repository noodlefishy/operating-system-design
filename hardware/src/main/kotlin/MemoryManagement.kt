package io.cuttlefish


interface MemoryManagement {
    fun read(address: Long): Long
    fun write(address: Long, value: Long)
}