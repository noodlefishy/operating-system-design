package io.cuttlefish

import io.cuttlefish.backend.*
import io.cuttlefish.components.*
import io.cuttlefish.components.devices.Console
import java.io.*


// lc -w file.lx
// lc -c file1.o file2.o ... -o fileMain
// lc fileMain

suspend fun main(args: Array<String>) {
    when (args.firstOrNull()) {
        "-w" -> {
            val parse = Parser(File(args[1]), 0).decode()
            val backend = Backend()
            val machineCode = backend.encode(parse)
            val ff =
                if (args.size >= 3 && args[2] == "-o") File(args[3]) else File("${File(args[1]).nameWithoutExtension}.bin")
            ff.writeText("")
            machineCode.forEach { ff.appendText(it.toString() + '\n') }
        }

        "-r" -> {
            val code = File(args[1]).readLines().map { it.toUShort() }


            val memory = MemoryBus(PhysicalMemory(), Console())
            for ((index, word) in code.withIndex()) {
                memory.write(index.toShort(), word.toShort())
            }
            val cpu = Cpu(
                mmu = memory
            )

            while (!cpu.isHalted) {
                cpu.tick()
            }


        }
    }
}