package view

import model.Formula
import model.Point
import java.awt.*
import java.lang.Exception
import javax.swing.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.properties.Delegates

class PlotView(val points: Array<Point>,
               val formula: Formula,
               val parent: JFrame) {

    val frame = JFrame()

    var minY = Double.MAX_VALUE
    var minX = Double.MAX_VALUE

    var maxY = Double.MIN_VALUE
    var maxX = Double.MIN_VALUE

    private val width = 640
    private val height = 640
    var offsetX = 20
    var offsetY = 20

    private var coefX by Delegates.notNull<Double>()
    private var coefY by Delegates.notNull<Double>()
    private val stepX = 1

    lateinit var plotPane: PlotPane

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
        pane.layout = GridBagLayout()
        val paneConstraints = GridBagConstraints()
        paneConstraints.anchor = GridBagConstraints.NORTHWEST
        paneConstraints.fill = GridBagConstraints.HORIZONTAL
        paneConstraints.gridx = 0
        paneConstraints.gridy = 0
        plotPane = PlotPane(width, height, coefX, coefY, width/2 - offsetX, height/2 - offsetY, stepX, formula, points)
        pane.add(plotPane, paneConstraints)

        val labelsPanel = JPanel()
        val labelsConstrains = GridBagConstraints()
        labelsPanel.layout = GridBagLayout()
        labelsConstrains.anchor = GridBagConstraints.NORTHWEST
        labelsConstrains.fill = GridBagConstraints.HORIZONTAL
        labelsConstrains.gridx = 0
        labelsConstrains.gridy = 0
        labelsPanel.add(JLabel("Зеленый график: ${formula.string}"), labelsConstrains)

        paneConstraints.gridy = 1
        pane.add(labelsPanel, paneConstraints)
    }

    private fun calculateBoundsAndCoefficients() {
        for (i in points.indices) {
            if (points[i].x > maxX) {
                maxX = points[i].x
            }
            if (points[i].x < minX) {
                minX = points[i].x
            }
            if (points[i].y > maxY) {
                maxY = points[i].y
            }
            if (points[i].y < minY) {
                minY = points[i].y
            }
        }

        coefX = (width / 2 - offsetX) / max(abs(maxX), abs(minX))
        coefY = (height / 2 - offsetY) / max(abs(maxY), abs(minY))
    }

    class PlotPane(private val _width: Int,
                   private val _height: Int,
                   var coefX: Double,
                   var coefY: Double,
                   var centerX: Int,
                   var centerY: Int,
                   private val stepX: Int,
                   private val formula: Formula,
                   private val points: Array<Point>): JPanel() {

        private val arrowSize = 5

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

            /* draw arrows */
            g.drawLine(_width, centerY, _width - arrowSize, centerY-arrowSize)
            g.drawLine(_width, centerY, _width - arrowSize, centerY+arrowSize)
            g.drawString("X", _width - 10, centerY - 10)
            g.drawString("Y", centerX + 10, 10)
            g.drawLine(centerX, 0, centerX + arrowSize, arrowSize)
            g.drawLine(centerX, 0, centerX - arrowSize, arrowSize)

            /* draw ords */
            g.drawLine(centerX + coefX.toInt(), centerY - 5, centerX + coefX.toInt(), centerY + 5)
            g.drawString("1", centerX + coefX.toInt(), centerY - 5)
            g.drawLine(centerX - 5, centerY - coefY.toInt(), centerX + 5, centerY - coefY.toInt())
            g.drawString("1", centerX - 8, centerY - coefY.toInt() - 2)

            /* translating coordinates */
            g.translate(centerX, centerY)

            /* draw points */
            g.color = Color.GRAY
            for (i in points.indices) {
                val x = (points[i].x * coefX).toInt()
                val y = -(points[i].y * coefY).toInt()
                g.fillArc(x-3, y-3, 6, 6, 0, 360)
            }

            /* draw plots */

            var prevX = -centerX / coefX
            var prevY1 = -formula.expression.setVariable("x", prevX).evaluate()

            val fromX = -centerX
            val toX = abs(centerX) + _width

            for (x in fromX until toX step stepX) {
                val tx = x / coefX
                val ty1 = try {
                    -formula.expression.setVariable("x", tx).evaluate()
                } catch (e: Exception) {
                    -formula.expression.setVariable("x", tx + 0.00001).evaluate()
                }
                g.color = Color.GREEN
                g.drawLine((prevX*coefX).toInt(), (prevY1*coefY).toInt(), (tx*coefX).toInt(), (ty1*coefY).toInt())
                prevX = tx
                prevY1 = ty1
            }
        }
    }
}