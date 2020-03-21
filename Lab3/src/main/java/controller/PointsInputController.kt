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

class PointsInputController(private val view: PointsInputView): Controller {

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
            val model = ApproximationMethod()

            var successValidation = true
            try {
                when {
                    view.linearApproximation.isSelected -> {
                        methodType = ApproximationMethod.Type.LINEAR
                    }
                    view.squareApproximation.isSelected -> {
                        methodType = ApproximationMethod.Type.SQUARE
                    }
                    view.cubeApproximation.isSelected -> {
                        methodType = ApproximationMethod.Type.CUBE
                    }
                    view.powerApproximation.isSelected -> {
                        methodType = ApproximationMethod.Type.POWER
                    }
                    view.hyperbolaApproximation.isSelected -> {
                        methodType = ApproximationMethod.Type.HYPERBOLA
                    }
                    view.indicativeApproximation.isSelected -> {
                        methodType = ApproximationMethod.Type.INDICATIVE
                    }
                    view.logApproximation.isSelected -> {
                        methodType = ApproximationMethod.Type.LOG
                    }
                    view.expApproximation.isSelected -> {
                        methodType = ApproximationMethod.Type.EXP
                    }
                    else -> {
                        throw IllegalArgumentException()
                    }
                }
                data = model.validate(methodType, view.table.model as DefaultTableModel)
            } catch (e: Exception) {
                e.printStackTrace() //TODO: убрать
                successValidation = false
            }

            if (successValidation) {
                try {
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

    private class CustomWindowCloseOperationAdapter(val parent: JFrame): WindowAdapter() {
        override fun windowClosed(e: WindowEvent?) {
            super.windowClosed(e)
            parent.isEnabled = true
        }
    }
}