package action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.psi.xml.XmlTag
import org.apache.batik.svggen.SVGAttribute

class UnbakeTransformationAction: AnAction("SVGUnbakeTransformation")  {

    override fun update(e: AnActionEvent) {
        super.update(e)

        val tag = findActionXmlTag(e)
        e.presentation.apply {
            isVisible = true
            isEnabled = tag != null
        }
    }

    override fun actionPerformed(e: AnActionEvent) {
        findActionXmlTag(e)?.let { t ->
            WriteCommandAction.runWriteCommandAction(e.project, "SVGToolbox.WrapInGroup", "EditorPopupMenu", Runnable {
                unbakeTransformation(t)
            }, e.psiFile())
        }
    }

    fun unbakeTransformation(tag: XmlTag) {
        val tagPath = tag.getAttribute(SvgTagAttribute.PathData.value)?.value
        val svgPath = SVGPath()

        tagPath?.let { path ->
            try {
                svgPath.parse(path)
                // todo...

            } catch (e: Exception) {
                // sth went wrong
            }
        }
    }

    private fun findActionXmlTag(event: AnActionEvent): XmlTag? {
        val tag = XmlTools.findXmlTag(event.currentPsiElement())
        return tag?.let {
            if (it.getAttribute(SvgTagAttribute.PathData.value) != null) {
                return tag
            } else {
                return null
            }
        }
    }
}