package action.extensions

import org.apache.batik.dom.svg.AbstractSVGPathSegList
import org.apache.batik.dom.svg.SVGItem
import org.apache.batik.dom.svg.SVGPathSegItem
import org.apache.batik.dom.svg.SVGPointItem
import kotlin.math.min

/**
SVGPathSegArcItem in AbstractSVGPathSegList
SVGPathSegCurvetoCubicItem in AbstractSVGPathSegList
SVGPathSegCurvetoCubicSmoothItem in AbstractSVGPathSegList
SVGPathSegCurvetoQuadraticItem in AbstractSVGPathSegList
SVGPathSegCurvetoQuadraticSmoothItem in AbstractSVGPathSegList
SVGPathSegGenericItem in AbstractSVGNormPathSegList
SVGPathSegLinetoHorizontalItem in AbstractSVGPathSegList
SVGPathSegLinetoVerticalItem in AbstractSVGPathSegList
SVGPathSegMovetoLinetoItem in AbstractSVGPathSegList
SVGPathSegItem
SVGPointItem
 */

fun SVGItem.toAndroidString(): String {
    if (this is AbstractSVGPathSegList.SVGPathSegMovetoLinetoItem) {
        return this.pathSegTypeAsLetter + this.x.toShortString() + ',' + this.y.toShortString()
    } else if (this is AbstractSVGPathSegList.SVGPathSegLinetoVerticalItem) {
        return this.pathSegTypeAsLetter + this.y
    } else if (this is AbstractSVGPathSegList.SVGPathSegLinetoHorizontalItem) {
        return this.pathSegTypeAsLetter + this.x
    } else if (this is AbstractSVGPathSegList.SVGPathSegCurvetoQuadraticSmoothItem) {
        return this.pathSegTypeAsLetter + this.x.toShortString() + ',' + this.y.toShortString()
    } else if (this is AbstractSVGPathSegList.SVGPathSegCurvetoQuadraticItem) {
        return this.pathSegTypeAsLetter + this.x1.toShortString() + ',' + this.y1.toShortString() + this.x.toShortString() + ',' + this.y.toShortString()
    } else if (this is AbstractSVGPathSegList.SVGPathSegCurvetoCubicSmoothItem) {
        return this.pathSegTypeAsLetter + this.x2.toShortString() + ',' + this.y2.toShortString() + this.x.toShortString() + ',' + this.y.toShortString()
    } else if (this is AbstractSVGPathSegList.SVGPathSegCurvetoCubicItem) {
        return this.pathSegTypeAsLetter + this.x1.toShortString() + ',' + this.y1.toShortString() + ' ' + this.x2.toShortString() + ',' + this.y2.toShortString() + ' ' + this.x.toShortString() + ',' + this.y.toShortString()
    } else if (this is SVGPathSegItem) {
        return this.valueAsString.toUpperCase()
    }

    return this.valueAsString
}

fun SVGItem.minX(): Float {
    if (this is AbstractSVGPathSegList.SVGPathSegMovetoLinetoItem) return x
    if (this is AbstractSVGPathSegList.SVGPathSegLinetoVerticalItem) return Float.NaN
    if (this is AbstractSVGPathSegList.SVGPathSegLinetoHorizontalItem) return x
    if (this is AbstractSVGPathSegList.SVGPathSegCurvetoQuadraticSmoothItem) return x
    if (this is AbstractSVGPathSegList.SVGPathSegCurvetoQuadraticItem) return min(x, x1)
    if (this is AbstractSVGPathSegList.SVGPathSegCurvetoCubicSmoothItem) return min(x, x2)
    if (this is AbstractSVGPathSegList.SVGPathSegCurvetoCubicItem) return min(x, min(x1, x2))
    if (this is SVGPointItem) return x

    return Float.NaN
}

fun SVGItem.minY(): Float {
    if (this is AbstractSVGPathSegList.SVGPathSegMovetoLinetoItem) return y
    if (this is AbstractSVGPathSegList.SVGPathSegLinetoVerticalItem) return y
    if (this is AbstractSVGPathSegList.SVGPathSegLinetoHorizontalItem) return Float.NaN
    if (this is AbstractSVGPathSegList.SVGPathSegCurvetoQuadraticSmoothItem) return y
    if (this is AbstractSVGPathSegList.SVGPathSegCurvetoQuadraticItem) return min(y, y1)
    if (this is AbstractSVGPathSegList.SVGPathSegCurvetoCubicSmoothItem) return min(y, y2)
    if (this is AbstractSVGPathSegList.SVGPathSegCurvetoCubicItem) return min(y, min(y1, y2))
    if (this is SVGPointItem) return y

    return Float.NaN
}

fun SVGItem.translate(aX: Float, aY: Float) {
    if (this is AbstractSVGPathSegList.SVGPathSegMovetoLinetoItem) { x += aX; y += aY }
    if (this is AbstractSVGPathSegList.SVGPathSegLinetoVerticalItem) { x += aX }
    if (this is AbstractSVGPathSegList.SVGPathSegLinetoHorizontalItem) { y += aY }
    if (this is AbstractSVGPathSegList.SVGPathSegCurvetoQuadraticSmoothItem) { x += aX; y += aY }
    if (this is AbstractSVGPathSegList.SVGPathSegCurvetoQuadraticItem) { x += aX; x1 += aX; y += aY; y1 += aY }
    if (this is AbstractSVGPathSegList.SVGPathSegCurvetoCubicSmoothItem) { x += aX; x2 += aX; y += aY; y2 += aY }
    if (this is AbstractSVGPathSegList.SVGPathSegCurvetoCubicItem) { x += aX; x1 += aX; x2 += aX; y += aY; y1 += aY; y2 += aY }
    if (this is SVGPointItem) { x += aX; y += aY }
}