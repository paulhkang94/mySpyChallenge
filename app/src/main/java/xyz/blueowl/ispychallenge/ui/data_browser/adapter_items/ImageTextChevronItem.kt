package xyz.blueowl.ispychallenge.ui.data_browser.adapter_items

import android.view.View
import com.squareup.picasso.Picasso
import xyz.blueowl.ispychallenge.R
import xyz.blueowl.ispychallenge.databinding.ItemImageTextChevronBinding
import xyz.blueowl.ispychallenge.ui.data_browser.shared.AdapterItem

data class ImageTextChevronItem(
    val header: String,
    val label: String,
    val imageUrl: String,
    val onClick: () -> Unit
) : AdapterItem() {

    override val id: Any = ImageTextChevronItem::javaClass
    override val layoutResource: Int = R.layout.item_image_text_chevron

    override fun bind(view: View) {
        ItemImageTextChevronBinding.bind(view).apply {
            this.textHeader.text = header
            this.textLabel.text = label
            Picasso.get()
                .load(imageUrl)
                .resize(160, 160)
                .centerInside()
                .into(this.image)

            view.setOnClickListener {
                onClick()
            }
        }
    }
}
