package com.mudhut.postsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mudhut.postsapp.R
import com.mudhut.postsapp.db.models.CommentEntity

class CommentsRecyclerAdapter(
    private val comments: List<CommentEntity>
) : RecyclerView.Adapter<CommentsRecyclerAdapter.CommentsVH>() {
    class CommentsVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val commentTxtView: TextView = itemView.findViewById(R.id.commentTextView)
        val commenterTxtView: TextView = itemView.findViewById(R.id.commenterTextView)

        companion object {
            fun from(parent: ViewGroup): CommentsVH {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(
                    R.layout.comment_item_layout,
                    parent,
                    false
                )
                return CommentsVH(view)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsVH {
        return CommentsVH.from(parent)
    }

    override fun onBindViewHolder(holder: CommentsVH, position: Int) {
        holder.apply {
            commentTxtView.text = comments[position].body
            commenterTxtView.text = comments[position].email
        }
    }

    override fun getItemCount(): Int {
        return comments.size
    }
}
