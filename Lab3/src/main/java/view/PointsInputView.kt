package view

import java.awt.*
import java.util.*
import javax.swing.*

class PointsInputView(val parent: JFrame, val rowSize: Int) {
    val frame = JFrame("Ввод точек")
    lateinit var table: JTable
        private set

    val linearApproximation = JRadioButton("Линейная аппроксимация")
    val squareApproximation = JRadioButton("Квадратичная аппроксимация")
    val cubeApproximation = JRadioButton("Кубическая аппроксимация")
    val powerApproximation = JRadioButton("Степенная аппроксимация")
    val hyperbolaApproximation = JRadioButton("Гиперболическая аппроксимация")
    val indicativeApproximation = JRadioButton("Показательная аппроксимация")
    val logApproximation = JRadioButton("Логарифмическая аппроксимация")
    val expApproximation = JRadioButton("Экспоненциальная аппроксимация")

    val backButton = JButton("Назад")
    val nextButton = JButton("Построить график")

    init {
        initTable(rowSize)
        addComponentsToPane(frame.contentPane)
        addSettings()
    }

    private fun addSettings() {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())

        frame.isResizable = false
        frame.defaultCloseOperation = JDialog.DISPOSE_ON_CLOSE
        frame.pack()
        frame.isAlwaysOnTop = true
        frame.isVisible = true
    }

    private fun addComponentsToPane(pane: Container) {
        val base = JPanel()
        base.layout = GridBagLayout()
        val baseConstraints = GridBagConstraints()

        val tablePanel = JPanel()
        tablePanel.layout = FlowLayout()
        tablePanel.border = BorderFactory.createEmptyBorder(20, 10, 0, 10)
        val tableScrollPane = JScrollPane(table)
        tableScrollPane.preferredSize = Dimension(300, 300)
        tablePanel.add(tableScrollPane)

        val functionTypePanel = JPanel()
        functionTypePanel.layout = GridLayout(0, 1)
        functionTypePanel.border = BorderFactory.createEmptyBorder(10, 0, 10, 0)

        val functionButtonGroup = ButtonGroup()
        functionButtonGroup.add(linearApproximation)
        functionButtonGroup.add(squareApproximation)
        functionButtonGroup.add(cubeApproximation)
        functionButtonGroup.add(powerApproximation)
        functionButtonGroup.add(hyperbolaApproximation)
        functionButtonGroup.add(indicativeApproximation)
        functionButtonGroup.add(logApproximation)
        functionButtonGroup.add(expApproximation)

        functionTypePanel.add(linearApproximation)
        functionTypePanel.add(squareApproximation)
        functionTypePanel.add(cubeApproximation)
        functionTypePanel.add(powerApproximation)
        functionTypePanel.add(hyperbolaApproximation)
        functionTypePanel.add(indicativeApproximation)
        functionTypePanel.add(logApproximation)
        functionTypePanel.add(expApproximation)

        linearApproximation.isSelected = true

        val buttonPanel = JPanel()
        buttonPanel.layout = GridBagLayout()

        val buttonConstraints = GridBagConstraints()

        buttonConstraints.fill = GridBagConstraints.HORIZONTAL
        buttonConstraints.gridx = 0
        buttonConstraints.gridy = 0
        buttonPanel.add(backButton, buttonConstraints)

        buttonConstraints.gridx = 2
        buttonConstraints.gridy = 0
        buttonPanel.add(nextButton, buttonConstraints)

        baseConstraints.fill = GridBagConstraints.HORIZONTAL
        baseConstraints.gridx = 0
        baseConstraints.gridy = 0
        base.add(tablePanel, baseConstraints)

        baseConstraints.gridy = 1
        base.add(functionTypePanel, baseConstraints)

        baseConstraints.gridy = 2
        base.add(buttonPanel, baseConstraints)

        pane.add(base)
    }

    public fun changeTableValue(row: Int, column: Int, value: Any) {
        table.setValueAt(value, row, column)
    }

    private fun initTable(rowSize: Int) {
        val columnNames = Vector<String>(2)
        columnNames.addElement("X")
        columnNames.addElement("Y")
        val data = Vector<Vector<String>>(rowSize)
        for (i in 0 until rowSize) {
            val preData = Vector<String>(2)
            preData.addElement("0")
            preData.addElement("0")
            data.addElement(preData)
        }

        table = JTable(data, columnNames)
        table.cellSelectionEnabled = true
    }
}