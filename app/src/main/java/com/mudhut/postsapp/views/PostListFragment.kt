package com.mudhut.postsapp.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mudhut.postsapp.R
import com.mudhut.postsapp.adapters.ListRecyclerAdapter
import com.mudhut.postsapp.adapters.ListRecyclerListener
import com.mudhut.postsapp.db.models.PostEntity
import com.mudhut.postsapp.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostListFragment : Fragment(), ListRecyclerListener {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
    }

    override fun onResume() {
        super.onResume()
        hideBanner()
        showProgressBar()
        viewModel.getPosts()
    }

    private fun setObservers() {
        viewModel.postList.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                hideProgressBar()
                initializeRecyclerView(it)
            } else {
                hideProgressBar()
                showBanner()
            }
        })
    }

    private fun initializeRecyclerView(posts: List<PostEntity>) {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.listFragmentRecycler)
        val recyclerViewAdapter = ListRecyclerAdapter(posts, this)
        recyclerView?.adapter = recyclerViewAdapter
        recyclerView?.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    override fun onListClick(postId: Int, userId: Int) {
        findNavController().navigate(
            R.id.detailsFragment,
            bundleOf("postId" to postId, "userId" to userId)
        )
    }

    private fun showProgressBar() {
        view?.findViewById<ProgressBar>(R.id.listFragmentProgressBar)?.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        view?.findViewById<ProgressBar>(R.id.listFragmentProgressBar)?.visibility = View.GONE
    }

    private fun showBanner() {
        view?.findViewById<TextView>(R.id.noPostsBanner)?.visibility = View.VISIBLE
    }

    private fun hideBanner() {
        view?.findViewById<TextView>(R.id.noPostsBanner)?.visibility = View.GONE
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = PostListFragment()
    }
}
