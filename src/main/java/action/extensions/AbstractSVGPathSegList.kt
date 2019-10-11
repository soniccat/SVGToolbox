package action.extensions

import org.apache.batik.dom.svg.AbstractSVGPathSegList
import org.apache.batik.dom.svg.SVGItem

fun AbstractSVGPathSegList?.forEach(block: (item: SVGItem, i: Int) -> Unit) {
    val itemCount = this?.numberOfItems ?: 0
    var i = 0

    while (i < itemCount) {
        val item = this!!.getItem(i) as SVGItem
        block(item, i)
        ++i;
    }
}