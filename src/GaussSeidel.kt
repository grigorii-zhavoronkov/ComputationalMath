import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class GaussSeidel(private val eps: Double, private var input: Array<DoubleArray>, private val size: Int) {

    var counter: Int = 0

    private fun diagonalPrevalenceByCols(): Boolean {
        val changedValues = IntArray(size) { -1 }
        val diagPrevInput = Array(size){DoubleArray(size + 1)}
        for (i in 0 until size) {
            var max = Double.MIN_VALUE
            var maxCol = -1
            for (j in 0 until size) {
                if (input[i][j] > max) {
                    max = input[i][j]
                    maxCol = j
                }
            }
            if (i == maxCol) {
                changedValues[i] = maxCol
                diagPrevInput[maxCol] = input[i]
                continue
            }
            if ((input[i].sum() - abs(max) - input[i][size] < abs(max)) && (!changedValues.contains(maxCol))) {
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
                if (input[j][i] > max) {
                    max = input[j][i]
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
                sum += input[j][i]
            }
            if ((sum - abs(max) < abs(max)) && (!changedValues.contains(maxRow))) {
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
        } while (!converge(current, previous))
        return current
    }

    private fun converge(current: Array<Double>, previous: Array<Double>): Boolean {
        var norm = 0.0
        for (i in 0 until current.size) {
            norm += (current[i] - previous[i]).pow(2.0);
        }
        return (sqrt(norm) < eps);
    }
}