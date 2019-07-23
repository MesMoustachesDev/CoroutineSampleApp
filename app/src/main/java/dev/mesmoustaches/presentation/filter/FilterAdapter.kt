package dev.mesmoustaches.presentation.filter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.mesmoustaches.R
import dev.mesmoustaches.android.view.GenericViewHolder
import dev.mesmoustaches.domain.model.Filter
import dev.mesmoustaches.domain.model.FilterType
import kotlinx.android.synthetic.main.item_filter_checkbox.view.*
import kotlinx.android.synthetic.main.item_filter_list.view.*

class FilterAdapter : RecyclerView.Adapter<GenericViewHolder>() {

    private var items = listOf<Cell>()

    override fun onBindViewHolder(holder: GenericViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            viewType,
            parent,
            false
        )
        return when (viewType){
            R.layout.item_filter_list -> FilterListViewHolder(view)
            else -> FilterRadioViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int = when (items[position].type) {
        FilterType.CheckBoxFilter -> R.layout.item_filter_checkbox
        FilterType.ListFilter -> R.layout.item_filter_list
    }

    override fun getItemCount(): Int = items.size

    inner class FilterListViewHolder(itemView: View) : GenericViewHolder(itemView) {
        override fun <T> bind(t: T) {
            val item = t as Cell
            itemView.checkbox.text = item.name
        }
    }

    inner class FilterRadioViewHolder(itemView: View) : GenericViewHolder(itemView) {
        override fun <T> bind(t: T) {
            val item = t as Cell
            itemView.radio.text = item.name
        }
    }

    fun update(events: List<Cell>) {
        val diffResult = DiffUtil.calculateDiff(DiffCallback(items, events))
        items = events
        diffResult.dispatchUpdatesTo(this)
    }

    class DiffCallback(
        private val oldList: List<Cell>,
        private val newList: List<Cell>
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
            return (old.name == new.name)
        }
    }

    data class Cell(
        val name: String,
        val path: String,
        val type: FilterType
    )
}

fun Filter.toCell() = FilterAdapter.Cell(
    name = name,
    path = path,
    type = type
)
