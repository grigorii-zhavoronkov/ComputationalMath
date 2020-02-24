import java.io.File
import java.util.*
import kotlin.system.exitProcess

const val DIVIDER = "===================="

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    // TODO: Вывод меню  + выбор + InputReader + GaussSeidel + output

    val inputReader = InputReader()
    var coefficients: Array<DoubleArray>? = null
    var eps: Double = 0.0
    var size: Int = 0

    println("Данная программа позволяет решить СЛАУ методом Гаусса-Зейделя")
    loop@ while (true) {
        println(DIVIDER)
        println("Главное меню:")
        println("1) Ввод коэффициентов с клавиатуры")
        println("2) Ввод коэффициентов из файла")
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
                    break@loop
                }
                2 -> {
                    println("Вы выбрали ввод из файла")
                    while (true) {
                        println(DIVIDER)
                        println("Формат файла")
                        println("Первая строчка - точность (eps)")
                        println("Вторая строчка - размер матрицы (n)")
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
                            break@loop
                        } catch (e: Exception) {
                            e.printStackTrace()
                            println("Возникла ошибка при вводе из файла. Проверьте имя файла и его формат.")
                            println(DIVIDER)
                        }
                    }
                }
                0 -> {
                    println("Выход из программы...")
                    exitProcess(0)
                }
                else -> throw Exception()
            }
        } catch (e: Exception) {
            println("Вы ввели неверное число. Попробуйте еще раз.")
            println(DIVIDER)
        }
    }

    println("РЕШАЮ...")

    val solver = GaussSeidel(eps, coefficients!!, size)
    val solution = solver.solve()

    println(DIVIDER)
    if (solution != null) {
        println("Заданная точность: $eps")
        println("Заданное количество элементов: $size")
        println("Матрица: ")
        for (i in 0 until size) {
            for (j in 0 until size) {
                print("${coefficients[i][j]} ")
            }
            println("| ${coefficients[i][size]}")
        }

        println("Решение системы:")
        for (i in solution.indices) {
            println("x[${i + 1}]: ${solution[i]}")
        }
        println("Количество итераций: ${solver.counter}")
    } else {
        println("Невозможно решить систему.\nНевозможно привести матрицу к виду диагонального преобладания.")
    }

}