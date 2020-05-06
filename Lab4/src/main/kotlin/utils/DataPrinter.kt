package utils

import model.Point

class DataPrinter {
    fun printPoints(data: Array<Point>) {
        println("========== BEGIN OF OUTPUT ==========")
        data.forEach { println("${it.x} : ${it.y}") }
        println("=========== END OF OUTPUT ===========")
    }
}