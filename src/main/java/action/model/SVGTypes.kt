package action.model

enum class SVGTag(val value: String) {
    Group("group"),
    Path("path")
}

enum class SVGPathAttribute(val value: String) {
    PathData("android:pathData"),
}

enum class SVGGroupAttribute(val value: String) {
    Name("android:name"),
    TranslateX("android:translateX"),
    TranslateY("android:translateY")
}
