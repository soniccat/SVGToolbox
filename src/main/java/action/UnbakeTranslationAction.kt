package action

import action.extensions.currentPsiElement
import action.extensions.findXmlTag
import action.extensions.psiFile
import action.model.SVGPath
import action.model.SVGTagAttribute
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.psi.xml.XmlTag

class UnbakeTranslationAction: AnAction(Globals.commandName(Name))  {

    companion object {
        val Name = "SVGUnbakeTransformation"
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
            WriteCommandAction.runWriteCommandAction(e.project, Globals.commandName(Name), Globals.groupId, Runnable {
                unbakeTransformation(t)
            }, e.psiFile())
        }
    }

    fun unbakeTransformation(tag: XmlTag) {
        val tagPath = tag.getAttribute(SVGTagAttribute.PathData.value)?.value
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
        val tag = event.currentPsiElement().findXmlTag()
        return tag?.let {
            if (it.getAttribute(SVGTagAttribute.PathData.value) != null) {
                return tag
            } else {
                return null
            }
        }
    }
}