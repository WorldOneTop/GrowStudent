package com.worldonetop.growstudent.di

import android.content.Context
import com.worldonetop.growstudent.source.Remote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private val URL = "https://grow-student-server-wjouw.run.goorm.io/"

    @Singleton
    @Provides
    fun provideCongressService(@ApplicationContext context: Context): Remote {
        return Retrofit.Builder()
            .baseUrl(URL)
            .client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Remote::class.java)
    }
    private fun provideOkHttpClient():OkHttpClient{

        val logger = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
    }
}