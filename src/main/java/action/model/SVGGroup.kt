package action.model

import com.intellij.psi.xml.XmlTag

class SVGGroup {
    companion object {
        val DefaultName = "NewGroup"
    }

    var name = DefaultName
    var translation = Translation()

    class Translation(var x: Float, var y: Float) {
        constructor(): this(0.0f,0.0f)

        fun inverted() = Translation(-x, -y)

        operator fun plusAssign(translation: Translation) {
            x += translation.x
            y += translation.y
        }
    }
}