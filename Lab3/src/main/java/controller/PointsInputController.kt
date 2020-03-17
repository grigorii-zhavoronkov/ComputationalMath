package controller

import model.ApproximationMethod
import model.PointFinder
import view.PlotView
import view.PointsInputView
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.table.DefaultTableModel

class PointsInputController(val view: PointsInputView): Controller {

    init {
        addActionListeners()
    }

    override fun addActionListeners() {
        view.backButton.addActionListener {
            view.parent.isEnabled = true
            view.frame.dispose()
        }

        view.frame.addWindowListener(CustomWindowCloseOperationAdapter(view.parent))

        view.nextButton.addActionListener {
            lateinit var data: Array<DoubleArray>
            lateinit var methodType: ApproximationMethod.Type

            var successValidation = true
            try {
                data = when {
                    view.linearApproximation.isSelected -> {
                        methodType = ApproximationMethod.Type.LINEAR
                        parse()
                    }
                    view.squareApproximation.isSelected -> {
                        methodType = ApproximationMethod.Type.SQUARE
                        parse()
                    }
                    view.cubeApproximation.isSelected -> {
                        methodType = ApproximationMethod.Type.CUBE
                        parse()
                    }
                    view.powerApproximation.isSelected -> {
                        methodType = ApproximationMethod.Type.POWER
                        parse()
                        // x[i] > 0 y[i] > 0
                    }
                    view.hyperbolaApproximation.isSelected -> {
                        methodType = ApproximationMethod.Type.HYPERBOLA
                        parse()
                        // x[i] != 0
                    }
                    view.indicativeApproximation.isSelected -> {
                        methodType = ApproximationMethod.Type.INDICATIVE
                        parse()
                        // y[i] > 0
                    }
                    view.logApproximation.isSelected -> {
                        methodType = ApproximationMethod.Type.LOG
                        parse()
                        // x[i] > 0
                    }
                    view.expApproximation.isSelected -> {
                        methodType = ApproximationMethod.Type.EXP
                        parse()
                        // y[i] > 0
                    }
                    else -> {
                        throw IllegalArgumentException()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace() //TODO: убрать
                successValidation = false
            }

            if (successValidation) {
                try {
                    val model = ApproximationMethod()
                    val finder = PointFinder()
                    val formula1 = model.evaluate(view.rowSize, data, methodType)
                    val dropId = finder.findPointId(data, formula1)
                    val copyData = Array(view.rowSize - 1) {DoubleArray(2)}
                    var offset = 0
                    for (i in data.indices) {
                        if (i == dropId) {
                            offset = 1
                        } else {
                            copyData[i - offset] = data[i]
                        }
                    }
                    val formula2 = model.evaluate(view.rowSize - 1, copyData, methodType)
                    val plotView = PlotView(data, dropId, formula1, formula2, view.frame)
                    PlotController(plotView)
                    view.frame.isVisible = false
                } catch (e: Exception) {
                    e.printStackTrace()
                    JOptionPane.showMessageDialog(view.frame,
                            "Возникла ошибка при вычислениях. Попробуйте ввести другие данные.",
                            "Ошибка вычислений",
                            JOptionPane.WARNING_MESSAGE)
                }
            } else {
                JOptionPane.showMessageDialog(view.frame,
                        "При валидации возникла ошибка. Проверьте введенные данные.",
                        "Ошибка валидации",
                        JOptionPane.WARNING_MESSAGE)
            }
        }
    }

    private fun parse(): Array<DoubleArray> {
        val data = Array(view.rowSize) {DoubleArray(2)}
        val tableModel: DefaultTableModel = view.table.model as DefaultTableModel
        for (i in 0 until tableModel.rowCount) {
            data[i][0] = (tableModel.getValueAt(i, 0) as String).toDouble()
            data[i][1] = (tableModel.getValueAt(i, 1) as String).toDouble()
        }
        return data
    }

    private class CustomWindowCloseOperationAdapter(val parent: JFrame): WindowAdapter() {
        override fun windowClosed(e: WindowEvent?) {
            super.windowClosed(e)
            parent.isEnabled = true
        }
    }
}