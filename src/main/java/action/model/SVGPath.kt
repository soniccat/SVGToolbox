package action.model

import action.extensions.AbstractElementExt
import action.extensions.forEach
import action.extensions.toAndroidString
import org.apache.batik.anim.dom.SVGOMAnimatedPathData
import org.apache.batik.dom.svg.SVGItem
import org.apache.batik.dom.svg.SVGPathSegItem
import org.w3c.dom.svg.SVGPathSeg
import org.w3c.dom.svg.SVGPathSegList

class SVGPath {
    private var pathData: SVGOMAnimatedPathData? = null
    val svgItems: SVGOMAnimatedPathData.BaseSVGPathSegList?
        get () = pathData?.pathSegList as? SVGOMAnimatedPathData.BaseSVGPathSegList

    fun parse(pathString: String) {
        pathData = createPathData(pathString)
        pathData?.check()
    }

    fun applyTransformation(transformation: PathTransformation) {
        svgItems?.forEach { item, _ ->
            val isClosePath = item.pathSegType == SVGPathSeg.PATHSEG_CLOSEPATH
            if (!isClosePath) {
                transformation.apply(item)
            }
        }
    }

    override fun toString(): String {
        return svgItems.toAndroidString()
    }

    private fun createPathData(pathString: String): SVGOMAnimatedPathData {
        return object : SVGOMAnimatedPathData(AbstractElementExt.createFakeElement(), "namespace", "localname", pathString) {
            override fun check() {
                initPathSegsIfNeeded()
                super.check()
            }

            override fun getPathSegList(): SVGPathSegList {
                initPathSegsIfNeeded()
                return this.pathSegs
            }

            private fun initPathSegsIfNeeded() {
                if (this.pathSegs == null) {
                    this.pathSegs = object : BaseSVGPathSegList() {
                        override fun setValueAsString(value: MutableList<Any?>?) {
                            // skip building path string
                            this.valid = true
                        }
                    }
                }
            }
        }
    }

    interface PathTransformation {
        fun apply(item: SVGItem)
    }
}