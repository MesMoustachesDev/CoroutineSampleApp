package dev.mesmoustaches.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.setTextViewHTML
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.mesmoustaches.R
import dev.mesmoustaches.android.view.GenericViewHolder
import dev.mesmoustaches.domain.model.EventDomain
import kotlinx.android.synthetic.main.item_event.view.*

class HomeAdapter(val needMore: (Int) -> Unit) : RecyclerView.Adapter<GenericViewHolder>() {

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
            R.layout.item_event -> EventViewHolder(view)
            else -> NeedMoreViewHolder(view, needMore)
        }
    }

    override fun getItemCount(): Int = items.size

    inner class EventViewHolder(itemView: View) : GenericViewHolder(itemView) {
        override fun <T> bind(t: T) {
            val item = t as Cell.DataCell
            itemView.name.text = item.title
            itemView.date.setTextViewHTML(item.date)
            Glide.with(itemView.image)
                .load(item.image)
                .placeholder(R.drawable.logo)
                .into(itemView.image)
        }
    }

    inner class NeedMoreViewHolder(itemView: View, private val needMore: (Int) -> Unit) : GenericViewHolder(itemView) {
        override fun <T> bind(t: T) {
            needMore.invoke(itemCount)
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
            return (old is Cell.DataCell && new is Cell.DataCell && old.id == new.id)
        }

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val old = oldList[oldItemPosition]
            val new = newList[newItemPosition]
            return (old is Cell.DataCell && new is Cell.DataCell && old.title == new.title)
        }
    }

    override fun getItemViewType(position: Int): Int = when (items[position]) {
        is Cell.DataCell -> R.layout.item_event
        is Cell.NeedMore -> R.layout.item_need_more
    }

    sealed class Cell(val id: String) {
        class DataCell(
            id: String,
            val title: String,
            val date: String,
            val image: String?
        ) : Cell(id)

        object NeedMore : Cell("-1")
    }
}

fun EventDomain.toCell() = HomeAdapter.Cell.DataCell(
    id = id,
    title = title,
    date = dateText,
    image = image)
