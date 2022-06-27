package xyz.blueowl.ispychallenge.ui.data_browser.adapter_items

import xyz.blueowl.ispychallenge.R
import xyz.blueowl.ispychallenge.ui.data_browser.shared.HeaderData

class AttributesHeaderItem: HeaderData {
    override val headerResId: Int = R.string.header_attributes
}

class RelationshipHeaderItem: HeaderData {
    override val headerResId: Int = R.string.header_relationships
}
