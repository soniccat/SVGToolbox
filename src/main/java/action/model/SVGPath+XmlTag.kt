package action.model

import com.intellij.psi.xml.XmlTag

fun SVGPath.load(tag: XmlTag) {
    val path = tag.getAttribute(SVGPathAttribute.PathData.value)?.value
    if (path != null) {
        parse(path)
    }
}

fun SVGPath.save(tag: XmlTag) {
    tag.setAttribute(SVGPathAttribute.PathData.value, toString())
}