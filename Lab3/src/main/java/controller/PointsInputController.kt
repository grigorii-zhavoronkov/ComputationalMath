package controller

import model.ApproximationMethod
import view.PointsInputView
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.JFrame

class PointsInputController(val view: PointsInputView,
                            val model: ApproximationMethod): Controller {

    override fun addActionListeners() {
        view.backButton.addActionListener {
            view.parent.isEnabled = true
            view.frame.dispose()
        }

        view.frame.addWindowListener(CustomWindowCloseOperationAdapter(view.parent))

        view.nextButton.addActionListener {
            view.frame.isVisible = false
            // TODO: validate data
            // TODO: parse from table to Array<DoubleArray>
        }
    }

    private class CustomWindowCloseOperationAdapter(val parent: JFrame): WindowAdapter() {
        override fun windowClosed(e: WindowEvent?) {
            super.windowClosed(e)
            parent.isEnabled = true
        }
    }
}