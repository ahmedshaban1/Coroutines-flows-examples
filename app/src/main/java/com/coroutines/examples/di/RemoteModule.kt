package com.coroutines.examples.di

import com.coroutines.examples.network.ExampleApi
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun getRemoteModule(baseUrl: String) = module {
    single {
        Retrofit.Builder().client(get()).baseUrl(baseUrl)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }


    factory {
        GsonBuilder()
            .setLenient()
            .create();
    }


    factory<Interceptor> {
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    factory {
        OkHttpClient.Builder()
            .addInterceptor(get())
            .build()
    }


    single {
        get<Retrofit>().create(ExampleApi::class.java)
    }



}