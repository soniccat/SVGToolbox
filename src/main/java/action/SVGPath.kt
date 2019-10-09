package action

import org.apache.batik.anim.dom.AbstractElement
import org.apache.batik.anim.dom.SVGOMAnimatedPathData
import org.apache.batik.dom.svg.AbstractSVGPathSegList
import org.apache.batik.dom.svg.SVGItem
import org.apache.batik.dom.svg.SVGPathSegItem
import org.w3c.dom.Attr
import org.w3c.dom.Node

class SVGPath {
    private var pathData: SVGOMAnimatedPathData? = null
    val pathSegList: SVGOMAnimatedPathData.BaseSVGPathSegList? = pathData?.pathSegList as? SVGOMAnimatedPathData.BaseSVGPathSegList

    fun parse(pathString: String) {
        val pathData = SVGOMAnimatedPathData(createFakeElement(), "namespace", "localname", pathString)
        pathData.check()
    }

    fun applyTransformation(transformation: PathTransformation) {
        pathSegList?.let {
            forEachSvgItem(it) { item, i ->
                transformation.apply(item)
            }
        }
    }

    override fun toString(): String {
        return pathSegList?.let {
            svgListToString(it)
        } ?: ""
    }

    private fun svgListToString(list: SVGOMAnimatedPathData.BaseSVGPathSegList): String {
        val buf = StringBuffer(list.numberOfItems * 8)
        forEachSvgItem(list) { item, i ->
            buf.append(svgItemToString(item))
        }

        return ""
    }

    private fun forEachSvgItem(list: SVGOMAnimatedPathData.BaseSVGPathSegList, block: (item: SVGItem, i: Int) -> Unit) {
        val itemCount = list.numberOfItems
        var i = 0
        while (i < itemCount) {
            val item = list.getItem(i) as SVGItem
            block(item, i)
            ++i;
        }
    }

    private fun svgItemToString(item: SVGItem): String {
        if (item is AbstractSVGPathSegList.SVGPathSegMovetoLinetoItem) {
            return item.pathSegTypeAsLetter + item.x.toString() + ',' + item.y.toString()
        } else if (item is AbstractSVGPathSegList.SVGPathSegCurvetoCubicItem) {
            return item.pathSegTypeAsLetter + item.x1.toString() + ',' + item.y1.toString() + ' ' + item.x2.toString() + ',' + item.y2.toString() + ' ' + item.x.toString() + ',' + item.y.toString()
        } else if (item is SVGPathSegItem) {
            return item.valueAsString.toUpperCase()
        }

        return item.valueAsString
    }

    private fun createFakeElement(): AbstractElement {
        return object : AbstractElement() {
            override fun getNodeName(): String {
                return "nodename"
            }

            override fun isReadonly(): Boolean {
                return true
            }

            override fun newNode(): Node {
                return null!!
            }

            override fun setReadonly(p0: Boolean) {
            }

            override fun setAttributeNodeNS(newAttr: Attr?): Attr {
                // do nothing...
                return null!!
            }

            override fun setAttributeNS(namespaceURI: String?, qualifiedName: String?, value: String?) {
                // do nothing...
            }

            override fun setAttribute(name: String?, value: String?) {
                // do nothing
            }
        }
    }

    interface PathTransformation {
        fun apply(element: SVGItem)
    }
}