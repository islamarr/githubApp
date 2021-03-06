package com.islam.githubapp.data

sealed class Resource<out T> {

    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val exception: String) : Resource<Nothing>()
    object Loading : Resource<Nothing>()

}

val Resource<*>.succeeded
    get() = this is Resource.Success<*> && data != null