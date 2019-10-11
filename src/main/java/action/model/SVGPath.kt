package action.model

import action.extensions.AbstractElementExt
import action.extensions.forEach
import action.extensions.toAndroidString
import org.apache.batik.anim.dom.SVGOMAnimatedPathData
import org.apache.batik.dom.svg.SVGItem
import org.w3c.dom.svg.SVGPathSegList

class SVGPath {
    private var pathData: SVGOMAnimatedPathData? = null
    val pathSegList: SVGOMAnimatedPathData.BaseSVGPathSegList? = pathData?.pathSegList as? SVGOMAnimatedPathData.BaseSVGPathSegList

    fun parse(pathString: String) {
        val pathData = createPathData(pathString)
        pathData.check()
    }

    fun applyTransformation(transformation: PathTransformation) {
        pathSegList?.forEach { item, i ->
            transformation.apply(item)
        }
    }

    override fun toString(): String {
        return pathSegList.toAndroidString()
    }

    private fun createPathData(pathString: String): SVGOMAnimatedPathData {
        return object : SVGOMAnimatedPathData(AbstractElementExt.createFakeElement(), "namespace", "localname", pathString) {
            override fun getPathSegList(): SVGPathSegList {
                if (this.pathSegs == null) {
                    this.pathSegs = object : BaseSVGPathSegList() {
                        override fun setValueAsString(value: MutableList<Any?>?) {
                            // skip building path string
                            this.valid = true
                        }
                    }
                }

                return this.pathSegs
            }
        }
    }

    interface PathTransformation {
        fun apply(element: SVGItem)
    }
}