package view

import model.Formula
import model.Point
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.plot.PlotOrientation
import org.jfree.chart.plot.XYPlot
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer
import org.jfree.data.xy.XYDataset
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection
import java.awt.Container
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.JFrame
import javax.swing.UIManager


class PlotView(val points: Array<Point>,
               val formula: Formula,
               val parent: JFrame) {

    val frame = JFrame()

    var minY = Double.MAX_VALUE
    var minX = Double.MAX_VALUE

    var maxY = Double.MIN_VALUE
    var maxX = Double.MIN_VALUE

    private val graphAccuracy = 100.0

    init {
        calculateBounds()
        addComponentsToPane(frame.contentPane)
        addSettings()
    }

    private fun addSettings() {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())

        frame.isResizable = false
        frame.defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
        frame.pack()
        frame.isVisible = true
    }

    private fun addComponentsToPane(pane: Container) {
        pane.layout = GridBagLayout()
        val paneConstraints = GridBagConstraints()
        paneConstraints.anchor = GridBagConstraints.NORTHWEST
        paneConstraints.fill = GridBagConstraints.HORIZONTAL
        paneConstraints.gridx = 0
        paneConstraints.gridy = 0
        val chartPanel = createChart()
        chartPanel.size = Dimension(1000, 1000)
        pane.add(chartPanel, paneConstraints)
    }

    private fun calculateBounds() {
        for (i in points.indices) {
            if (points[i].x > maxX) {
                maxX = points[i].x
            }
            if (points[i].x < minX) {
                minX = points[i].x
            }
            if (points[i].y > maxY) {
                maxY = points[i].y
            }
            if (points[i].y < minY) {
                minY = points[i].y
            }
        }
    }

    private fun createChart(): ChartPanel {
        val dataset = createDataset()
        val chart = ChartFactory.createXYLineChart("", "X", "Y", dataset, PlotOrientation.VERTICAL, true, true, false)
        val plot = chart.xyPlot as XYPlot
        val renderer = XYLineAndShapeRenderer()

        renderer.setSeriesLinesVisible(0, true)
        renderer.setSeriesShapesVisible(0, false)
        renderer.setSeriesLinesVisible(1, false)
        renderer.setSeriesShapesVisible(1, true)

        plot.renderer = renderer
        return ChartPanel(chart)
    }

    private fun createDataset(): XYDataset {
        val dataset = XYSeriesCollection()
        dataset.addSeries(createPlot())
        dataset.addSeries(createDots())
        return dataset
    }

    private fun createPlot(): XYSeries {
        val series = XYSeries(formula.string)

        var x = minX-1
        while (x <= (maxX+1)) {
            series.add(x, formula.expression.setVariable("x", x).evaluate())
            x += (maxX - minX) / graphAccuracy
        }
        return series
    }

    private fun createDots(): XYSeries {
        val series = XYSeries("Точки")

        points.forEach {
            series.add(it.x, it.y)
        }
        return series
    }
}