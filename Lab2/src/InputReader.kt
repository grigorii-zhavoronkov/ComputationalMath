import de.congrace.exp4j.Calculable
import de.congrace.exp4j.ExpressionBuilder
import java.util.*

class InputReader {

    private val scanner: Scanner = Scanner(System.`in`)

    fun readInputDouble(infoMessage: String): Double {
        var input = 0.0
        while (true) {
            print("${infoMessage.capitalize()}: ")
            try {
                input = scanner.nextLine().toDouble()
                return input
            } catch (e: Exception) {
                println("Неверный формат ввода. Попробуйте снова.")
            }
        }
    }

    fun readInputDouble(infoMessage: String, errMessage: String): Double {
        var input = 0.0
        while (true) {
            print("${infoMessage.capitalize()}: ")
            try {
                input = scanner.nextLine().toDouble()
                return input
            } catch (e: Exception) {
                println(errMessage.capitalize())
            }
        }
    }

    fun readInputDouble(infoMessage: String, errMessage: String, bottomLimit: Double): Double {
        var input = 0.0
        while (true) {
            input = readInputDouble(infoMessage, errMessage)
            if (input >= bottomLimit) {
                return input
            } else {
                println(errMessage)
            }
        }
    }

    fun readInputDouble(infoMessage: String, errMessage: String, bottomLimit: Double, upperLimit: Double): Double {
        var input = 0.0
        while (true) {
            input = readInputDouble(infoMessage, errMessage)
            if (input in bottomLimit..upperLimit) {
                return input
            } else {
                println(errMessage)
            }
        }
    }

    fun readInputInt(infoMessage: String): Int {
        var input = 0
        while (true) {
            print("${infoMessage.capitalize()}: ")
            try {
                input = scanner.nextLine().toInt()
                return input
            } catch (e: Exception) {
                println("Неверный формат ввода. Попробуйте снова.")
            }
        }
    }

    fun readInputInt(infoMessage: String, errMessage: String): Int {
        var input = 0
        while (true) {
            print("${infoMessage.capitalize()}: ")
            try {
                input = scanner.nextLine().toInt()
                return input
            } catch (e: Exception) {
                println(errMessage.capitalize())
            }
        }
    }

    fun readInputInt(infoMessage: String, errMessage: String, bottomLimit: Int): Int {
        var input = 0
        while (true) {
            input = readInputInt(infoMessage, errMessage)
            if (input >= bottomLimit) {
                return input
            } else {
                println(errMessage)
            }
        }
    }

    fun readInputInt(infoMessage: String, errMessage: String, bottomLimit: Int, upperLimit: Int): Int {
        var input = 0
        while (true) {
            input = readInputInt(infoMessage, errMessage)
            if (input in bottomLimit..upperLimit) {
                return input
            } else {
                println(errMessage)
            }
        }
    }

    fun readInputFormula(infoMessage: String, errMessage: String): Calculable {
        var expression: Calculable
        while (true) {
            print("${infoMessage.capitalize()}: ")
            try {
                expression = ExpressionBuilder(scanner.nextLine()).withVariableNames("x").build()
                return expression
            } catch (e: Exception) {
                println(errMessage.capitalize())
            }
        }
    }
}