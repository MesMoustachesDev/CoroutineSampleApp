package dev.mesmoustaches.presentation.filter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.mesmoustaches.R
import dev.mesmoustaches.android.view.GenericViewHolder
import dev.mesmoustaches.domain.model.Filter
import dev.mesmoustaches.domain.model.FilterCategoryDomain
import kotlinx.android.synthetic.main.item_filter_group.view.*

class FilterGroupAdapter : RecyclerView.Adapter<GenericViewHolder>() {

    private var items = listOf<Cell>()

    override fun onBindViewHolder(holder: GenericViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_filter_group,
            parent,
            false
        )
        return FilterViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    inner class FilterViewHolder(itemView: View) : GenericViewHolder(itemView) {
        private val adapter: FilterAdapter by lazy {
            FilterAdapter()
        }

        init {
            itemView.filterRecyclerView.adapter = adapter
        }

        override fun <T> bind(t: T) {
            val item = t as Cell
            itemView.name.setText(item.name)

            item.filters?.map { it.toCell() }?.let {
                adapter.update(it)
            }
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
        val name: Int,
        val filters: List<Filter>?
    )
}

fun FilterCategoryDomain.toCell() = FilterGroupAdapter.Cell(
    name = nameToDisplay,
    filters = filters
)
