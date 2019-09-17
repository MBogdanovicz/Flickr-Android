package com.flickr.com.flickr.model

import java.io.Serializable

data class BasePhoto(val photos: Photos?, val stat: String, val message: String?)

data class Photo(val id: String, val owner: String, val secret: String, val server: String, val farm: Int, val title: String, val ispublic: Int, val isfriend: Int, val isfamily: Int) : Serializable

data class Photos(val page: Int, val pages: Int, val perpage: Int, val total: String, val photo: ArrayList<Photo>)
