package action.extensions

import org.apache.batik.anim.dom.AbstractElement
import org.w3c.dom.Attr
import org.w3c.dom.Node

class AbstractElementExt {
    companion object {
        fun createFakeElement(): AbstractElement {
            return object : AbstractElement() {
                override fun getNodeName(): String {
                    return ""
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
            }
        }
    }
}