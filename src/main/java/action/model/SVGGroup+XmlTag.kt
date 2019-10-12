package action.model

import action.extensions.*
import com.intellij.psi.xml.XmlTag

fun SVGGroup.load(tag: XmlTag) {
    name = tag.getAttributeValue(SVGGroupAttribute.Name.value) ?: SVGGroup.DefaultName

    val translationX = tag.getFloatAttributeValue(SVGGroupAttribute.TranslateX.value)
    val translationY = tag.getFloatAttributeValue(SVGGroupAttribute.TranslateY.value)
    translation = SVGGroup.Translation(translationX, translationY)
}

fun SVGGroup.save(tag: XmlTag) {
    tag.setAttribute(SVGGroupAttribute.Name.value, tag.name)

    if (translation.x != 0.0f) {
        tag.setFloatAttributeValue(SVGGroupAttribute.TranslateX.value, translation.x)
    }

    if (translation.y != 0.0f) {
        tag.setFloatAttributeValue(SVGGroupAttribute.TranslateY.value, translation.y)
    }
}