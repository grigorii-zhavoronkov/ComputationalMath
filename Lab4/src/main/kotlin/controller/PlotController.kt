package controller

import view.PlotView
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent

class PlotController(private val view: PlotView) {
    init {
        view.frame.addWindowListener(object : WindowAdapter() {
            override fun windowClosed(e: WindowEvent?) {
                super.windowClosed(e)
                view.parent.isVisible = true
            }
        })
    }
}