package action.extensions

import org.apache.batik.dom.svg.AbstractSVGPathSegList
import org.apache.batik.dom.svg.SVGItem
import org.apache.batik.dom.svg.SVGPathSegItem

fun AbstractSVGPathSegList?.forEach(block: (item: SVGPathSegItem, i: Int) -> Unit) {
    val itemCount = this?.numberOfItems ?: 0
    var i = 0

    while (i < itemCount) {
        val item = this!!.getItem(i) as? SVGPathSegItem
        if (item != null) {
            block(item, i)
        } else {
        }
        ++i;
    }
}