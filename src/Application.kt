import java.io.File
import java.util.*
import kotlin.system.exitProcess

const val DIVIDER = "===================="

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val inputReader = InputReader()
    var coefficients: Array<DoubleArray>? = null
    var eps: Double = 0.0
    var size: Int = 0
    var maxIterations: Int = 0

    println("Данная программа позволяет решить СЛАУ методом Гаусса-Зейделя")
    while (true) {
        loop@ while (true) {
            println(DIVIDER)
            println("Главное меню:")
            println("1) Ввод коэффициентов с клавиатуры")
            println("2) Ввод коэффициентов из файла")
            println("3) Случайные коэффициенты")
            println("0) Выход")
            print("Ввод: ")
            try {
                when (scanner.next().toInt()) {
                    1 -> {
                        println(DIVIDER)
                        println("Вы выбрали ввод с консоли")
                        inputReader.consoleCoefficientReader(scanner)
                        coefficients = inputReader.coefficients
                        eps = inputReader.eps
                        size = inputReader.size
                        maxIterations = inputReader.maxIterations
                        break@loop
                    }
                    2 -> {
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
                                val fileReader = Scanner(File(scanner.next()))
                                inputReader.fileCoefficientReader(fileReader)
                                fileReader.close()
                                coefficients = inputReader.coefficients
                                eps = inputReader.eps
                                size = inputReader.size
                                maxIterations = inputReader.maxIterations
                                break@loop
                            } catch (e: Exception) {
                                println("Возникла ошибка при вводе из файла. Проверьте имя файла и его формат.")
                            }
                        }
                    }
                    3 -> {
                        println(DIVIDER)
                        println("Вы выбрали заполнение матрицы случайными коэффициентами")
                        inputReader.randomCoefficients(scanner)
                        coefficients = inputReader.coefficients
                        eps = inputReader.eps
                        size = inputReader.size
                        maxIterations = inputReader.maxIterations
                        break@loop
                    }
                    0 -> {
                        println("Выход из программы...")
                        exitProcess(0)
                    }
                    else -> throw Exception()
                }
            } catch (e: Exception) {
                println("Вы ввели неверное число. Попробуйте еще раз.")
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