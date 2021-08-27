package com.mudhut.postsapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mudhut.postsapp.db.models.*
import com.mudhut.postsapp.db.relations.PostWithComments
import com.mudhut.postsapp.repositories.LocalRepository
import com.mudhut.postsapp.repositories.RemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) : ViewModel() {

    private val _postList = MutableLiveData<List<PostEntity>>()
    val postList: LiveData<List<PostEntity>> get() = _postList

    private val _postWithComments = MutableLiveData<PostWithComments>()
    val postWithComments: LiveData<PostWithComments> get() = _postWithComments

    private val _user = MutableLiveData<UserEntity>()
    val user: LiveData<UserEntity> get() = _user

    fun getPosts() {
        viewModelScope.launch {
            localRepository.getPosts().collect {
                if (it.isEmpty()) {
                    fetchRemoteData()
                } else {
                    _postList.postValue(it)
                }
            }
        }
    }

    fun getUser(userId: Int) {
        viewModelScope.launch {
            _user.postValue(localRepository.getUser(userId))
        }
    }

    fun getPostWithComments(postId: Int) {
        viewModelScope.launch {
            _postWithComments.postValue(localRepository.getPostWithComments(postId))
        }
    }

    private fun fetchRemoteData() {
        insertUsers()
        insertPosts()
        insertComments()
    }

    private fun insertUsers() {
        viewModelScope.launch {
            val users = remoteRepository.getUsers()
            if (users.isNotEmpty()) {
                users.forEach {
                    val user = UserEntity(
                        it.id,
                        it.name,
                        it.email,
                        Address(
                            it.address.street,
                            it.address.suite,
                            it.address.city,
                            it.address.zipcode,
                            Geo(it.address.geo.lat, it.address.geo.lng)
                        ),
                        it.phone,
                        it.website,
                        Company(it.company.name, it.company.catchPhrase, it.company.bs)
                    )
                    localRepository.insertUser(user)
                }
            }
        }
    }

    private fun insertPosts() {
        viewModelScope.launch {
            val posts = remoteRepository.getPosts()
            if (posts.isNotEmpty()) {
                posts.forEach {
                    val post = PostEntity(it.id, it.userId, it.title, it.body)
                    localRepository.insertPost(post)
                }
            }
        }
    }

    private fun insertComments() {
        viewModelScope.launch {
            val comments = remoteRepository.getComments()
            if (comments.isNotEmpty()) {
                comments.forEach {
                    val comment = CommentEntity(it.id, it.postId, it.name, it.email, it.body)
                    localRepository.insertComment(comment)
                }
            }
        }
    }

}
