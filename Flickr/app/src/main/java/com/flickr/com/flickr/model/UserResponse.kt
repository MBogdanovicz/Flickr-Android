package com.flickr.com.flickr.model

data class BaseUser(val user: User, val stat: String, val message: String?)

data class User(val id: String, val nsid: String, val username: Username)

data class Username(val _content: String)
