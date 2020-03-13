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
import kotlin.math.abs

class SimpsonMethod(
        private val function: Expression,
        private val bottomLimit: Double,
        private val upperLimit: Double,
        private val eps: Double) {

    var n = 1
    var accuracy = 0.0
    var answer = 0.0

    fun solve() {
        var currentValue = simpson(n)
        do {
            val previousValue = currentValue
            n *= 2
            currentValue = simpson(n)
            accuracy = abs(currentValue - previousValue)
        } while (accuracy > eps)
        accuracy /= 15
        answer = currentValue
    }

    private fun simpson(n: Int): Double {
        val h = (upperLimit - bottomLimit) / n
        var sum2 = 0.0
        var sum4 = 0.0
        for (i in 1 until n step 2) {
            sum4 += function.setVariable("x", bottomLimit + h*i).evaluate()
            sum2 += function.setVariable("x", bottomLimit + h*(i+1)).evaluate()
        }
        return h/3 * (function.setVariable("x", bottomLimit).evaluate() + 4  * sum4 + 2 * sum2)
    }

}