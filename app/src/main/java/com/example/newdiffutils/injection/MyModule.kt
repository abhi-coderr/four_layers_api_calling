package com.example.newdiffutils.injection

import com.example.newdiffutils.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

const val BASE_URL = "https://jsonplaceholder.typicode.com/"
@Module
@InstallIn(SingletonComponent::class)
object MyModule {

    // TODO: implement the logging interceptor

    @Provides
    @Singleton
    fun retrofitInstance(): ApiService{
        val api : ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
        }
        return api
    }

}