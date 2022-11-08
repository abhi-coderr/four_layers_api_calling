package com.example.newdiffutils.network

import com.example.newdiffutils.network.models.Todo
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("/todos")
    suspend fun getTodos() : Response<List<Todo>>


}