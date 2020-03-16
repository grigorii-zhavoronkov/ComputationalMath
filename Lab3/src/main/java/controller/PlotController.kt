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
    }

    private class CustomWindowCloseOperationAdapter(val parent: JFrame): WindowAdapter() {
        override fun windowClosed(e: WindowEvent?) {
            super.windowClosed(e)
            parent.isVisible = true
        }
    }
}