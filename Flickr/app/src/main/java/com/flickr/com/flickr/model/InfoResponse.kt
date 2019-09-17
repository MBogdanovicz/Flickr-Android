package com.flickr.com.flickr.model

data class BaseInfo(val photo: PhotoInfo?, val stat: String?, val message: String?)

data class Dates(val taken: String?)

data class Description(val _content: String?)

data class PhotoInfo(val id: String?, val secret: String?, val server: String?, val farm: Number?, val title: Title?, val description: Description?, val dates: Dates?)

data class Title(val _content: String?)