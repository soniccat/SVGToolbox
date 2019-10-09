package action

import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.psi.xml.XmlTag

class WrapInGroupAction : AnAction("SVGWrapInGroup") {

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
                t.wrapInGroup("newGroup")
            }, e.psiFile())
        }
    }

    private fun findActionXmlTag(event: AnActionEvent): XmlTag? {
        val tag = XmlTools.findXmlTag(event.currentPsiElement())
        return tag?.let {
            if (tag.name == SvgTag.Path.value || tag.name == SvgTag.Group.value) {
                return tag
            } else {
                return null
            }
        }
    }
}