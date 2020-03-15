package model

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

    fun evaluate(size: Int, data: Array<DoubleArray>, type: Type) {
        when (type) {
            Type.LINEAR -> linear(size, data)
            Type.SQUARE -> square(size, data)
            Type.CUBE -> cube(size, data)
            Type.POWER -> power(size, data)
            Type.HYPERBOLA -> hyperbola(size, data)
            Type.INDICATIVE -> indicative(size, data)
            Type.LOG -> log(size, data)
            Type.EXP -> exp(size, data)
        }
        //TODO: make evaluation
        //TODO: store data
    }

    private fun linear(size: Int, data: Array<DoubleArray>) {
        var sumx = 0.0
        var sumy = 0.0
        var sumxy = 0.0
        var sumx2 = 0.0
        for (i in data.indices) {
            sumx += data[i][0]
            sumy += data[i][1]
            sumxy += data[i][0]*data[i][1]
            sumx2 += data[i][0] * data[i][0]
        }
        val a = (sumx*sumy - size*sumxy) / (sumx * sumx - size*sumx2)
        val b = (sumx*sumxy - sumx2*sumy) / (sumx*sumx - size * sumx2)
    }

    private fun square(size: Int, data: Array<DoubleArray>) {

    }

    private fun cube(size: Int, data: Array<DoubleArray>) {

    }

    private fun power(size: Int, data: Array<DoubleArray>) {

    }

    private fun hyperbola(size: Int, data: Array<DoubleArray>) {

    }

    private fun indicative(size: Int, data: Array<DoubleArray>) {

    }

    private fun log(size: Int, data: Array<DoubleArray>) {

    }

    private fun exp(size: Int, data: Array<DoubleArray>) {

    }
}