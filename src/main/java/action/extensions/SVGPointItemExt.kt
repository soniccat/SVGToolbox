package action.extensions

import org.apache.batik.dom.svg.SVGPointItem

class SVGPointItemExt {
    companion object {
        fun zero(): SVGPointItem {
            return SVGPointItem(0.0f, 0.0f)
        }
    }
}