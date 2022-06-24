package xyz.blueowl.ispychallenge.ui.shared

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * A base recycler view adapter that can be used across the app. This internally uses [ListAdapter] and every recyclerview
 * that uses this adapter need to conform to the model [AdapterItem] and follow the pattern defined in [ItemViewHolder]
 */
class UniversalListAdapter(private val listener: ((AdapterItem) -> Unit)? = null) : ListAdapter<AdapterItem, ItemViewHolder>(ItemDiffCallback) {

    override fun getItemViewType(position: Int): Int {
        val adapterItem: AdapterItem = getItem(position)
        return adapterItem.layoutResource
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(viewType, parent, false)
        return ItemViewHolder(view, listener)
    }

    override fun onViewRecycled(holder: ItemViewHolder) {
        super.onViewRecycled(holder)
        holder.unbind()
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    // This is to ensure that diff Util is called whenever a new list is submitted to the adapter
    override fun submitList(list: List<AdapterItem>?) {
        super.submitList(list?.toList())
    }
}

/**
 * Unique ViewHolder that's being used to draw the views in the recycler view
 * @param view - View that's been drawn on the list
 * @param clickCallback - The callback that's been sent to the parent with [AdapterItem] so all further actions can be done
 */
class ItemViewHolder(
    private val view: View,
    private val clickCallback: ((AdapterItem) -> Unit)?,
) : RecyclerView.ViewHolder(view) {

    var adapterItem: AdapterItem? = null
        private set

    init {
        view.setOnClickListener {
            adapterItem?.let { clickCallback?.invoke(it) }
        }
    }

    fun bind(adapterItem: AdapterItem) {
        this.adapterItem = adapterItem
        adapterItem.bind(view)
    }

    fun unbind() {
        adapterItem?.unbind()
        adapterItem = null
    }
}

/**
 * The interface that all elements on the list must implement.
 * If the children of this interface confirms to data class then no need to override equals function if not the equals provides a way for the
 * DiffUtil class to calculate whether the items drawn are similar to previous ones, thus avoiding redrawing the whole list
 */
abstract class AdapterItem {
    abstract val id: Any
    abstract val content: Any?

    @get:LayoutRes
    abstract val layoutResource: Int

    abstract fun bind(view: View)
    abstract override fun equals(other: Any?): Boolean
    abstract override fun hashCode(): Int
    open fun unbind() {
        // override if required
    }
}

object ItemDiffCallback : DiffUtil.ItemCallback<AdapterItem>() {
    override fun areItemsTheSame(oldItem: AdapterItem, newItem: AdapterItem) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: AdapterItem, newItem: AdapterItem) = oldItem == newItem
}