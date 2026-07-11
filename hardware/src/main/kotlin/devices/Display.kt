package io.cuttlefish.devices


import java.awt.*
import javax.swing.*


class Display : Device {
    private val frame = JFrame("Pixastachio")
    private var grid: GridPanel? = null
    override val deviceId: UShort = 2u
    val dimensions = 8 to 8
    override val memoryUsed: UIntRange = 0xFF03u..0xFF4Fu
    override suspend fun read(address: UShort): Short {
        TODO("Not yet implemented")
    }

    override suspend fun write(address: UShort, value: Short) {
        when (address.toInt()) {
            0xFF03 -> {
                when (value) {
                    0.toShort() -> {
                        frame.dispose()
                    }

                    1.toShort() -> {
                        grid = frameInit(frame)
                        grid!!.repaint()
                    }

                    2.toShort() -> {
                        pixelData.copyOf().forEach { pixelData[it] = 0x0000 }
                        grid!!.repaint()
                    }

                    4.toShort() -> {
                        grid!!.repaint()
                    }
                }
            }

            in 0xFF0F..0xFF4F -> {
                pixelData[(address.toInt() - 0xFF0F)] = value.toInt()
            }

        }
    }
}


private val pixelData = IntArray(8 * 8)


fun main() {
    val frame = JFrame("8x8 Pixel Grid")
    val g = frameInit(frame)

    val x = 4 - 1
    val y = 4 - 1
    pixelData[y * 8 + x] = 0xFFE0
    g.repaint()
}

class GridPanel(private val data: IntArray) : JPanel() {
    private val pixelSize = 50

    init {
        this.preferredSize = Dimension(8 * pixelSize, 8 * pixelSize)
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)

        for (i in data.indices) {
            val x = i % 8
            val y = i / 8


            val rgb565 = data[i] and 0xFFFF


            val r5 = (rgb565 shr 11) and 0x1F // Red: Top 5 bits
            val g6 = (rgb565 shr 5) and 0x3F // Green: Middle 6 bits
            val b5 = rgb565 and 0x1F // Blue: Bottom 5 bits


            val r8 = (r5 * 255) / 31
            val g8 = (g6 * 255) / 63
            val b8 = (b5 * 255) / 31

            g.color = Color(r8, g8, b8)

            g.fillRect(x * pixelSize, y * pixelSize, pixelSize, pixelSize)


            g.color = Color.LIGHT_GRAY
            g.drawRect(x * pixelSize, y * pixelSize, pixelSize, pixelSize)
        }
    }
}


fun frameInit(frame: JFrame): GridPanel {
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    val grid = GridPanel(pixelData)
    frame.add(grid)

    frame.pack()
    frame.isResizable = false
    frame.setLocationRelativeTo(null)

    frame.isVisible = true
    return grid
}
