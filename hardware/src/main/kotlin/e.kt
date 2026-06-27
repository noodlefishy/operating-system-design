package io.cuttlefish


@JvmInline
value class Address(val value: Short) {
    operator fun plus(other: Address): Address = Address((this.value + other.value).toShort())
}

fun main() {
    println(Address(20))
    println(message = run { Address(20) + Address(30) })
    println("Yayy!")

}