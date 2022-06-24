package xyz.blueowl.ispychallenge.ui.data_browser.adapter_items

import android.view.View
import com.squareup.picasso.Picasso
import xyz.blueowl.ispychallenge.R
import xyz.blueowl.ispychallenge.databinding.ItemUserBinding
import xyz.blueowl.ispychallenge.ui.shared.AdapterItem

data class UserItem(
    val username: String,
    val email: String,
    val imageUrl: String,
    val onClick: ()->Unit
): AdapterItem() {

    override val id: Any = UserItem::javaClass
    override val content: Any? = null
    override val layoutResource: Int = R.layout.item_user

    override fun bind(view: View) {
        ItemUserBinding.bind(view).apply {
            this.textUsername.text = username
            this.textEmail.text = email
            Picasso.get().load(imageUrl).into(this.imageUser)
        }
    }
}
