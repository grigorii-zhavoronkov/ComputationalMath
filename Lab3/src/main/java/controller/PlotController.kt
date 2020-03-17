package controller

import view.PlotView
import java.awt.event.MouseEvent
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.JFrame
import javax.swing.event.MouseInputAdapter
import kotlin.properties.Delegates

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
        val customMouseDragListener = CustomMouseDragListener(view)
        view.plotPane.addMouseListener(customMouseDragListener)
        view.plotPane.addMouseMotionListener(customMouseDragListener)
    }

    private class CustomWindowCloseOperationAdapter(val parent: JFrame): WindowAdapter() {
        override fun windowClosed(e: WindowEvent?) {
            super.windowClosed(e)
            parent.isVisible = true
        }
    }

    private class CustomMouseDragListener(val view: PlotView): MouseInputAdapter() {
        private var pressedX by Delegates.notNull<Int>()
        private var pressedY by Delegates.notNull<Int>()

        override fun mousePressed(e: MouseEvent?) {
            super.mousePressed(e!!)
            pressedX = e.x
            pressedY = e.y
        }

        override fun mouseDragged(e: MouseEvent?) {
            super.mouseDragged(e!!)
            val dx = e.x - pressedX
            val dy = e.y - pressedY
            val plotPane = view.plotPane
            plotPane.centerX += dx
            plotPane.centerY += dy
            view.frame.remove(plotPane)
            view.frame.add(plotPane)
            view.frame.revalidate()
            view.frame.repaint()
            pressedX = e.x
            pressedY = e.y
        }
    }
}