package action

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.psi.PsiElement

fun AnActionEvent.psiFile() = this.getData(CommonDataKeys.PSI_FILE)

fun AnActionEvent.editor() = this.getData(CommonDataKeys.EDITOR)

fun AnActionEvent.currentPsiElement(): PsiElement? {
    val primaryCaret = this.editor()?.caretModel?.primaryCaret
    val psiFile = this.psiFile()
    if (psiFile == null || primaryCaret == null) return null

    return psiFile.findElementAt(primaryCaret.offset)
}