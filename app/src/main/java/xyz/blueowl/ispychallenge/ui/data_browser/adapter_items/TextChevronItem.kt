package xyz.blueowl.ispychallenge.ui.data_browser.adapter_items

import android.view.View
import xyz.blueowl.ispychallenge.R
import xyz.blueowl.ispychallenge.databinding.ItemTextChevronBinding
import xyz.blueowl.ispychallenge.ui.data_browser.shared.AdapterItem

data class TextChevronItem(
    private val header: String,
    private val onClick: () -> Unit
) : AdapterItem() {

    override val id: Any = header
    override val layoutResource: Int = R.layout.item_text_chevron

    override fun bind(view: View) {
        ItemTextChevronBinding.bind(view).apply {
            this.textHeader.text = header
            view.setOnClickListener { onClick() }
        }
    }
}