/*
 * Copyright 2020 Grigoriy Javoronkov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.*
import kotlin.math.abs
import kotlin.random.Random

class InputReader {

    var eps: Double = 0.0
    var size: Int = 0
    var maxIterations: Int = 0
    var coefficients: Array<DoubleArray>? = null

    private fun readInputDouble(scanner: Scanner, infoMessage: String, errMessage: String): Double {
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

    private fun readInputDouble(scanner: Scanner, infoMessage: String, errMessage: String, bottomLimit: Double): Double {
        var input = 0.0
        while (true) {
            input = readInputDouble(scanner, infoMessage, errMessage)
            if (input >= bottomLimit) {
                return input
            } else {
                println(errMessage)
            }
        }
    }

    private fun readInputInt(scanner: Scanner, infoMessage: String, errMessage: String): Int {
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

    private fun readInputInt(scanner: Scanner, infoMessage: String, errMessage: String, bottomLimit: Int): Int {
        var input = 0
        while (true) {
            input = readInputInt(scanner, infoMessage, errMessage)
            if (input >= bottomLimit) {
                return input
            } else {
                println(errMessage)
            }
        }
    }

    private fun readInputInt(scanner: Scanner, infoMessage: String, errMessage: String, bottomLimit: Int, upperLimit: Int): Int {
        var input = 0
        while (true) {
            input = readInputInt(scanner, infoMessage, errMessage)
            if (input in bottomLimit..upperLimit) {
                return input
            } else {
                println(errMessage)
            }
        }
    }

    private fun configurationsReader(scanner: Scanner) {
        eps = readInputDouble(scanner, "введите точность", "Вы ввели точность в неверном формате. Попробуйте снова.")
        size = readInputInt(scanner, "Введите размер матрицы (n)", "Вы ввели размер в неверном формате, попробуйте снова. (n <= 20)", 0, 20)
        maxIterations = readInputInt(scanner, "Введите максимальное количество итераций (0 - без ограничений)", "Вы ввели количество итераций в неверном формате. Попробуйте снова.", 0)
    }

    fun consoleCoefficientReader(scanner: Scanner) {
        configurationsReader(scanner)
        println("Ввод матрицы системы")
        coefficients = Array(size){DoubleArray(size+1)}
        for (i in coefficients!!.indices) {
            for (j in 0 until coefficients!![i].size - 1) {
                coefficients!![i][j] = readInputDouble(scanner, "Введите элемент a[${i + 1}][${j + 1}]", "Вы ввели неверный коэффициент попробуйте снова")
            }
        }
        for (i in coefficients!!.indices) {
            coefficients!![i][size] = readInputDouble(scanner, "Введите элемент b[${i+1}]", "Вы ввели неверный коэффициент попробуйте снова")
        }
    }

    fun fileCoefficientReader(fileScanner: Scanner) {
        eps = fileScanner.next().toDouble()
        size = fileScanner.next().toInt()
        maxIterations = fileScanner.next().toInt()
        if (size > 20 || maxIterations < 0) {
            throw Exception()
        }
        coefficients = Array(size){DoubleArray(size+1)}

        for (i in 0 until size) {
            for (j in 0 until size) {
                coefficients!![i][j] = fileScanner.next().trim().toDouble()
            }
        }
        
        for (i in 0 until size) {
            coefficients!![i][size] = fileScanner.next().trim().toDouble()
        }
    }

    fun randomCoefficients(scanner: Scanner) {
        configurationsReader(scanner)
        val range = readInputDouble(scanner, "Введите величину разброса случайной величины", "Вы ввели величину разброса в неверном формате. Попробуйте снова.", 0.1)
        val offset = readInputDouble(scanner, "Введите смещение разброса случайной величины", "Вы ввели смещение в неверном формате. Попробуйте снова.")
        coefficients = generateRandomValues(range, offset)
    }

    private fun generateRandomValues(range: Double, offset: Double): Array<DoubleArray> {
        val random = Random(System.currentTimeMillis())
        val coefficients = Array(size){DoubleArray(size+1)}
        for (i in 0 until size) {
            coefficients[i][size] = random.nextDouble(offset, range + offset)
            coefficients[i][i] = random.nextDouble(offset, range + offset)
            for (j in 0 until size) {
                if (i != j) {
                    if (offset < 0) {
                        coefficients[i][j] = random.nextDouble(
                            -abs(coefficients[i][i]) / (size + 1),
                            (abs(coefficients[i][i])) / (size + 1)
                        )
                    } else {
                        coefficients[i][j] = random.nextDouble(
                            offset,
                            (abs(coefficients[i][i])) / (size + 1)
                        )
                    }
                }
            }
        }
        return coefficients
    }
    
    
}