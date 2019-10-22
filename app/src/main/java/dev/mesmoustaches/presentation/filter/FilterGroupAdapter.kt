package dev.mesmoustaches.presentation.filter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.mesmoustaches.R
import dev.mesmoustaches.android.view.GenericViewHolder
import dev.mesmoustaches.domain.model.FilterCategoryDomain
import kotlinx.android.synthetic.main.item_filter_group.view.*
import kotlinx.android.synthetic.main.item_filter_group.view.name

class FilterGroupAdapter(val onFilterChanged: (List<Cell>) -> Unit) :
    RecyclerView.Adapter<GenericViewHolder>() {

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
            else -> FilterViewHolder(view)
        }
    }

    override fun getItemCount(): Int = items.size

    inner class FilterViewHolder(itemView: View) : GenericViewHolder(itemView) {
        private val adapter: FilterAdapter by lazy {
            FilterAdapter {
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

    override fun getItemViewType(position: Int): Int = R.layout.item_filter_group

    fun update(events: List<Cell>) {
        items = events
        notifyDataSetChanged()
    }

    data class Cell(
        val id: String,
        val name: Int,
        val filters: List<FilterAdapter.FilterCell>?
    )
}

fun FilterCategoryDomain.toCell(nameToDisplay: Int, filters: List<FilterAdapter.FilterCell>?) =
    FilterGroupAdapter.Cell(
        id = id,
        name = nameToDisplay,
        filters = filters
    )
