package action

class Globals {
    companion object {
        val name = "SVGToolbox"
        val groupId = "EditorPopupMenu"

        fun commandName(name: String): String {
            return "${this.name}.${name}"
        }
    }
}