package com.flickr.com.flickr.model

data class BaseSize(val sizes: Sizes?, val stat: String?, val message: String?)

data class Size(val label: String?, val width: String?, val height: String?, val source: String?, val url: String?, val media: String?)

data class Sizes(val canblog: Number?, val canprint: Number?, val candownload: Number?, val size: List<Size>?)