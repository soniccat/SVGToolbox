package action.extensions

import org.apache.batik.dom.svg.AbstractSVGPathSegList

fun AbstractSVGPathSegList?.toAndroidString(): String {
    val buf = StringBuffer((this?.numberOfItems ?: 0) * 8)
    forEach { item, i ->
        buf.append(item.toAndroidString())
    }

    return ""
}