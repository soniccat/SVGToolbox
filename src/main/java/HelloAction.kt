import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.ui.Messages
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.IElementType
import com.intellij.psi.xml.XmlTag
import com.intellij.psi.xml.XmlToken
import org.apache.batik.anim.dom.AbstractElement
import org.apache.batik.anim.dom.SVGOMAnimatedPathData
import org.apache.batik.dom.svg.AbstractSVGPathSegList
import org.apache.batik.dom.svg.SVGItem
import org.apache.batik.dom.svg.SVGPathSegItem
import org.w3c.dom.Attr
import org.w3c.dom.DOMException
import org.w3c.dom.Node

class HelloAction : AnAction("Hello") {
    override fun update(e: AnActionEvent) {
        super.update(e)

        val psiFile = getPsiFile(e)
        if (psiFile == null) {
            return
        }

        val isAvailable = psiFile.fileType.name == "XML"
        e.presentation.apply {
            isEnabled = isAvailable
            isVisible = isAvailable
        }
    }

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project
        val editor = event.getRequiredData(CommonDataKeys.EDITOR)
        val caretModel = editor.caretModel
        val primaryCaret = caretModel.primaryCaret

        val psiFile = getPsiFile(event)
        if (psiFile == null) return

        val element = psiFile.findElementAt(primaryCaret.offset)
        if (element == null || !element.isValid) return

        var xmlTokenType: IElementType? = null
        if (element is XmlToken) {
            xmlTokenType = element.tokenType
        }

        val tag = findTag(element)
        if (tag == null) return

        if (tag.name == "path") {
            WriteCommandAction.runWriteCommandAction(project, "Myplugin.Textboxes", "EditorPopupMenu", Runnable {
                val path = tag.getAttribute("android:pathData")?.value
                if (path != null) {
                    val pathData = SVGOMAnimatedPathData(object: AbstractElement() {
                        override fun getNodeName(): String {
                            return "nodename"
                        }

                        override fun isReadonly(): Boolean {
                            return true
                        }

                        override fun newNode(): Node {
                            return null!!
                        }

                        override fun setReadonly(p0: Boolean) {
                        }

                        override fun setAttributeNodeNS(newAttr: Attr?): Attr {
                            // do nothing...
                            return null!!
                        }

                        override fun setAttributeNS(namespaceURI: String?, qualifiedName: String?, value: String?) {
                            // do nothing...
                        }

                        override fun setAttribute(name: String?, value: String?) {
                            // do nothing
                        }
                    }, "namespace", "localname", path)

                    pathData.check()
                    val list = pathData.pathSegList as SVGOMAnimatedPathData.BaseSVGPathSegList
                    val item = list.getItem(0)
                    if (item is AbstractSVGPathSegList.SVGPathSegMovetoLinetoItem) {
                        val prevValue = item.valueAsString
                        val prevListString = svgListToString(list)
                        item.x += 10

                        val newValue = item.valueAsString
                        val newListString = svgListToString(list)
                    }
                }

                wrapInGroup(tag)
            }, psiFile)
        }

        val typeName = xmlTokenType?.toString() ?: ""
        Messages.showMessageDialog(project, typeName + "\n" + element.javaClass.toString() + "\n" +element.text, "Greeting", Messages.getInformationIcon())
    }

    private fun wrapInGroup(childToWrap: XmlTag) {
        val parentTag = childToWrap.parentTag
        if (parentTag == null) return

        val createdTag = parentTag.createChildTag("group", "", "", false)
        val newTag = parentTag.addSubTag(createdTag, true)
        newTag.setAttribute("name", "newGroup")
        newTag.addSubTag(childToWrap, true)
        childToWrap.delete()
    }

    private fun findTag(element: PsiElement): XmlTag? {
        var current: PsiElement? = element
        while (current != null && current !is XmlTag) {
            current = current.parent
        }

        return current as? XmlTag
    }

    @Throws(DOMException::class)
    private fun svgListToString(list: SVGOMAnimatedPathData.BaseSVGPathSegList): String {
        var i = 0;
        if (list.numberOfItems > 0) {
            var item = list.getItem(i) as SVGItem
            ++i

            val buf = StringBuffer(list.numberOfItems * 8)
            buf.append(svgItemToString(item))

            while (i < list.numberOfItems) {
                item = list.getItem(i) as SVGItem
                ++i
                buf.append(svgItemToString(item))
            }

            return buf.toString()
        }

        return ""
    }

    private fun svgItemToString(item: SVGItem): String {
        if (item is AbstractSVGPathSegList.SVGPathSegMovetoLinetoItem) {
            return item.pathSegTypeAsLetter + item.x.toString() + ',' + item.y.toString()
        } else if (item is AbstractSVGPathSegList.SVGPathSegCurvetoCubicItem) {
            return item.pathSegTypeAsLetter + item.x1.toString() + ',' + item.y1.toString() + ' ' + item.x2.toString() + ',' + item.y2.toString() + ' ' + item.x.toString() + ',' + item.y.toString()
        } else if (item is SVGPathSegItem) {
            return item.valueAsString.toUpperCase()
        }

        return item.valueAsString
    }

    private fun getPsiFile(e: AnActionEvent) = e.getData(CommonDataKeys.PSI_FILE)
}