import controller.MainMenuController
import model.ApproximationMethod
import view.MainMenuView

fun main(args: Array<String>) {
    val mainMenuView = MainMenuView()
    val approximationMethod = ApproximationMethod()
    val mainMenuController = MainMenuController(approximationMethod, mainMenuView)
}