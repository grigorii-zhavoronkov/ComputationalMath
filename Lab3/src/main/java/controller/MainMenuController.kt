package controller

import model.ApproximationMethod
import view.MainMenuView
import view.PointsInputView

class MainMenuController(val view: MainMenuView): Controller {

    init {
        addActionListeners()
    }

    override fun addActionListeners() {
        view.fileInputButton.addActionListener {
            run {
                // TODO: fileInputView implementation
            }
        }
        view.pointsInputButton.addActionListener {
            run {
                view.frame.isEnabled = false
                val pointsInputView = PointsInputView(view.frame, view.nInput.value as Int)
                PointsInputController(pointsInputView, ApproximationMethod())
            }
        }
    }
}