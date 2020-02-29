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

import kotlin.math.abs

class GaussSeidel(
    private val eps: Double,
    private var input: Array<DoubleArray>,
    private val size: Int,
    private val maxIterations: Int) {

    var counter: Int = 0
    val accuracy: DoubleArray = DoubleArray(size)

    private fun diagonalPrevalenceByCols(): Boolean {
        val changedValues = IntArray(size) { -1 }
        val diagPrevInput = Array(size){DoubleArray(size + 1)}
        for (i in 0 until size) {
            var max = Double.MIN_VALUE
            var maxCol = -1
            for (j in 0 until size) {
                if (abs(input[i][j]) > max) {
                    max = abs(input[i][j])
                    maxCol = j
                }
            }
            if (i == maxCol) {
                changedValues[i] = maxCol
                diagPrevInput[maxCol] = input[i]
                continue
            }
            var sum = 0.0
            for (j in 0 until size) {
                sum += abs(input[i][j])
            }
            if ((sum - max <= max) && (!changedValues.contains(maxCol))) {
                changedValues[i] = maxCol
                diagPrevInput[maxCol] = input[i]
            } else {
                return false
            }
        }
        input = diagPrevInput
        return true
    }

    private fun diagonalPrevalenceByRows(): Boolean {
        val changedValues = IntArray(size) { -1 }
        val diagPrevInput = Array(size){DoubleArray(size + 1)}
        for (i in 0 until size) {
            var max = Double.MIN_VALUE
            var maxRow = -1
            for (j in 0 until size) {
                if (abs(input[j][i]) > max) {
                    max = abs(input[j][i])
                    maxRow = j
                }
            }
            if (i == maxRow) {
                changedValues[i] = maxRow
                for (j in 0 until size) {
                    diagPrevInput[j][maxRow] = input[j][i]
                }
                continue
            }
            var sum = 0.0
            for (j in 0 until size) {
                sum += abs(input[j][i])
            }
            if ((sum - max <= max) && (!changedValues.contains(maxRow))) {
                changedValues[i] = maxRow
                for (k in 0 until size) {
                    diagPrevInput[maxRow][k] = input[i][k];
                }
            } else {
                return false
            }
        }
        for (i in 0 until size) {
            diagPrevInput[i][size] = input[i][size]
        }
        input = diagPrevInput
        return true
    }

    fun solve(): Array<Double>? {
        if (!diagonalPrevalenceByCols() && !diagonalPrevalenceByRows()) {
            return null
        }
        val inputLeft: Array<DoubleArray> = Array(size) { DoubleArray(size) }
        for (i in 0 until size) {
            for (j in 0 until size) {
                inputLeft[i][j] = input[i][j]
            }
        }
        val inputRight: Array<Double> = Array(size) {0.0}
        for (i in 0 until size) {
            inputRight[i] = input[i][size]
        }
        val previous: Array<Double> = Array(size){0.0}
        val current: Array<Double> = inputRight.copyOf()
        do {

            counter++
            for (i in 0 until size) {
                previous[i] = current[i]
            }
            for (i in 0 until size) {
                var newCoef = 0.0
                for (j in 0 until i) {
                    newCoef += (inputLeft[i][j] * current[j])
                }
                for (j in (i+1) until size) {
                    newCoef += (inputLeft[i][j] * previous[j])
                }
                current[i] = (inputRight[i] - newCoef) / inputLeft[i][i]
            }
        } while (!converge(current, previous) && !(maxIterations != 0 && maxIterations <= counter))
        return current
    }

    private fun converge(current: Array<Double>, previous: Array<Double>): Boolean {
        var converge = true
        for (i in 0 until current.size) {
            accuracy[i] = abs(current[i] - previous[i])
            if (abs(current[i] - previous[i]) > eps) {
                converge = false
            }
        }
        return converge
    }
}