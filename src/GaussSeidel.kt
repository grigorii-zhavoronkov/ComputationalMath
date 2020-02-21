import kotlin.math.pow
import kotlin.math.sqrt

class GaussSeidel(private var eps: Double) {

    fun solve(): Array<Double> {
        val size = 5
        val input_left: Array<Array<Double>> = Array(size){ Array(size) {0.0} }
        val input_right: Array<Double> = Array(size) {0.0}
        val previous: Array<Double> = Array(size){0.0}
        val current: Array<Double> = Array(size){0.0}
        do {
            for (i in current.indices) {
                previous[i] = current[i]
            }
            for (i in current.indices) {
                var newCoef = 0.0
                for (j in 0 until i) {
                    newCoef += input_left[i][j] * current[j]
                }
                for (j in (i+1) until size) {
                    newCoef += input_left[i][j] * previous[j];
                }
                current[i] = (input_right[i] - newCoef) / input_left[i][i]
            }
        } while (!converge(current, previous))
        return current;
    }

    private fun converge(current: Array<Double>, previous: Array<Double>): Boolean {
        var norm = 0.0;
        for (i in current.indices) {
            norm += (current[i] - previous[i]).pow(2.0);
        }
        return (sqrt(norm) < eps);
    }
}