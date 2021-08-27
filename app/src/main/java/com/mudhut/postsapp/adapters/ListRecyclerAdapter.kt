package com.mudhut.postsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mudhut.postsapp.R
import com.mudhut.postsapp.db.models.PostEntity

class ListRecyclerAdapter(
    private val posts: List<PostEntity>,
    private val listener: ListRecyclerListener
) : RecyclerView.Adapter<ListRecyclerAdapter.ListRecyclerVH>() {

    class ListRecyclerVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.postTitleTxt)
        val bodyTextView: TextView = itemView.findViewById(R.id.postBodyTxt)

        companion object {
            fun from(parent: ViewGroup): ListRecyclerVH {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(
                    R.layout.post_item_layout,
                    parent,
                    false
                )
                return ListRecyclerVH(view)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListRecyclerVH {
        return ListRecyclerVH.from(parent)
    }

    override fun onBindViewHolder(holder: ListRecyclerVH, position: Int) {
        holder.apply {
            itemView.setOnClickListener {
                listener.onListClick(posts[position].id, posts[position].userId)
            }
            titleTextView.text = posts[position].title
            bodyTextView.text = posts[position].body
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }
}

interface ListRecyclerListener {
    fun onListClick(postId: Int, userId: Int)
}

