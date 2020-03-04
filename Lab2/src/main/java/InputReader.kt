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

    fun readInputFormula(infoMessage: String, errMessage: String): Expression {
        var expression: Expression
        while (true) {
            print("${infoMessage.capitalize()}: ")
            try {
                expression = ExpressionBuilder(scanner.nextLine()).variable("x").build()
                return expression
            } catch (e: Exception) {
                println(errMessage.capitalize())
            }
        }
    }
}