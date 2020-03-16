package view

import java.awt.*
import javax.swing.*
import javax.swing.JFrame.EXIT_ON_CLOSE

class MainMenuView {
    val frame = JFrame()

    val fileInputButton = JButton("Чтение точек из файла")
    val pointsInputButton = JButton("Ввод точек вручную")

    private val nSpinnerModel = SpinnerNumberModel(4, 4, 100, 1)
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
        pane.layout = GridBagLayout()

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

        pointInputPanel.border = BorderFactory.createEmptyBorder(30, 10, 10, 10)

        val orLabelPanel = JPanel()
        val orLabelLayout = FlowLayout()
        orLabelPanel.layout = orLabelLayout
        orLabelPanel.add(orLabel)

        val fileButtonPanel = JPanel()
        val fileButtonLayout = FlowLayout()
        fileButtonPanel.layout = fileButtonLayout
        fileButtonPanel.add(fileInputButton)

        val baseConstraints = GridBagConstraints()
        baseConstraints.fill = GridBagConstraints.HORIZONTAL
        baseConstraints.gridx = 0
        baseConstraints.gridy = 0
        pane.add(pointInputPanel, baseConstraints)

        baseConstraints.gridy = 1
        pane.add(orLabelPanel, baseConstraints)

        baseConstraints.gridy = 2
        pane.add(fileButtonPanel, baseConstraints)
    }
}