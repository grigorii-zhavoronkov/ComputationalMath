import java.util.*

class InputReader {

    var eps: Double = 0.0
    var size: Int = 0
    var coefficients: Array<DoubleArray>? = null

    fun consoleCoefficientReader(scanner: Scanner) {
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
                break
            } catch (e: Exception) {
                println("Вы ввели размер в неверном формате, попробуйте снова")
            }
        }
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
    
    
}