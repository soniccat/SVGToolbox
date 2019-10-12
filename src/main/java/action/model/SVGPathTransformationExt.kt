package action.model

import action.extensions.forEach
import action.extensions.minX
import action.extensions.minY
import action.extensions.translate
import org.apache.batik.dom.svg.SVGItem
import org.apache.batik.dom.svg.SVGPathSegItem

fun SVGPath.applyTranslation(translation: SVGGroup.Translation) {
    applyTransformation(object : SVGPath.PathTransformation {
        override fun apply(item: SVGItem) {
            if (item is SVGPathSegItem) {
                item.translate(translation.x, translation.y)
            }
        }
    })
}

fun SVGPath.getTranslation(): SVGGroup.Translation {
    val translation = SVGGroup.Translation()
    var isFirstSet = false

    svgItems.forEach { item, i ->
        if (item.pathSegType != SVGPathSegItem.PATHSEG_CLOSEPATH) {
            if (!isFirstSet) {
                isFirstSet = true
                translation.x = item.minX()
                translation.y = item.minY()
            }

            val minX = item.minX()
            if (!minX.isNaN() && translation.x > minX) {
                translation.x = minX
            }

            val minY = item.minY()
            if (!minY.isNaN() && translation.y > minY) {
                translation.y = minY
            }
        } else {
        }
    }

    return translation
}