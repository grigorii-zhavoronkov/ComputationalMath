package controller

import view.PlotView
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.JFrame

class PlotController(val view: PlotView) : Controller {

    init {
        addActionListeners()
    }

    override fun addActionListeners() {
        view.frame.addWindowListener(CustomWindowCloseOperationAdapter(view.parent))
        view.plotPane.addMouseWheelListener {
            run {
                val plotPane = view.plotPane
                plotPane.coef += it.wheelRotation * 0.5
                if (plotPane.coef > 10.0) {
                    view.frame.remove(plotPane)
                    view.frame.add(plotPane)
                    view.frame.revalidate()
                    view.frame.repaint()
                } else {
                    plotPane.coef = 10.0
                }
            }
        }
    }

    private class CustomWindowCloseOperationAdapter(val parent: JFrame): WindowAdapter() {
        override fun windowClosed(e: WindowEvent?) {
            super.windowClosed(e)
            parent.isVisible = true
        }
    }
}