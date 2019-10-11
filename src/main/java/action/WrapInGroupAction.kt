package action

import action.extensions.currentPsiElement
import action.extensions.findXmlTag
import action.extensions.psiFile
import action.extensions.wrapInGroup
import action.model.SVGGroup
import action.model.SVGTag
import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.psi.xml.XmlTag

class WrapInGroupAction : AnAction(Name) {

    companion object {
        val Name = Globals.commandName("WrapInGroup")
    }

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
            WriteCommandAction.runWriteCommandAction(e.project, Name, Globals.groupId, Runnable {
                t.wrapInGroup(SVGGroup.DefaultName)
            }, e.psiFile())
        }
    }

    private fun findActionXmlTag(event: AnActionEvent): XmlTag? {
        val tag = event.currentPsiElement().findXmlTag()
        return tag?.let {
            if (tag.name == SVGTag.Path.value || tag.name == SVGTag.Group.value) {
                return tag
            } else {
                return null
            }
        }
    }
}