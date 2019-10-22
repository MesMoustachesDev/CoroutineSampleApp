package dev.mesmoustaches.presentation.filter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.mesmoustaches.R
import dev.mesmoustaches.android.view.GenericViewHolder
import dev.mesmoustaches.domain.model.FilterType
import kotlinx.android.synthetic.main.item_filter_list.view.*

class FilterAdapter(val onFilterChanged: () -> Unit) : RecyclerView.Adapter<GenericViewHolder>() {

    private var items = listOf<FilterCell>()
    private var lastItemChecked: FilterCell? = null

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
            val item = t as FilterCell
            itemView.checkbox.text = item.name
            itemView.checkbox.setOnCheckedChangeListener(null)
            itemView.checkbox.isChecked = item.selected

            itemView.checkbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    lastItemChecked?.selected = false
                    notifyItemChanged(items.indexOf(lastItemChecked))
                }
                item.selected = isChecked
                lastItemChecked = item
                notifyItemChanged(items.indexOf(item))
                onFilterChanged()
            }
        }
    }

    fun update(events: List<FilterCell>) {
        val selectedItem = events.firstOrNull { it.selected }
        lastItemChecked = selectedItem
        items = events
        notifyDataSetChanged()
    }

    data class FilterCell(
        val name: String,
        val path: String,
        var selected: Boolean,
        val type: FilterType
    )
}
