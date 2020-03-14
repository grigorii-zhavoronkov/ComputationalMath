package view

import java.awt.*
import javax.swing.*
import javax.swing.JFrame.EXIT_ON_CLOSE

class MainMenuView {

    val frame = JFrame() // Главный фрейм

    val fileInputButton = JButton("Чтение точек из файла")
    val pointsInputButton = JButton("Ввод точек вручную")

    private val nSpinnerModel = SpinnerNumberModel(3, 3, 100, 1)
    val nInput = JSpinner(nSpinnerModel)

    val nLabel = JLabel("Введите N (количество точек):")
    val orLabel = JLabel("или")

    init {
        addComponentsToPane(frame.contentPane)
        addSettings()
    }

    private fun addSettings() {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())

        nLabel.labelFor = nInput

        frame.isResizable = false
        frame.defaultCloseOperation = EXIT_ON_CLOSE
        frame.pack()
        frame.isVisible = true
    }

    private fun addComponentsToPane(pane: Container) {
        val baseLayout = GridLayout(0, 1)

        pane.layout = baseLayout

        val pointInputPanel = JPanel()
        val pointLayout = GridBagLayout()
        val pointConstraints = GridBagConstraints()
        pointInputPanel.layout = pointLayout

        pointConstraints.gridx = 0
        pointConstraints.gridy = 0
        pointConstraints.fill = GridBagConstraints.HORIZONTAL
        pointInputPanel.add(nLabel, pointConstraints)

        pointConstraints.gridx = 1
        pointConstraints.gridy = 0
        pointInputPanel.add(nInput, pointConstraints)

        pointConstraints.gridx = 0
        pointConstraints.gridy = 1
        pointConstraints.gridwidth = 2
        pointInputPanel.add(pointsInputButton, pointConstraints)

        pointInputPanel.border = BorderFactory.createEmptyBorder(20, 10, 0, 10)

        val orLabelPanel = JPanel()
        val orLabelLayout = FlowLayout()
        orLabelPanel.layout = orLabelLayout
        orLabelPanel.add(orLabel)
        orLabelPanel.border = BorderFactory.createEmptyBorder(10, 0, 10, 0)

        val fileButtonPanel = JPanel()
        val fileButtonLayout = FlowLayout()
        fileButtonPanel.layout = fileButtonLayout
        fileButtonPanel.add(fileInputButton)
        //fileButtonPanel.border = BorderFactory.createEmptyBorder(0, 0, 20, 0)

        pane.add(pointInputPanel)
        pane.add(orLabelPanel)
        pane.add(fileButtonPanel)
    }
}