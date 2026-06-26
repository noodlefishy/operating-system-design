package io.cuttlefish

import io.cuttlefish.backend.*
import io.cuttlefish.components.*
import io.cuttlefish.components.devices.*
import java.io.*
import kotlin.system.*

// lc -c $file -o $out (compile)
// lc -i $file (compile + run)
// lc -r $file (run)
suspend fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println(
            $$"""
            [USAGE] lc -c $file -o $out (compile)
            [USAGE] lc -i $file (compile + run)
            [USAGE] lc -r $file (run)
        """.trimIndent()
        )
        exitProcess(0)
    }
    val file = File(args[1])
    when (args[0]) { // flag
        "-c" -> {
            val parse = Parser(file).decode()
            val backend = Backend()
            val machineCode = backend.encode(parse)
            if (args[2] == "-o") machineCode.forEach { File(args[3]).appendText(it.toString()) }
            else machineCode.forEach { File(file.nameWithoutExtension).appendText(it.toString()) }
        }

        "-i" -> {
            val parse = Parser(file).decode()
            val backend = Backend()
            val machineCode = backend.encode(parse)

            val memory = MemoryBus(
                PhysicalMemory(1024), DisplayDevice()
            )
            for ((index, word) in machineCode.withIndex()) {
                memory.write(index.toShort(), word.toShort())
            }
            val cpu = Cpu(memory)
            while (!cpu.isHalted) {
                cpu.tick()
            }
        }

        "-r" -> {
            val machineCode = File(args[1]).readLines().map { it.toUShort() }
            val memory = MemoryBus(
                PhysicalMemory(1024), DisplayDevice()
            )
            for ((index, word) in machineCode.withIndex()) {
                memory.write(index.toShort(), word.toShort())
            }
            val cpu = Cpu(memory)
            while (!cpu.isHalted) {
                cpu.tick()
            }
        }
    }
}


//suspend fun main() {
//    val parser = Parser(File("main.kar"))
//    val instructions = parser.decode()
//
//    val backend = Backend()
//    val machineCode = backend.encode(instructions)
//    println(machineCode)
////    backend.decode(machineCode).forEach(::println)
//
//    val memory = MemoryBus(
//        PhysicalMemory(1024),
//        DisplayDevice()
//    )
//
//    for ((index, word) in machineCode.withIndex()) {
//        memory.write(index.toShort(), word.toShort())
//    }
//
//    val cpu = Cpu(memory)
//
//    while (!cpu.isHalted) {
//        cpu.tick()
//    }
//    println(cpu.registers)
//}