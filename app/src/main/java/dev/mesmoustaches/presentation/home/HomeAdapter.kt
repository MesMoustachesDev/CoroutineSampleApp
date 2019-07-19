package dev.mesmoustaches.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.mesmoustaches.R
import dev.mesmoustaches.android.view.GenericViewHolder
import kotlinx.android.synthetic.main.item_employee.view.*

class HomeAdapter : RecyclerView.Adapter<GenericViewHolder>() {

    private var items = listOf<Cell>()

    override fun onBindViewHolder(holder: GenericViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder {
        return EmployeeViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_employee,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    inner class EmployeeViewHolder(itemView: View) : GenericViewHolder(itemView) {

        override fun <T> bind(t: T) {
            val item = t as Cell
            itemView.name.text = item.employeeName
        }
    }

    fun update(clubs: List<Cell>) {
        val diffResult = DiffUtil.calculateDiff(DiffCallback(items, clubs))
        items = clubs
        diffResult.dispatchUpdatesTo(this)
    }

    class DiffCallback(
        private val oldList: List<Cell>,
        private val newList: List<Cell>
    ) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].id == newList[newItemPosition].id

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] == newList[newItemPosition]
    }

    data class Cell(
        val id: String,
        val image: String,
        val employeeName: String
    )
}