package xyz.blueowl.ispychallenge.ui.data_browser.adapter_items

import android.view.View
import xyz.blueowl.ispychallenge.R
import xyz.blueowl.ispychallenge.databinding.ItemNearMeChallengeBinding
import xyz.blueowl.ispychallenge.ui.data_browser.shared.AdapterItem
import java.text.DecimalFormat

data class NearMeChallengeItem(
    val challengeId: String,
    val winsCount: Int,
    val avgRating: Double,
    val distance: Double,
    val hint: String,
    val creator: String,
    val onClick: () -> Unit
) : AdapterItem() {

    override val id = challengeId

    override val layoutResource = R.layout.item_near_me_challenge

    override fun bind(view: View) {
        val decimalFormat = DecimalFormat("#.00")

        ItemNearMeChallengeBinding.bind(view).apply {
            this.textWins.text = "$winsCount wins"
            this.textRating.text = "${decimalFormat.format(avgRating)} stars"
            this.textDistance.text = "${decimalFormat.format(distance)}m"
            this.textHint.text = hint
            this.textUser.text = "By: $creator"

            view.setOnClickListener {
                onClick()
            }

            // TODO calculate real device location
            // TODO polish
        }
    }
}
