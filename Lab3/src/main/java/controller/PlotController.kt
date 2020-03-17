package controller

import net.objecthunter.exp4j.ExpressionBuilder
import view.PlotView
import java.awt.event.MouseEvent
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.event.MouseInputAdapter
import kotlin.properties.Delegates

class PlotController(val view: PlotView) : Controller {

    init {
        addActionListeners()
    }

    val formatter = DecimalFormat("#.#####", DecimalFormatSymbols(Locale.GERMAN))

    override fun addActionListeners() {
        view.frame.addWindowListener(CustomWindowCloseOperationAdapter(view.parent))
        view.plotPane.addMouseWheelListener {
            run {
                val plotPane = view.plotPane
                plotPane.coef += it.wheelRotation * 0.5
                if (plotPane.coef > 10.0) {
                    view.frame.remove(plotPane)
                    view.frame.add(plotPane)
                    view.frame.revalidate()
                    view.frame.repaint()
                } else {
                    plotPane.coef = 10.0
                }
            }
        }
        val customMouseDragListener = CustomMouseDragListener(view)
        view.plotPane.addMouseListener(customMouseDragListener)
        view.plotPane.addMouseMotionListener(customMouseDragListener)
        view.calculationButton.addActionListener {
            run {
                try {
                    val x = view.calculationX.text.toDouble()
                    val exp1 = ExpressionBuilder(view.formula1).variable("x").build()
                    val exp2 = ExpressionBuilder(view.formula2).variable("x").build()
                    val value1 = formatter.format(exp1.setVariable("x", x).evaluate())
                    val value2 = formatter.format(exp2.setVariable("x", x).evaluate())
                    view.formula1CalculationResult.text = "Результат для первой формулы: $value1"
                    view.formula2CalculationResult.text = "Результат для второй формулы: $value2"
                    view.frame.pack()
                } catch (e: Exception) {
                    JOptionPane.showMessageDialog(view.frame, "Некорректное значение x.", "Ошибка", JOptionPane.WARNING_MESSAGE)
                }
            }
        }
    }

    private class CustomWindowCloseOperationAdapter(val parent: JFrame): WindowAdapter() {
        override fun windowClosed(e: WindowEvent?) {
            super.windowClosed(e)
            parent.isVisible = true
        }
    }

    private class CustomMouseDragListener(val view: PlotView): MouseInputAdapter() {
        private var pressedX by Delegates.notNull<Int>()
        private var pressedY by Delegates.notNull<Int>()

        override fun mousePressed(e: MouseEvent?) {
            super.mousePressed(e!!)
            pressedX = e.x
            pressedY = e.y
        }

        override fun mouseDragged(e: MouseEvent?) {
            super.mouseDragged(e!!)
            val dx = e.x - pressedX
            val dy = e.y - pressedY
            val plotPane = view.plotPane
            plotPane.centerX += dx
            plotPane.centerY += dy
            view.frame.remove(plotPane)
            view.frame.add(plotPane)
            view.frame.revalidate()
            view.frame.repaint()
            pressedX = e.x
            pressedY = e.y
        }
    }
}