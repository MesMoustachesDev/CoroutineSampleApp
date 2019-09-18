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
import dev.mesmoustaches.domain.model.FilterType
import kotlinx.android.synthetic.main.item_filter_checkbox.view.*
import kotlinx.android.synthetic.main.item_filter_group.view.*
import kotlinx.android.synthetic.main.item_filter_group.view.name

class FilterGroupAdapter(val onFilterChanged: (List<Cell>) -> Unit) : RecyclerView.Adapter<GenericViewHolder>() {

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
        return when (viewType) {
            R.layout.item_filter_group -> FilterViewHolder(view)
            else -> CheckBoxFilterViewHolder(view)
        }
    }

    override fun getItemCount(): Int = items.size

    inner class FilterViewHolder(itemView: View) : GenericViewHolder(itemView) {
        private val adapter: FilterAdapter by lazy {
            FilterAdapter{
                onFilterChanged(items)
            }
        }

        init {
            itemView.filterRecyclerView.adapter = adapter
        }

        override fun <T> bind(t: T) {
            val item = t as Cell
            itemView.name.setText(item.name)

            item.filters?.let {
                adapter.update(it)
            }
        }
    }

    inner class CheckBoxFilterViewHolder(itemView: View) : GenericViewHolder(itemView) {
        override fun <T> bind(t: T) {
            val item = t as Cell
            itemView.name.setText(item.name)
            itemView.radioGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.choice1 -> {
                        t.filters?.get(0)?.selected = true
                        t.filters?.get(1)?.selected = false
                    }
                    R.id.choice2 -> {
                        t.filters?.get(0)?.selected = false
                        t.filters?.get(1)?.selected = true
                    }
                }
                onFilterChanged(items)
            }
        }
    }

    override fun getItemViewType(position: Int): Int = when (items[position].filters?.get(0)?.type) {
        is FilterType.CheckBoxFilter -> R.layout.item_filter_checkbox
        else -> R.layout.item_filter_group
    }

    fun update(events: List<Cell>) {
//        val diffResult = DiffUtil.calculateDiff(DiffCallback(items, events))
        items = events
        notifyDataSetChanged()
        //diffResult.dispatchUpdatesTo(this)
    }

    class DiffCallback(
        private val oldList: List<Cell>,
        private val newList: List<Cell>
    ) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val old = oldList[oldItemPosition]
            val new = newList[newItemPosition]
            return (old.id == new.id)
        }

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val old = oldList[oldItemPosition]
            val new = newList[newItemPosition]

            var res = true

            if (old.filters?.size != new.filters?.size) {
                return false
            } else {
                old.filters?.forEachIndexed { index, filter ->
                    res = res && (new.filters?.get(index)?.selected == filter.selected)
                }
            }
            return res
        }
    }

    data class Cell(
        val id: String,
        val name: Int,
        val filters: List<Filter>?
    )
}

fun FilterCategoryDomain.toCell() = FilterGroupAdapter.Cell(
    id = id,
    name = nameToDisplay,
    filters = filters
)
