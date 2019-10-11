package action

import com.intellij.psi.PsiElement
import com.intellij.psi.xml.XmlTag

class XmlTools {
    companion object {
        @JvmStatic
        fun findXmlTag(element: PsiElement?): XmlTag? {
            if (element == null || !element.isValid) return null

            var current: PsiElement? = element
            while (current != null && current !is XmlTag) {
                current = current.parent
            }

            return current as? XmlTag
        }
    }
}