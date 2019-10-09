package action

import com.intellij.psi.xml.XmlTag

fun XmlTag.wrapInGroup(name: String): XmlTag? {
    val parentTag = this.parentTag
    if (parentTag == null) return null

    val createdTag = parentTag.createChildTag(SvgTag.Group.value, "", "", false)
    val newTag = parentTag.addAfter(createdTag, this) as XmlTag
    newTag.setAttribute(SvgTagAttribute.Name.value, name)

    val result = newTag.add(this) as XmlTag
    this.delete()

    return result
}