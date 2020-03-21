package view

import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.awt.*
import java.lang.Exception
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import javax.swing.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.properties.Delegates

class PlotView(val points: Array<DoubleArray>,
               val dropId: Int,
               val formula1: String,
               val formula2: String,
               val parent: JFrame) {

    val frame = JFrame()

    private var decimalFormatSymbols = DecimalFormatSymbols(Locale.GERMAN)
    private var formatter: DecimalFormat

    var minY = Double.MAX_VALUE
    var minX = Double.MAX_VALUE

    var maxY = Double.MIN_VALUE
    var maxX = Double.MIN_VALUE

    val calculationButton = JButton("Узнать значение")
    val calculationX = JTextField()
    val formula1CalculationResult = JLabel()
    val formula2CalculationResult = JLabel()

    private val width = 640
    private val height = 640
    var offsetX = 20
    var offsetY = 20

    private var coef by Delegates.notNull<Double>()
    private val stepX = 1

    lateinit var plotPane: PlotPane

    init {
        decimalFormatSymbols.decimalSeparator = '.'
        formatter = DecimalFormat("#.#####", decimalFormatSymbols)
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
        val expression1 = ExpressionBuilder(formula1).variable("x").build()
        val expression2 = ExpressionBuilder(formula2).variable("x").build()
        plotPane = PlotPane(width, height, coef, width/2 - offsetX, height/2 - offsetY, stepX, dropId, expression1, expression2, points)
        pane.add(plotPane, paneConstraints)

        val labelsPanel = JPanel()
        val labelsConstrains = GridBagConstraints()
        labelsPanel.layout = GridBagLayout()
        labelsConstrains.anchor = GridBagConstraints.NORTHWEST
        labelsConstrains.fill = GridBagConstraints.HORIZONTAL
        labelsConstrains.gridx = 0
        labelsConstrains.gridy = 0
        labelsPanel.add(JLabel("Зеленый график: $formula1"), labelsConstrains)

        labelsConstrains.gridy = 1
        labelsPanel.add(JLabel("Синий график: $formula2"), labelsConstrains)

        labelsConstrains.gridy = 2
        labelsPanel.add(JLabel("Исключенная точка: (${formatter.format(points[dropId][0])} : ${formatter.format(points[dropId][1])})"), labelsConstrains)

        val calculationPanel = JPanel()
        val calculationPanelConstraints = GridBagConstraints()
        calculationPanel.layout = GridBagLayout()
        calculationPanelConstraints.fill = GridBagConstraints.HORIZONTAL
        calculationPanelConstraints.anchor = GridBagConstraints.NORTHWEST

        val inputPanel = JPanel()
        inputPanel.layout = FlowLayout(FlowLayout.LEFT, 5, 5)
        inputPanel.add(JLabel("Введите X:"))
        calculationX.columns = 20
        inputPanel.add(calculationX)

        calculationPanelConstraints.gridy = 0
        calculationPanelConstraints.gridx = 0
        calculationPanel.add(inputPanel)

        calculationPanelConstraints.gridy = 1
        calculationPanelConstraints.gridx = 0
        calculationPanel.add(calculationButton, calculationPanelConstraints)

        calculationPanelConstraints.gridy = 2
        calculationPanel.add(formula1CalculationResult, calculationPanelConstraints)

        calculationPanelConstraints.gridy = 3
        calculationPanel.add(formula2CalculationResult, calculationPanelConstraints)

        paneConstraints.gridy = 1
        pane.add(labelsPanel, paneConstraints)

        paneConstraints.gridy = 2
        pane.add(calculationPanel, paneConstraints)
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

    class PlotPane(private val _width: Int,
                   private val _height: Int,
                   var coef: Double,
                   var centerX: Int,
                   var centerY: Int,
                   private val stepX: Int,
                   private val dropId: Int,
                   private val formula1: Expression,
                   private val formula2: Expression,
                   private val points: Array<DoubleArray>): JPanel() {

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
            g.drawLine(centerX + coef.toInt(), centerY - 5, centerX + coef.toInt(), centerY + 5)
            g.drawString("1", centerX + coef.toInt(), centerY - 5)
            g.drawLine(centerX - 5, centerY - coef.toInt(), centerX + 5, centerY - coef.toInt())
            g.drawString("1", centerX - 8, centerY - coef.toInt() - 2)

            /* translating coordinates */
            g.translate(centerX, centerY)

            /* draw points */
            for (i in points.indices) {
                val x = (points[i][0] * coef).toInt()
                val y = -(points[i][1] * coef).toInt()
                if (i == dropId) {
                    g.color = Color.RED
                }
                g.fillArc(x-3, y-3, 6, 6, 0, 360)
                if (i == dropId) {
                    g.color = Color.GRAY
                }
            }

            /* draw plots */

            var prevX = -centerX / coef
            var prevY1 = -formula1.setVariable("x", -centerX/coef).evaluate()
            var prevY2 = -formula2.setVariable("x", -centerX/coef).evaluate()

            val fromX = -centerX
            val toX = abs(centerX) + _width

            for (x in fromX until toX step stepX) {
                val tx = x / coef
                var ty1: Double?
                var ty2: Double?
                try {
                    ty1 = -formula1.setVariable("x", tx).evaluate()
                    ty2 = -formula2.setVariable("x", tx).evaluate()
                } catch (e: Exception) {
                    ty1 = -formula1.setVariable("x", tx + 0.00001).evaluate()
                    ty2 = -formula2.setVariable("x", tx + 0.00001).evaluate()
                }
                g.color = Color.GREEN
                g.drawLine((prevX*coef).toInt(), (prevY1*coef).toInt(), (tx*coef).toInt(), (ty1!!*coef).toInt())
                g.color = Color.BLUE
                g.drawLine((prevX*coef).toInt(), (prevY2*coef).toInt(), (tx*coef).toInt(), (ty2!!*coef).toInt())
                prevX = tx
                prevY1 = ty1
                prevY2 = ty2
            }
        }
    }
}