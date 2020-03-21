package view

import java.awt.Dimension
import java.awt.FileDialog
import java.awt.FlowLayout
import java.io.File
import javax.swing.JFrame

class FileInputView(val parent: JFrame) {
    var fileChooser = FileDialog(parent,"Выберите файл", FileDialog.LOAD)
    var selectedFile: File? = null

    fun showDialog() {
        fileChooser.preferredSize = Dimension(600, 400)
        fileChooser.size = Dimension(600, 400)
        fileChooser.layout = FlowLayout(FlowLayout.CENTER, 10, 10)
        fileChooser.isResizable = true
        fileChooser.isVisible = true
        fileChooser.pack()
        val result = fileChooser.file
        if (result != null) {
            selectedFile = File(fileChooser.directory + result)
        }
        fileChooser.dispose()
    }
}