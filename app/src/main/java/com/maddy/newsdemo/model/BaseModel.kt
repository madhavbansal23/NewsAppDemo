package com.maddy.newsdemo.model

data class BaseModel<T>(
    val status: String?,
    val message: String?,
    val articles: T
)
