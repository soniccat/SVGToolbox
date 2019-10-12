package action.extensions

import action.model.SVGTag
import action.model.SVGGroupAttribute
import com.intellij.psi.xml.XmlTag

fun XmlTag.wrapInGroup(name: String): XmlTag? {
    val parentTag = this.parentTag
    if (parentTag == null) return null

    val createdTag = parentTag.createChildTag(SVGTag.Group.value, "", "", false)
    val newTag = parentTag.addAfter(createdTag, this) as XmlTag
    newTag.setAttribute(SVGGroupAttribute.Name.value, name)

    val result = newTag.add(this) as XmlTag
    this.delete()

    return result
}

fun XmlTag.getFloatAttributeValue(name: String): Float {
    return getAttributeValue(name)?.let {
        return it.toFloat()
    } ?: 0.0f
}

fun XmlTag.setFloatAttributeValue(name: String, value: Float) {
    setAttribute(name, value.toShortString())
}