package model

import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

class ApproximationMethod {

    enum class Type {
        LINEAR,
        SQUARE,
        CUBE,
        POWER,
        HYPERBOLA,
        INDICATIVE,
        LOG,
        EXP
    }

    fun evaluate(size: Int, data: Array<DoubleArray>, type: Type): Expression {
        return when (type) {
            Type.LINEAR -> linear(size, data)
            Type.SQUARE -> square(size, data)
            Type.CUBE -> cube(size, data)
            Type.POWER -> power(size, data)
            Type.HYPERBOLA -> hyperbola(size, data)
            Type.INDICATIVE -> indicative(size, data)
            Type.LOG -> log(size, data)
            Type.EXP -> exp(size, data)
        }
    }

    private fun linear(size: Int, data: Array<DoubleArray>): Expression {
        var sumx = 0.0
        var sumy = 0.0
        var sumxy = 0.0
        var sumx2 = 0.0
        for (i in data.indices) {
            sumx += data[i][0]
            sumy += data[i][1]
            sumxy += data[i][0] * data[i][1]
            sumx2 += data[i][0] * data[i][0]
        }
        val a: Double = (sumx*sumy - size*sumxy) / (sumx * sumx - size*sumx2)
        val b: Double = (sumx*sumxy - sumx2*sumy) / (sumx*sumx - size * sumx2)
        if (a.isFinite() && b.isFinite()) {
            return ExpressionBuilder("$a*x+$b").variable("x").build()
        } else {
            throw Exception()
        }
    }

    private fun square(size: Int, data: Array<DoubleArray>): Expression {
        var sumx = 0.0
        var sumy = 0.0
        var sumx2 = 0.0
        var sumx3 = 0.0
        var sumx4 = 0.0
        var sumxy = 0.0
        var sumx2y = 0.0
        for (i in data.indices) {
            val x = data[i][0]
            val y = data[i][1]
            sumx += x
            sumy += y
            sumx2 += x*x
            sumx3 += x*x*x
            sumx4 += x*x*x*x
            sumxy += x*y
            sumx2y += x*x*y
        }
        //TODO: DET TO FUNCTION
        val d = sumx2 * (sumx2*sumx2 - sumx*sumx3) - sumx*(sumx3*sumx2 - sumx4*sumx) + size*(sumx3*sumx3 - sumx4*sumx2)
        val da = sumy*(sumx2*sumx2 - sumx*sumx3) - sumx*(sumxy*sumx2 - sumx2y*sumx) + size*(sumxy*sumx3 - sumx2y*sumx2)
        val db = sumx2*(sumxy*sumx2 - sumx2y*sumx) - sumy*(sumx3*sumx2 - sumx4*sumx) + size*(sumx3*sumx2y - sumx4*sumxy)
        val dc = sumx2*(sumx2*sumx2y - sumx3*sumxy) - sumx*(sumx3*sumx2y-sumx4*sumxy) + sumy*(sumx3*sumx3 - sumx4*sumx2)
        val a = da/d
        val b = db/d
        val c = dc/d
        return ExpressionBuilder("$a*x^2 + $b*x + $c").variable("x").build()
    }

    private fun cube(size: Int, data: Array<DoubleArray>): Expression {
        TODO("REALIZE")
    }

    private fun power(size: Int, data: Array<DoubleArray>): Expression {
        TODO("REALIZE")
    }

    private fun hyperbola(size: Int, data: Array<DoubleArray>): Expression {
        TODO("REALIZE")
    }

    private fun indicative(size: Int, data: Array<DoubleArray>): Expression {
        TODO("REALIZE")
    }

    private fun log(size: Int, data: Array<DoubleArray>): Expression {
        TODO("REALIZE")
    }

    private fun exp(size: Int, data: Array<DoubleArray>): Expression {
        TODO("REALIZE")
    }
}