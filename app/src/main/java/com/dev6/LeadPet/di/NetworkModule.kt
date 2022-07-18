package com.dev6.LeadPet.di

import com.dev6.data.service.*
import com.dev6.data.service.FeedAPI
import com.dev6.data.service.JoinAPI
import com.dev6.data.service.LoginAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
open class NetworkModule {

    protected open fun baseUrl() = "http://localhost:8080/".toHttpUrl()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpRequestInterceptor())
            .connectTimeout(5, TimeUnit.SECONDS)
            .callTimeout(5, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideJoinService(retrofit: Retrofit): JoinAPI {
        return retrofit.create(JoinAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideLoginService(retrofit: Retrofit): LoginAPI {
        return retrofit.create(LoginAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideLifeService(retrofit: Retrofit): LifePostAPI {
        return retrofit.create(LifePostAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideDonateService(retrofit: Retrofit): DonatePostAPI {
        return retrofit.create(DonatePostAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideAdoptService(retrofit: Retrofit): AdoptPostAPI {
        return retrofit.create(AdoptPostAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideFeedService(retrofit: Retrofit): FeedAPI {
        return retrofit.create(FeedAPI::class.java)
    }
}