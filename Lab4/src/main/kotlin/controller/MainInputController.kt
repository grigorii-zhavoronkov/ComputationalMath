package controller

import model.Formula
import model.Point
import net.objecthunter.exp4j.ExpressionBuilder
import utils.ApproximationMethod
import utils.EulerMethod
import view.MainInputView
import view.PlotView
import javax.swing.JOptionPane
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener

class MainInputController(val view: MainInputView) {
    init {
        view.inputFunctionTextField.document.addDocumentListener(object : DocumentListener{
            override fun changedUpdate(e: DocumentEvent?) {
            }

            override fun insertUpdate(e: DocumentEvent?) {
                view.secondArgumentLabel.text = "(${view.inputFunctionTextField.text})"
            }

            override fun removeUpdate(e: DocumentEvent?) {
                if (view.inputFunctionTextField.text.isEmpty()) {
                    view.secondArgumentLabel.text = "f(x,y)"
                } else {
                    view.secondArgumentLabel.text = "(${view.inputFunctionTextField.text})"
                }
            }
        })

        view.calculateButton.addActionListener {
            val eulerMethod: EulerMethod?
            val data: Array<Point>?
            try {
                val formula = Formula(
                    view.secondArgumentLabel.text,
                    ExpressionBuilder(view.secondArgumentLabel.text).variables("x", "y").build()
                )
                val x = view.xInputTextField.text.toDouble()
                val y = view.yInputTextField.text.toDouble()
                val endOfSegment = view.endOfSegmentTextField.text.toDouble()
                val accuracy = view.accuracyTextField.text.toDouble()
                eulerMethod = EulerMethod(formula, x, y, endOfSegment, accuracy)
                data = eulerMethod.evaluate()
            } catch (e: Exception) {
                JOptionPane.showMessageDialog(view.frame,
                    "Возникла ошибка при валидации значений. Проверьте все поля ввода.",
                    "Ошибка валидации",
                    JOptionPane.WARNING_MESSAGE)
                return@addActionListener
            }
            val approximationMethod: ApproximationMethod?
            val formula: Formula?
            try {
                approximationMethod = ApproximationMethod()
                formula = approximationMethod.findBest(data)
            } catch (e: Exception) {
                JOptionPane.showMessageDialog(view.frame,
                    "Возникла ошибка при построении графика",
                    "Ошибка построения графика",
                    JOptionPane.WARNING_MESSAGE)
                return@addActionListener
            }
            view.frame.isVisible = false
            val plotView = PlotView(data, formula, view.frame)
            PlotController(plotView)
        }
    }
}