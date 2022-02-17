package com.a.hub.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a.hub.R
import com.a.hub.data.model.Issue
import com.a.hub.databinding.ItemIssueBinding
import com.a.hub.helper.gone
import com.a.hub.helper.setTextOrHide
import com.a.hub.ui.adapter.base.BaseAdapter

class IssueAdapter: BaseAdapter<IssueAdapter.IssueViewHolder>() {

    var repoName: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        return IssueViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        val item: Issue = getItem(position) as Issue
        holder.bind(item, repoName)
        if(onItemClick != null){
            holder.itemView.setOnClickListener {
                onItemClick?.invoke(item, it)
            }
            holder.binding.itemImage.setOnClickListener {
                onItemClick?.invoke(item, it)
            }
        }
    }


    class IssueViewHolder(
        val binding: ItemIssueBinding
    ): RecyclerView.ViewHolder(binding.root){

        @SuppressLint("SetTextI18n")
        fun bind(item: Issue, repoName: String){
            binding.itemTitle.text = item.title
            binding.itemName.text = repoName + " #" + item.number
            binding.itemComments.text = item.comments.toString()
            if(item.labels.isEmpty()){
                binding.itemLabels.gone()
            }else {
                binding.itemLabels.setTextOrHide(
                    item.labels.joinToString(prefix = "[", postfix = "]", separator = "] [") {
                        if (it.name.isEmpty()) it.description else it.name
                    }
                ) //TODo list with colors like gh
            }
        }

        companion object {
            fun create(parent: ViewGroup): IssueViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_issue, parent, false)
                return IssueViewHolder(ItemIssueBinding.bind(view))
            }
        }
    }
}