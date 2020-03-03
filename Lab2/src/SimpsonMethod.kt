import de.congrace.exp4j.Calculable
import kotlin.math.abs

class SimpsonMethod(
        private val function: Calculable,
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
            n*=2
            currentValue = simpson(n)
            accuracy = abs(currentValue - previousValue)
        } while (accuracy > eps)
        accuracy /= 15 // правило Рунге
        answer = currentValue
    }

    private fun simpson(n: Int): Double {
        val h = (upperLimit - bottomLimit) / n
        var sum2 = 0.0
        var sum4 = 0.0
        for (i in 1 until n step 2) {
            sum4 += function.calculate(bottomLimit + h*i)
            sum2 += function.calculate(bottomLimit + h*(i+1))
        }
        return h/3 * (function.calculate(bottomLimit) + 4  * sum4 + 2 * sum2)
    }

}