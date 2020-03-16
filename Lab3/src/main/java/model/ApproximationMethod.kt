package model

import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import kotlin.math.abs
import kotlin.math.ln
import kotlin.math.pow

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
        val a: Double = (sumx*sumy - size*sumxy) / (sumx.pow(2) - size * sumx2)
        val b: Double = (sumx*sumxy - sumx2*sumy) / (sumx.pow(2) - size * sumx2)
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

        val matrix = Array(3) {DoubleArray(3)}
        matrix[0][0] = sumx2
        matrix[0][1] = sumx
        matrix[0][2] = size*1.0
        matrix[1][0] = sumx3
        matrix[1][1] = sumx2
        matrix[1][2] = sumx
        matrix[2][0] = sumx4
        matrix[2][1] = sumx3
        matrix[2][2] = sumx2
        val d = determinant(matrix)

        matrix[0][0] = sumy
        matrix[1][0] = sumxy
        matrix[2][0] = sumx2y
        val da = determinant(matrix)

        matrix[0][0] = sumx2
        matrix[1][0] = sumx3
        matrix[2][0] = sumx4
        matrix[0][1] = sumy
        matrix[1][1] = sumxy
        matrix[2][1] = sumx2y
        val db = determinant(matrix)

        matrix[0][1] = sumx
        matrix[1][1] = sumx2
        matrix[2][1] = sumx3
        matrix[0][2] = sumy
        matrix[1][2] = sumxy
        matrix[2][2] = sumx2y
        val dc = determinant(matrix)
        val a = da/d
        val b = db/d
        val c = dc/d
        if (a.isFinite() && b.isFinite() && c.isFinite()) {
            return ExpressionBuilder("$a*x^2 + $b*x + $c").variable("x").build()
        } else {
            throw Exception()
        }
    }

    private fun cube(size: Int, data: Array<DoubleArray>): Expression {
        var sumx = 0.0
        var sumx2 = 0.0
        var sumx3 = 0.0
        var sumx4 = 0.0
        var sumx5 = 0.0
        var sumx6 = 0.0
        var sumy = 0.0
        var sumxy = 0.0
        var sumx2y = 0.0
        var sumx3y = 0.0
        for (i in data.indices) {
            val x = data[i][0]
            val y = data[i][1]
            sumx += x
            sumx2 += x.pow(2)
            sumx3 += x.pow(3)
            sumx4 += x.pow(4)
            sumx5 += x.pow(5)
            sumx6 += x.pow(6)
            sumy += y
            sumxy += x * y
            sumx2y += x.pow(2) * y
            sumx3y += x.pow(3) * y
        }

        val matrix = Array(4) {DoubleArray(4)}
        matrix[0][0] = sumx3
        matrix[0][1] = sumx2
        matrix[0][2] = sumx
        matrix[0][3] = size * 1.0
        matrix[1][0] = sumx4
        matrix[1][1] = sumx3
        matrix[1][2] = sumx2
        matrix[1][3] = sumx
        matrix[2][0] = sumx5
        matrix[2][1] = sumx4
        matrix[2][2] = sumx3
        matrix[2][3] = sumx2
        matrix[3][0] = sumx6
        matrix[3][1] = sumx5
        matrix[3][2] = sumx4
        matrix[3][3] = sumx3
        val d = determinant(matrix)

        matrix[0][0] = sumy
        matrix[1][0] = sumxy
        matrix[2][0] = sumx2y
        matrix[3][0] = sumx3y
        val da = determinant(matrix)

        matrix[0][0] = sumx3
        matrix[1][0] = sumx4
        matrix[2][0] = sumx5
        matrix[3][0] = sumx6
        matrix[0][1] = sumy
        matrix[1][1] = sumxy
        matrix[2][1] = sumx2y
        matrix[3][1] = sumx3y
        val db = determinant(matrix)

        matrix[0][1] = sumx2
        matrix[1][1] = sumx3
        matrix[2][1] = sumx4
        matrix[3][1] = sumx5
        matrix[0][2] = sumy
        matrix[1][2] = sumxy
        matrix[2][2] = sumx2y
        matrix[3][2] = sumx3y
        val dc = determinant(matrix)

        matrix[0][2] = sumx
        matrix[1][2] = sumx2
        matrix[2][2] = sumx3
        matrix[3][2] = sumx4
        matrix[0][3] = sumy
        matrix[1][3] = sumxy
        matrix[2][3] = sumx2y
        matrix[3][3] = sumx3y
        val dd = determinant(matrix)

        val pa = da/d
        val pb = db/d
        val pc = dc/d
        val pd = dd/d

        if (pa.isFinite() && pb.isFinite() && pc.isFinite() && pd.isFinite()) {
            return ExpressionBuilder("$pa * x^3 + $pb * x^2 + $pc * x + $pd").variable("x").build()
        } else {
            throw Exception()
        }
    }

    private fun power(size: Int, data: Array<DoubleArray>): Expression {
        var sumx = 0.0
        var sumx2 = 0.0
        var sumy = 0.0
        var sumxy = 0.0
        for (i in data.indices) {
            val x = data[i][0]
            val y = data[i][1]
            sumx += ln(x)
            sumy += ln(y)
            sumx2 = ln(x).pow(2)
            sumxy = ln(x) * ln(y)
        }
        val b = (size * sumxy - sumx * sumy) / (size * sumx2 - sumx.pow(2))
        val a = kotlin.math.exp(1/size * sumy - b/size * sumx)
        if (a.isFinite() && b.isFinite()) {
            return ExpressionBuilder("$a * x ^ $b").variable("x").build()
        } else {
            throw Exception()
        }
    }

    private fun hyperbola(size: Int, data: Array<DoubleArray>): Expression {
        var sumx = 0.0
        var sumx2 = 0.0
        var sumyx = 0.0
        var sumy = 0.0
        for (i in data.indices) {
            val x = data[i][0]
            val y = data[i][1]
            sumx += 1/x
            sumx2 += 1/ x.pow(2)
            sumyx += y/x
            sumy += y
        }

        val b = (size * sumyx - sumx * sumy) / (size * sumx2 - sumx.pow(2))
        val a = 1/size * sumy - b/size * sumx
        if (a.isFinite() && b.isFinite()) {
            return ExpressionBuilder("$a + $b/x").variable("x").build()
        } else {
            throw Exception()
        }
    }

    private fun indicative(size: Int, data: Array<DoubleArray>): Expression {
        var sumx = 0.0
        var sumx2 = 0.0
        var sumlny = 0.0
        var sumxlny = 0.0
        for (i in data.indices) {
            val x = data[i][0]
            val y = data[i][1]
            sumx += x
            sumx2 += x.pow(2)
            sumlny += ln(y)
            sumxlny += x * ln(y)
        }

        val b = kotlin.math.exp((size * sumxlny - sumx * sumlny) / (size * sumx2 - sumx.pow(2)))
        val a = kotlin.math.exp(1/size * sumlny - ln(b)/size * sumx)

        if (a.isFinite() && b.isFinite()) {
            return ExpressionBuilder("$a * $b ^ x").variable("x").build()
        } else {
            throw Exception()
        }
    }

    private fun log(size: Int, data: Array<DoubleArray>): Expression {
        var sumylnx = 0.0
        var sumlnx = 0.0
        var sumy = 0.0
        var sumln2x = 0.0
        for (i in data.indices) {
            val x = data[i][0]
            val y = data[i][1]
            sumylnx += y * ln(x)
            sumlnx += ln(x)
            sumy += y
            sumln2x += ln(x).pow(2)
        }

        val b = (size * sumylnx - sumlnx * sumy) / (size * sumln2x * sumlnx.pow(2))
        val a = 1/size * sumy - b/size * sumlnx

        if (a.isFinite() && b.isFinite()) {
            return ExpressionBuilder("$a + $b * log(x)").variable("x").build()
        } else {
            throw Exception()
        }
    }

    private fun exp(size: Int, data: Array<DoubleArray>): Expression {
        var sumxlny = 0.0
        var sumx = 0.0
        var sumlny = 0.0
        var sumx2 = 0.0

        for (i in data.indices) {
            val x = data[i][0]
            val y = data[i][1]
            sumxlny += x * ln(y)
            sumx += x
            sumlny += ln(y)
            sumx2 += x.pow(2)
        }

        val b = (size * sumxlny - sumx * sumlny) / (size * sumx2 - sumx.pow(2))
        val a = 1/size * sumlny - b/size * sumx
        if (a.isFinite() && b.isFinite()) {
            return ExpressionBuilder("e^($a + $b * x)").variable("x").build()
        } else {
            throw Exception()
        }
    }

    private fun determinant(matrix: Array<DoubleArray>): Double {
        val eps = 1e-9
        var det = 1.0
        for (i in matrix.indices) {
            var k = i
            for (j in i+1 until matrix.size) {
                if (abs(matrix[j][i]) > abs(matrix[k][i])) {
                    k = j
                }
            }
            if (abs(matrix[k][i]) < eps) {
                return 0.0
            }
            val temp = matrix[i]
            matrix[i] = matrix[k]
            matrix[k] = temp
            if (i != k) {
                det = -det
            }
            det *= matrix[i][i]
            for (j in i+1 until matrix.size) {
                matrix[i][j] /= matrix[i][i]
            }
            for (j in matrix.indices) {
                if (j != i && abs(matrix[j][i]) > eps) {
                    for (l in i+1 until matrix.size) {
                        matrix[j][l] -= matrix[i][l] * matrix[j][i]
                    }
                }
            }
        }
        return det
    }
}