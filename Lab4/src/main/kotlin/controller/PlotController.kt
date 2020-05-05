package controller

import view.PlotView
import java.awt.event.MouseEvent
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.event.MouseInputAdapter
import kotlin.properties.Delegates

class PlotController(private val view: PlotView) {
    init {
        view.frame.addWindowListener(object : WindowAdapter() {
            override fun windowClosed(e: WindowEvent?) {
                super.windowClosed(e)
                view.parent.isVisible = true
            }
        })
        view.plotPane.addMouseWheelListener {
            run {
                val plotPane = view.plotPane
                val lastX = plotPane.coefX
                val lastY = plotPane.coefY
                plotPane.coefX += it.wheelRotation * 0.5
                plotPane.coefY += (plotPane.coefX - lastX) * lastY / lastX
                if (plotPane.coefX > 0.001 && plotPane.coefY > 0.001) {
                    view.frame.remove(plotPane)
                    view.frame.add(plotPane)
                    view.frame.revalidate()
                    view.frame.repaint()
                } else {
                    plotPane.coefX = lastX
                    plotPane.coefY = lastY
                }
            }
        }
        val customMouseDragListener = object : MouseInputAdapter() {
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
        view.plotPane.addMouseListener(customMouseDragListener)
        view.plotPane.addMouseMotionListener(customMouseDragListener)
    }
}