package com.sofaacademy.sofaminiproject.model

open class SofaResponse<T> (
    open val status: Int?,
    open val body: T?
)