package io.cuttlefish.components

import kotlinx.coroutines.delay

class Alu {
    suspend fun add(number1: Short, number2: Short): Short {
        delay(Clock.ALU_CALCULATION_TIME)
        return (number1 + number2).toShort()
    }

    suspend fun sub(number1: Short, number2: Short): Short {
        delay(Clock.ALU_CALCULATION_TIME)
        return (number1 - number2).toShort()
    }

    suspend fun mul(number1: Short, number2: Short): Short {
        delay(Clock.ALU_CALCULATION_TIME)
        return (number1 * number2).toShort()
    }

    suspend fun div(number1: Short, number2: Short): Short {
        delay(Clock.ALU_CALCULATION_TIME)
        return (number1 / number2).toShort()
    }
}