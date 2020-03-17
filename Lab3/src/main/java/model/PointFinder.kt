package model

import net.objecthunter.exp4j.ExpressionBuilder
import kotlin.math.abs

class PointFinder {
    fun findPointId(points: Array<DoubleArray>, formula: String): Int {
        val expression = ExpressionBuilder(formula).variable("x").build()
        var maxDelta = 0.0
        var idx = 1
        for (i in points.indices) {
            val x = points[i][0]
            val y = points[i][1]
            if (abs(expression.setVariable("x", x).evaluate() - y) > maxDelta) {
                maxDelta = abs(expression.setVariable("x", x).evaluate() - y)
                idx = i
            }
        }
        return idx
    }
}