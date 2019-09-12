package dev.mesmoustaches.presentation.filter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.mesmoustaches.R
import dev.mesmoustaches.android.view.GenericViewHolder
import dev.mesmoustaches.domain.model.Filter
import kotlinx.android.synthetic.main.item_filter_list.view.*

class FilterAdapter(val onFilterChanged: () -> Unit) : RecyclerView.Adapter<GenericViewHolder>() {

    private var items = listOf<Filter>()
    private var lastItemChecked: Filter? = null
    private var lastViewChecked: CheckBox? = null

    override fun onBindViewHolder(holder: GenericViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_filter_list,
            parent,
            false
        )
        return FilterListViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    inner class FilterListViewHolder(itemView: View) : GenericViewHolder(itemView) {
        override fun <T> bind(t: T) {
            val item = t as Filter
            itemView.checkbox.text = item.name
            itemView.checkbox.isChecked = item.selected
            if (item.selected) {
                lastItemChecked = item
                lastViewChecked = itemView.checkbox
            }
            itemView.checkbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    lastItemChecked?.selected = false
                    lastViewChecked?.isChecked = false
                }
                item.selected = isChecked
                onFilterChanged()
            }
        }
    }

    fun update(events: List<Filter>) {
//        val diffResult = DiffUtil.calculateDiff(DiffCallback(items, events))
        items = events
//        diffResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }

    class DiffCallback(
        private val oldList: List<Filter>,
        private val newList: List<Filter>
    ) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val old = oldList[oldItemPosition]
            val new = newList[newItemPosition]
            return (old.name == new.name)
        }

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val old = oldList[oldItemPosition]
            val new = newList[newItemPosition]
            return (old.selected == new.selected)
        }
    }
}
