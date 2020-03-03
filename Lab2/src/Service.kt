import de.congrace.exp4j.Calculable
import de.congrace.exp4j.ExpressionBuilder
import kotlin.system.exitProcess

class Service {

    private val mainMenu = """
        Главное меню:
        1) Выбрать интеграл из существующих формул
        2) Ввести интеграл вручную
        0) Выход
        Ввод""".trimIndent()

    private enum class InputType {
        EXIT,
        EXISTED_FORMULA,
        INPUT_FORMULA
    }

    private val formulas = ArrayList<String>()

    init {
        formulas.add("sin(x)")
        formulas.add("sin(x)")
        formulas.add("sin(x)")
        formulas.add("sin(x)")
        formulas.add("sin(x)")
        formulas.trimToSize()
    }

    fun serve() {
        println("Данная программа позволяет вычислить значение интеграла методом Симпсона")
        val inputReader = InputReader()
        do {
            println(DIVIDER)
            val inputType = InputType.values()[inputReader.readInputInt(mainMenu, "Неверная команда. Попробуйте снова.", 0, 2)]
            var formula: Calculable
            println(DIVIDER)
            when (inputType) {
                InputType.INPUT_FORMULA -> {
                    formula = inputReader.readInputFormula("Введите интеграл с переменной x", "Неверный формат формулы. Попробуйте снова.")
                }
                InputType.EXISTED_FORMULA -> {
                    var infoString = "Выберите формулу из списка предложенного ниже:"
                    for (i in formulas.indices) {
                        infoString += "\n${i+1}) ${formulas[i]}"
                    }
                    infoString += "\nВвод"
                    formula = ExpressionBuilder(formulas[inputReader.readInputInt(infoString, "Неверный параметр, попробуйте снова", 1, formulas.size) - 1])
                            .withVariableNames("x")
                            .build()
                }
                else -> {
                    println("Выход из программы...")
                    exitProcess(0)
                }
            }

            val bottomLimit = inputReader.readInputDouble("Введите нижний предел интегрирования")
            val upperLimit = inputReader.readInputDouble("Введите верхний предел интегрирования")
            val eps = inputReader.readInputDouble("Введите точность")
            val solver = SimpsonMethod(formula, bottomLimit, upperLimit, eps)
            solver.solve()
            println(DIVIDER)
            println("Количество разбиений: ${solver.n}")
            println("Погрешность вычислений: +-${solver.accuracy}")
            println("Ответ: ${solver.answer}")
        } while (inputType != InputType.EXIT)
    }
}