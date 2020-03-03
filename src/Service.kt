import java.io.File
import java.util.*
import kotlin.system.exitProcess

class Service {

    val MENU_MESSAGE = """
        Главное меню:
        1) Ввод коэффициентов с клавиатуры
        2) Ввод коэффициентов из файла
        3) Случайные коэффициенты
        0) Выход
        Ввод""".trimIndent()

    private enum class MenuInputType {
        EXIT,
        KEYBOARD,
        FILE,
        RANDOM
    }

    fun serve() {
        val scanner = Scanner(System.`in`)

        val inputReader = InputReader()
        var coefficients: Array<DoubleArray>? = null
        var eps: Double = 0.0
        var size: Int = 0
        var maxIterations: Int = 0

        println("Данная программа позволяет решить СЛАУ методом Гаусса-Зейделя")
        while (true) {
            when (MenuInputType.values()[inputReader.readInputInt(scanner, MENU_MESSAGE, "Вы ввели неверное число. Попробуйте еще раз.", 0, 3)]) {
                MenuInputType.KEYBOARD -> {
                    println(DIVIDER)
                    println("Вы выбрали ввод с консоли")
                    inputReader.consoleCoefficientReader(scanner)
                    coefficients = inputReader.coefficients
                    eps = inputReader.eps
                    size = inputReader.size
                    maxIterations = inputReader.maxIterations
                }
                MenuInputType.FILE -> {
                    println(DIVIDER)
                    println("Вы выбрали ввод из файла")
                    while (true) {
                        println("Формат файла")
                        println("Первая строчка - точность (eps)")
                        println("Вторая строчка - размер матрицы (n <= 20)")
                        println("Третья строчка - максимальное количество итераций (0 - без ограничений)")
                        println("Следующие n строк - n элементов матрицы системы")
                        println("Последняя строка - столбец свободных членов")
                        print("Введите имя файла: ")
                        try {
                            val fileReader = Scanner(File(scanner.nextLine()))
                            inputReader.fileCoefficientReader(fileReader)
                            fileReader.close()
                            coefficients = inputReader.coefficients
                            eps = inputReader.eps
                            size = inputReader.size
                            maxIterations = inputReader.maxIterations
                            break
                        } catch (e: Exception) {
                            println("Возникла ошибка при вводе из файла. Проверьте имя файла и его формат.")
                        }
                    }
                }
                MenuInputType.RANDOM -> {
                    println(DIVIDER)
                    println("Вы выбрали заполнение матрицы случайными коэффициентами")
                    inputReader.randomCoefficients(scanner)
                    coefficients = inputReader.coefficients
                    eps = inputReader.eps
                    size = inputReader.size
                    maxIterations = inputReader.maxIterations
                }
                MenuInputType.EXIT -> {
                    println("Выход из программы...")
                    exitProcess(0)
                }
            }
            println(DIVIDER)
            println("Заданная точность: $eps")
            println("Заданное количество элементов: $size")
            println("Матрица: ")
            for (i in 0 until size) {
                for (j in 0 until size) {
                    print(String.format("%10.2f ", coefficients!![i][j]))
                }
                println(String.format("| %10.2f", coefficients!![i][size]))
            }

            val solver = GaussSeidel(eps, coefficients!!, size, maxIterations)
            val solution = solver.solve()
            if (solution != null) {

                println("Решение системы:")
                for (i in solution.indices) {
                    println("x[${i + 1}]: ${solution[i]} +- ${solver.accuracy[i]}")
                }
                println("Количество итераций: ${solver.counter}")
            } else {
                println("Невозможно решить систему.\nНевозможно привести матрицу к виду диагонального преобладания.")
            }
        }
    }
}