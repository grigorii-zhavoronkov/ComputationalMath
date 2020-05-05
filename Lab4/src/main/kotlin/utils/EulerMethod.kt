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
        while (x < endOfSegment) {
            val point = Point(x, y)
            result.add(point)
            x += accuracy
            y += accuracy * formula.expression.setVariable("x", x).setVariable("y", y).evaluate()
        }
        return result.toTypedArray()
    }
}