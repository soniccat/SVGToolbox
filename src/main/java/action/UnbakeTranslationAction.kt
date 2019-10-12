package action

import action.extensions.currentPsiElement
import action.extensions.findXmlTag
import action.extensions.psiFile
import action.extensions.wrapInGroup
import action.model.*
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
        var pathTag = tag
        val tagPath = pathTag.getAttribute(SVGPathAttribute.PathData.value)?.value

        tagPath?.let { path ->
            try {
                // make sure we have parent path tag
                var parentTag = pathTag.parentTag
                if (parentTag == null || parentTag.name != SVGTag.Group.value) {
                    pathTag = pathTag.wrapInGroup(SVGGroup.DefaultName)!!
                    parentTag = pathTag.parentTag
                }

                // parse to objects
                val svgGroup = SVGGroup()
                svgGroup.load(parentTag!!)

                val svgPath = SVGPath()
                svgPath.parse(path)

                // change and save
                val translation = svgPath.getTranslation()
                svgGroup.translation += translation
                svgGroup.save(parentTag)

                svgPath.applyTranslation(translation.inverted())
                pathTag.setAttribute(SVGPathAttribute.PathData.value, svgPath.toString())

            } catch (e: Exception) {
                // sth went wrong
            }
        }
    }

    private fun findActionXmlTag(event: AnActionEvent): XmlTag? {
        val tag = event.currentPsiElement().findXmlTag()
        return tag?.let {
            if (it.getAttribute(SVGPathAttribute.PathData.value) != null) {
                return tag
            } else {
                return null
            }
        }
    }
}