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

import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
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
        formulas.add("8+2x-x^2")
        formulas.add("sin^2(x)")
        formulas.add("2*x^2")
        formulas.add("x/sqrt(x^4+16)")
        formulas.trimToSize()
    }

    fun serve() {
        println("Данная программа позволяет вычислить значение интеграла методом Симпсона")
        val inputReader = InputReader()
        do {
            println(DIVIDER)
            val inputType = InputType.values()[inputReader.readInputInt(mainMenu, "Неверная команда. Попробуйте снова.", 0, 2)]
            var formula: Expression
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
                            .variable("x")
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
            try {
                solver.solve()
                println(DIVIDER)
                println("Количество разбиений: ${solver.n}")
                println("Погрешность вычислений: +-${solver.accuracy}")
                println("Ответ: ${solver.answer}")
            } catch (e: Exception) {
                println("Интеграл не может быть вычислен. Попробуйте снова.")
            }
        } while (inputType != InputType.EXIT)
    }
}