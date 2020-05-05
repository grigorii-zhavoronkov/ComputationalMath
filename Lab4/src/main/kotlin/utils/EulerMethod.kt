package utils

import model.Formula
import model.Point

class EulerMethod(private val formula: Formula,
                  private var x: Double,
                  private var y: Double,
                  private val endOfSegment: Double,
                  private val accuracy: Double) {

    fun evaluate(): Array<Point> {
        val result = ArrayList<Point>()
        println("========== BEGIN OF OUTPUT ==========")
        for (i in 0 .. ((endOfSegment - x)/accuracy).toInt()) {
            val point = Point(x, y)
            println("$x : $y")
            result.add(point)
            y += accuracy * formula.expression.setVariable("x", x).setVariable("y", y).evaluate()
            x += accuracy
        }
        println("=========== END OF OUTPUT ===========")
        return result.toTypedArray()
    }
}