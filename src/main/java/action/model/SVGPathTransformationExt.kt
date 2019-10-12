package action.model

import action.extensions.forEach
import org.apache.batik.dom.svg.SVGItem
import org.apache.batik.dom.svg.SVGPathSegItem

fun SVGPath.applyTranslation(translation: SVGGroup.Translation) {
    applyTransformation(object : SVGPath.PathTransformation {
        override fun apply(item: SVGItem) {
            if (item is SVGPathSegItem) {
                item.x += translation.x
                item.y += translation.y
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
                translation.x = item.x
                translation.y = item.y
            } else {
                if (translation.x > item.x) {
                    translation.x = item.x
                }

                if (translation.y > item.y) {
                    translation.y = item.y
                }
            }
        } else {
        }
    }

    return translation
}