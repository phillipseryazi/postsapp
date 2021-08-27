package com.mudhut.postsapp.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mudhut.postsapp.R
import com.mudhut.postsapp.adapters.CommentsRecyclerAdapter
import com.mudhut.postsapp.db.models.CommentEntity
import com.mudhut.postsapp.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId = arguments?.getInt("userId")
        val postId = arguments?.getInt("postId")

        viewModel.getUser(userId!!)
        viewModel.getPostWithComments(postId!!)

        setObservers()
    }

    private fun setObservers() {
        viewModel.user.observe(viewLifecycleOwner, {
            if (it != null) {
                view?.findViewById<TextView>(R.id.detailsUserTextView)?.text = it.name
            }
        })

        viewModel.postWithComments.observe(viewLifecycleOwner, {
            if (it != null) {
                view?.findViewById<TextView>(R.id.detailsTitleTextView)?.text = it.post.title
                view?.findViewById<TextView>(R.id.detailsBodyTextView)?.text = it.post.body
                initializeRecyclerView(it.comments)
            }
        })
    }

    private fun initializeRecyclerView(comments: List<CommentEntity>) {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.commentsRecyclerView)
        val recyclerViewAdapter = CommentsRecyclerAdapter(comments)
        recyclerView?.adapter = recyclerViewAdapter
        recyclerView?.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
    }


    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = DetailsFragment()
    }
}