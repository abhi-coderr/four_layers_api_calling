package com.example.newdiffutils.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newdiffutils.databinding.RawItemLayoutBinding
import com.example.newdiffutils.network.models.Todo

class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val rawItemLayoutBinding: RawItemLayoutBinding) :
        RecyclerView.ViewHolder(rawItemLayoutBinding.root) {

            fun bind(todo: Todo){
                rawItemLayoutBinding.apply {
                    rawItemTextFromApiTv.text = todo.title
                    rawItemCheckBox.isChecked = todo.completed
                }
            }

    }


    private val diffCallback = object : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {

            return oldItem.id == newItem.id

        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var todos: List<Todo>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun getItemCount() = todos.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(RawItemLayoutBinding
            .inflate(LayoutInflater.from(parent.context),
                parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(todos[position])
    }


}