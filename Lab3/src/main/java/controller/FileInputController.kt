package controller

import view.FileInputView
import view.PointsInputView
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.util.*
import javax.swing.JOptionPane
import kotlin.collections.ArrayList

class FileInputController(private val view: FileInputView) : Controller {

    init {
        addActionListeners()
    }

    override fun addActionListeners() {
        view.fileChooser.addWindowListener(CustomWindowListener(view))
    }

    private class CustomWindowListener(val view: FileInputView): WindowAdapter() {
        override fun windowClosed(e: WindowEvent?) {
            super.windowClosed(e)
            if (view.selectedFile != null) {
                try {
                    val fileReader = Scanner(view.selectedFile!!)
                    var counter = 0
                    val points = ArrayList<Pair<Double, Double>>()
                    while (fileReader.hasNextLine()) {
                        val current = fileReader.nextLine().split(" ")
                        val x = current[0].toDouble()
                        val y = current[1].toDouble()
                        points.add(Pair(x, y))
                        counter++
                    }
                    val newView = PointsInputView(view.parent, counter)
                    PointsInputController(newView)
                    counter = 0
                    for (point in points) {
                        newView.changeTableValue(counter, 0, point.first.toString())
                        newView.changeTableValue(counter, 1, point.second.toString())
                        counter++
                    }
                } catch (e: Exception) {
                    JOptionPane.showMessageDialog(view.parent,
                            "Возникла ошибка при чтении файла. Проверьте его формат и права доступа.",
                            "Ошибка!",
                            JOptionPane.WARNING_MESSAGE)
                }
            }
        }
    }
}