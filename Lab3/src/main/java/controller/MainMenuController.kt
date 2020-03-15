package controller

import model.ApproximationMethod
import view.MainMenuView
import view.PointsInputView

class MainMenuController(
        val model: ApproximationMethod,
        val view: MainMenuView): Controller {

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
                val pointsInputController = PointsInputController(pointsInputView, model)
                pointsInputController.addActionListeners()
            }
        }
    }
}