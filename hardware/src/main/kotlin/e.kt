package io.cuttlefish

import java.awt.*
import javax.swing.*


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


            // 1. Get the 16-bit unsigned value from the short
            val rgb565 = data[i] and 0xFFFF


            // 2. Extract channels using bitmasks and bit shifts
            val r5 = (rgb565 shr 11) and 0x1F // Red: Top 5 bits
            val g6 = (rgb565 shr 5) and 0x3F // Green: Middle 6 bits
            val b5 = rgb565 and 0x1F // Blue: Bottom 5 bits


            // 3. Scale up to 8-bit channels (0-255) for Java Swing
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