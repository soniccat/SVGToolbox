package action.extensions

import org.apache.batik.dom.svg.AbstractSVGPathSegList
import org.apache.batik.dom.svg.SVGItem
import org.apache.batik.dom.svg.SVGPathSegItem

fun SVGItem.toAndroidString(): String {
    if (this is AbstractSVGPathSegList.SVGPathSegMovetoLinetoItem) {
        return this.pathSegTypeAsLetter + this.x.toString() + ',' + this.y.toString()
    } else if (this is AbstractSVGPathSegList.SVGPathSegCurvetoCubicItem) {
        return this.pathSegTypeAsLetter + this.x1.toString() + ',' + this.y1.toString() + ' ' + this.x2.toString() + ',' + this.y2.toString() + ' ' + this.x.toString() + ',' + this.y.toString()
    } else if (this is SVGPathSegItem) {
        return this.valueAsString.toUpperCase()
    }

    return this.valueAsString
}