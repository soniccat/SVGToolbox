package action.extensions

fun Float.toShortString(): String {
    val longValue = this.toLong()
    return if (this == longValue.toFloat()) {
        String.format("%d", longValue)
    } else {
        String.format("%s", this)
    }
}