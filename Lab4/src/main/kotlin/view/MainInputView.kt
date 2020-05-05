package view

import java.awt.Container
import java.awt.FlowLayout
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.*

class MainInputView {
    val frame = JFrame()
    val equationLabel = JLabel("Урванение: ")
    val firstArgumentLabel = JLabel("y' + ")
    val secondArgumentLabel = JLabel("f(x,y)") // input of f(x, y)
    val thirdArgumentLabel = JLabel(" = 0")
    // firstArgumentLabel + secondArgumentLabel + thirdArgumentLabel = whole equation
    val inputFunctionLabel = JLabel("Введите f(x, y): ")
    val inputFunctionTextField = JTextField()

    val xInputLabel = JLabel("x0: ")
    val xInputTextField = JTextField()
    val yInputLabel = JLabel("y0: ")
    val yInputTextField = JTextField()

    val endOfSegmentLabel = JLabel("Конец отрезка: ")
    val endOfSegmentTextField = JTextField()

    val accuracyLabel = JLabel("Точность: ")
    val accuracyTextField = JTextField()

    val calculateButton = JButton("Вычислить")

    init {
        addComponentsToPane(frame.contentPane)
        inputFunctionLabel.labelFor = inputFunctionTextField
        addSettingsToFrame()
    }

    private fun addComponentsToPane(pane: Container) {
        // init of main panel
        pane.layout = GridBagLayout()
        val paneConstraints = GridBagConstraints()
        setGridBagConstraintsToDefault(paneConstraints)

        // constraints of inputs
        val inputFunctionPanel = JPanel()
        inputFunctionPanel.layout = FlowLayout()
        inputFunctionPanel.add(inputFunctionLabel)
        inputFunctionTextField.columns = 13
        inputFunctionPanel.add(inputFunctionTextField)

        // equation view panel
        val equationViewPanel = JPanel()
        equationViewPanel.layout = GridBagLayout()
        val equationPanelConstraints = GridBagConstraints()
        setGridBagConstraintsToDefault(equationPanelConstraints)
        equationViewPanel.add(equationLabel, equationPanelConstraints)
        equationPanelConstraints.gridx = 1
        equationViewPanel.add(firstArgumentLabel, equationPanelConstraints)
        equationPanelConstraints.gridx = 2
        equationViewPanel.add(secondArgumentLabel, equationPanelConstraints)
        equationPanelConstraints.gridx = 3
        equationViewPanel.add(thirdArgumentLabel, equationPanelConstraints)

        // add other input panel
        val otherInputPanel = JPanel()
        otherInputPanel.layout = GridBagLayout()
        val otherInputPanelConstraints = GridBagConstraints()
        setGridBagConstraintsToDefault(otherInputPanelConstraints)

        val xInputPanel = JPanel()
        xInputPanel.layout = FlowLayout()
        xInputPanel.add(xInputLabel)
        xInputTextField.columns = 15
        xInputPanel.add(xInputTextField)

        val yInputPanel = JPanel()
        yInputPanel.layout = FlowLayout()
        yInputPanel.add(yInputLabel)
        yInputTextField.columns = 15
        yInputPanel.add(yInputTextField)

        val endOfSegmentPanel = JPanel()
        endOfSegmentPanel.layout = FlowLayout()
        endOfSegmentPanel.add(endOfSegmentLabel)
        endOfSegmentTextField.columns = 8
        endOfSegmentPanel.add(endOfSegmentTextField)

        val accuracyPanel = JPanel()
        accuracyPanel.layout = FlowLayout()
        accuracyPanel.add(accuracyLabel)
        accuracyTextField.columns = 11
        accuracyPanel.add(accuracyTextField)

        otherInputPanel.add(xInputPanel, otherInputPanelConstraints)
        otherInputPanelConstraints.gridy = 1
        otherInputPanel.add(yInputPanel, otherInputPanelConstraints)
        otherInputPanelConstraints.gridy = 2
        otherInputPanel.add(endOfSegmentPanel, otherInputPanelConstraints)
        otherInputPanelConstraints.gridy = 3
        otherInputPanel.add(accuracyPanel, otherInputPanelConstraints)

        // add calculation panel
        val calculationPanel = JPanel()
        calculationPanel.layout = FlowLayout()
        calculationPanel.add(calculateButton)

        // add all panels to main panel
        pane.add(inputFunctionPanel, paneConstraints)
        paneConstraints.gridy = 1
        pane.add(equationViewPanel, paneConstraints)
        paneConstraints.gridy = 2
        pane.add(otherInputPanel, paneConstraints)
        paneConstraints.gridy = 3
        pane.add(calculationPanel, paneConstraints)

        // adding borders
        inputFunctionPanel.border = BorderFactory.createEmptyBorder(40, 20, 10, 20)
        equationViewPanel.border = BorderFactory.createEmptyBorder(10, 20, 10, 20)
        otherInputPanel.border = BorderFactory.createEmptyBorder(10, 20, 10, 20)
        xInputPanel.border = BorderFactory.createEmptyBorder(0, 20, 5, 20)
        yInputPanel.border = BorderFactory.createEmptyBorder(5, 20, 5, 20)
        endOfSegmentPanel.border = BorderFactory.createEmptyBorder(5, 20, 5, 20)
        accuracyPanel.border = BorderFactory.createEmptyBorder(5, 20, 0, 20)
        calculationPanel.border = BorderFactory.createEmptyBorder(10, 20, 40, 20)
    }

    private fun setGridBagConstraintsToDefault(gridBagConstraints: GridBagConstraints) {
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL
        gridBagConstraints.gridx = 0
        gridBagConstraints.gridy = 0
    }

    private fun addSettingsToFrame() {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())

        frame.isResizable = false
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.pack()
        frame.isVisible = true
    }
}