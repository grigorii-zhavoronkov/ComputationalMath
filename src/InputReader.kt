import java.util.*
import kotlin.random.Random

class InputReader {

    var eps: Double = 0.0
    var size: Int = 0
    var coefficients: Array<DoubleArray>? = null

    private fun epsAndSizeReader(scanner: Scanner) {
        while (true) {
            print("Введите точность (eps): ")
            try {
                eps = scanner.next().toDouble()
                break
            } catch (e: Exception) {
                println("Вы ввели точность в неверном формате. Попробуйте снова.")
            }
        }
        while(true) {
            print("Введите размер матрицы (n): ")
            try {
                size = scanner.next().toInt()
                if (size <= 20) {
                    break
                } else {
                    throw Exception()
                }
            } catch (e: Exception) {
                println("Вы ввели размер в неверном формате, попробуйте снова. (n <= 20)")
            }
        }
    }

    fun consoleCoefficientReader(scanner: Scanner) {
        epsAndSizeReader(scanner)
        println("Ввод матрицы системы")
        coefficients = Array(size){DoubleArray(size+1)}
        for (i in coefficients!!.indices) {
            for (j in 0 until coefficients!![i].size - 1) {
                while (true) {
                    print("Введите элемент a[${i+1}][${j+1}]: ")
                    try {
                        coefficients!![i][j] = scanner.next().toDouble()
                        break
                    } catch (e: Exception) {
                        println("Вы ввели неверный коэффициент попробуйте снова")
                    }
                }
            }
        }
        for (i in coefficients!!.indices) {
            while (true) {
                print("Введите элемент b[${i+1}]: ")
                try {
                    coefficients!![i][size] = scanner.next().toDouble()
                    break
                } catch (e: Exception) {
                    println("Вы ввели неверный коэффициент попробуйте снова")
                }
            }
        }
    }

    fun fileCoefficientReader(fileScanner: Scanner) {
        eps = fileScanner.next().toDouble()
        size = fileScanner.next().toInt()
        coefficients = Array(size){DoubleArray(size+1)}

        for (i in 0 until size) {
            for (j in 0 until size) {
                coefficients!![i][j] = fileScanner.next().trim().toDouble()
            }
        }
        
        for (i in 0 until size) {
            coefficients!![i][size] = fileScanner.next().trim().toDouble()
        }
    }

    fun randomCoefficients(scanner: Scanner) {
        epsAndSizeReader(scanner)
        var range = 0.0
        var offset = 0.0
        while (true) {
            print("Введите величину разброса случайной величины: ")
            try {
                range = scanner.next().toDouble()
                if (range > 0) {
                    break
                } else {
                    throw Exception()
                }
            } catch (e: Exception) {
                println("Вы ввели величину разброса в неверном формате. Попробуйте снова.")
            }
        }
        while (true) {
            print("Введите смещение разброса случайной величины: ")
            try {
                offset = scanner.next().toDouble()
                break
            } catch (e: Exception) {
                println("Вы ввели смещение в неверном формате. Попробуйте снова.")
            }
        }
        coefficients = Array(size){DoubleArray(size+1)}
        val random = Random(System.currentTimeMillis())
        for (i in 0 until size) {
            coefficients!![i][size] = random.nextDouble(offset, range + offset)
            coefficients!![i][i] = random.nextDouble(offset, range + offset)
            for (j in 0 until size) {
                if (i != j) {
                    coefficients!![i][j] = random.nextDouble(offset / (size + 1), (coefficients!![i][i]) / (size + 1));
                }
            }

        }
    }
    
    
}