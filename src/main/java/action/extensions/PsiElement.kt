package action.extensions

import com.intellij.psi.PsiElement
import com.intellij.psi.xml.XmlTag

fun PsiElement?.findXmlTag(): XmlTag? {
    if (this == null || !this.isValid) return null

    var current: PsiElement? = this
    while (current != null && current !is XmlTag) {
        current = current.parent
    }

    return current as? XmlTag
}