package action

enum class SvgTag(val value: String) {
    Group("group"),
    Path("path")
}

enum class SvgTagAttribute(val value: String) {
    Name("android:name"),
    PathData("android:pathData")
}
