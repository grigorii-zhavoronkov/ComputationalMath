import controller.MainInputController
import view.MainInputView

fun main(args: Array<String>) {
    val mainInputView = MainInputView()
    MainInputController(mainInputView)
}