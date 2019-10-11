package action.extensions

import action.model.SVGTag
import action.model.SVGTagAttribute
import com.intellij.psi.xml.XmlTag

fun XmlTag.wrapInGroup(name: String): XmlTag? {
    val parentTag = this.parentTag
    if (parentTag == null) return null

    val createdTag = parentTag.createChildTag(SVGTag.Group.value, "", "", false)
    val newTag = parentTag.addAfter(createdTag, this) as XmlTag
    newTag.setAttribute(SVGTagAttribute.Name.value, name)

    val result = newTag.add(this) as XmlTag
    this.delete()

    return result
}