package com.android.kit.ui.recyclerview

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * A generic RecyclerView adapter
 *
 * @param <T> Type of the items in the list
 * @param <V> The type of the ViewDataBinding
</V></T> */
abstract class DataBoundRecyclerAdapter<T, V : ViewDataBinding>() :
    RecyclerView.Adapter<DataBoundViewHolder<V>>() {

    open var data: MutableList<T> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun updateItem(position: Int, item: T) {
        if (position > 0 && position < data.size) {
            data[position] = item
            notifyDataSetChanged()
        }
    }

    fun appendData(data: List<T>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun clear() {
        this.data.clear()
        notifyDataSetChanged()
    }

    protected var recyclerView: RecyclerView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder<V> {
        var binding = createBinding(parent)
        if(binding == null){
            binding = createBinding(parent, viewType)
        }
        if(binding == null) throw InstantiationException("Please override createBinding(parent: ViewGroup) OR createBinding(parent: ViewGroup, viewType: Int)")
        return DataBoundViewHolder(binding)
    }

    protected open fun createBinding(parent: ViewGroup): V? {
        return null
    }

    protected open fun createBinding(parent: ViewGroup, viewType: Int): V? {
        return null
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: DataBoundViewHolder<V>, position: Int) {
        bind(holder.binding, data[position], position)
        holder.binding.executePendingBindings()
    }

    protected abstract fun bind(binding: V, item: T, position: Int)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
        super.onAttachedToRecyclerView(recyclerView)
    }

    protected fun smoothScrollToPosition(position: Int, record: Boolean = false) {

//        val p = if (recyclerView is PickerRecyclerView){
//            (recyclerView as PickerRecyclerView).selectedPosition
//        } else{
//            -1
//        }
        recyclerView?.smoothScrollToPosition(position)
//        if (record && p > -1) {
//            ActionManager.record {
//                smoothScrollToPosition(p, record)
//            }
//        }
    }
}