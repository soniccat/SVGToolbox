package action

import com.intellij.openapi.actionSystem.ActionGroup
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class ToolboxGroup: ActionGroup("SVGGroup", false) {
    init {
    }

    override fun update(e: AnActionEvent) {
        super.update(e)
        isPopup = true
        e.presentation.isVisible = true
        e.presentation.isEnabled = true
    }

    override fun getChildren(e: AnActionEvent?): Array<AnAction> {
        val actions = ArrayList<AnAction>()
        actions.add(WrapInGroupAction())

        return actions.toTypedArray()
    }
}