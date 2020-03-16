package view

import net.objecthunter.exp4j.Expression
import java.awt.*
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.UIManager
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.properties.Delegates

class PlotView(val points: Array<DoubleArray>,
               val expression1: Expression,
               val expression2: Expression,
               val parent: JFrame) {

    val frame = JFrame()

    var minY = Double.MAX_VALUE
    var minX = Double.MAX_VALUE

    var maxY = Double.MIN_VALUE
    var maxX = Double.MIN_VALUE

    private val width = 640
    private val height = 640
    private val offsetX = 40
    private val offsetY = 40

    private var coef by Delegates.notNull<Double>()
    private val stepX = 1

    init {
        calculateBoundsAndCoefficients()
        addComponentsToPane(frame.contentPane)
        addSettings()
    }

    private fun addSettings() {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())

        frame.isResizable = false
        frame.defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
        frame.pack()
        frame.isVisible = true
    }

    private fun addComponentsToPane(pane: Container) {
        pane.layout = GridLayout(0, 1)
        pane.add(PlotPane(width, height, coef, width/2 - offsetX, height/2 - offsetY, stepX, expression1, expression2, points))
    }

    private fun calculateBoundsAndCoefficients() {
        for (i in points.indices) {
            if (points[i][0] > maxX) {
                maxX = points[i][0]
            }
            if (points[i][0] < minX) {
                minX = points[i][0]
            }
            if (points[i][1] > maxY) {
                maxY = points[i][1]
            }
            if (points[i][1] < minY) {
                minY = points[i][1]
            }
        }

        coef = min((width / 2 - offsetX) / max(abs(maxX), abs(minX)), (height / 2 - offsetY) / max(abs(maxY), abs(minY)))

    }

    private class PlotPane(val _width: Int,
                           val _height: Int,
                           val coef: Double,
                           val centerX: Int,
                           val centerY: Int,
                           val stepX: Int,
                           val formula1: Expression,
                           val formula2: Expression,
                           val points: Array<DoubleArray>): JPanel() {

        override fun getPreferredSize(): Dimension {
            return Dimension(_width, _height)
        }

        override fun paintComponent(g: Graphics?) {
            super.paintComponent(g!!)
            /* clear canvas */
            g.color = Color.WHITE
            g.fillRect(0, 0, _width, _height)
            /* draw coordinates */
            g.color = Color.GRAY
            g.drawLine(0, centerY, _width, centerY)
            g.drawLine(centerX, 0, centerX, _height)

            g.translate(centerX, centerY)

            for (i in points.indices) {
                val x = (points[i][0] * coef).toInt()
                val y = -(points[i][1] * coef).toInt()
                g.fillArc(x-3, y-3, 6, 6, 0, 360)
            }

            var prevX = -centerX / coef
            var prevY1 = -formula1.setVariable("x", -centerX/coef).evaluate()
            var prevY2 = -formula2.setVariable("x", -centerX/coef).evaluate()
            for (x in -centerX until centerX step stepX) {
                val tx = x / coef
                val ty1 = -formula1.setVariable("x", tx).evaluate()
                val ty2 = -formula2.setVariable("x", tx).evaluate()
                g.color = Color.RED
                g.drawLine((prevX*coef).toInt(), (prevY1*coef).toInt(), (tx*coef).toInt(), (ty1*coef).toInt())
                g.color = Color.BLUE
                g.drawLine((prevX*coef).toInt(), (prevY2*coef).toInt(), (tx*coef).toInt(), (ty2*coef).toInt())
                prevX = tx
                prevY1 = ty1
                prevY2 = ty2
            }
        }
    }
}