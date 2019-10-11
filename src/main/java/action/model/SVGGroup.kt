package action.model

import action.extensions.SVGPointItemExt

class SVGGroup {
    companion object {
        val DefaultName = "NewGroup"
    }

    var name = DefaultName
    var translation = SVGPointItemExt.zero()
}