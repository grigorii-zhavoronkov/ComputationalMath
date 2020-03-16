import controller.MainMenuController
import view.MainMenuView

fun main(args: Array<String>) {
    val mainMenuView = MainMenuView()
    MainMenuController(mainMenuView)
}