package com.gsrg.data.common.remote.adapter

interface RetrofitAdapter {
    fun <T> getRetrofitInterface(api: Class<T>): T
}
