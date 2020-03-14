package controller

import model.ApproximationMethod
import view.MainMenuView
import view.PointsInputView

class MainMenuController(
        val model: ApproximationMethod,
        val view: MainMenuView) {

    init {
        view.fileInputButton.addActionListener({e -> {

        }})
        view.pointsInputButton.addActionListener {
            run {
                view.frame.isEnabled = false
                val pointsInputView = PointsInputView()
            }
        }
    }
}