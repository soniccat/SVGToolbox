package action

import com.intellij.openapi.actionSystem.ActionGroup
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class ToolboxGroup: ActionGroup("SVGGroup", false) {
    override fun update(e: AnActionEvent) {
        super.update(e)

        val isAvailable = e.psiFile()?.fileType?.name == "XML"
        e.presentation.apply {
            isPopup = true
            isEnabled = isAvailable
            isVisible = isAvailable
        }
    }

    override fun getChildren(e: AnActionEvent?): Array<AnAction> {
        val actions = ArrayList<AnAction>()
        actions.add(UnbakeTransformationAction())
        actions.add(WrapInGroupAction())

        return actions.toTypedArray()
    }
}