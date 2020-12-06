package com.dwiyanstudio.nabungkuy.helper

interface ResponseListener {
    fun onSuccess()
    fun onFailure(error: String)
}