package xyz.blueowl.ispychallenge.ui.data_browser.adapter_items

import android.view.View
import xyz.blueowl.ispychallenge.R
import xyz.blueowl.ispychallenge.databinding.ItemHeaderLabelBinding
import xyz.blueowl.ispychallenge.ui.data_browser.shared.AdapterItem

data class HeaderLabelListItem(
    val header: String,
    val label: String
): AdapterItem() {
    override val id = header.hashCode() + label.hashCode()
    override val layoutResource = R.layout.item_header_label

    override fun bind(view: View) {
        ItemHeaderLabelBinding.bind(view).apply {
            this.textHeader.text = header
            this.textBody.text = label
        }
    }
}
