package action.model

enum class SVGTag(val value: String) {
    Group("group"),
    Path("path")
}

enum class SVGTagAttribute(val value: String) {
    Name("android:name"),
    PathData("android:pathData"),
    TranslateX("android:translateX"),
    TranslateY("android:translateY")
}
