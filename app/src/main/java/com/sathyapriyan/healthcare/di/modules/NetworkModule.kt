package com.sathyapriyan.healthcare.di.modules

import com.sathyapriyan.healthcare.data.remote.HealthCareApi
import com.sathyapriyan.healthcare.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val timeOutDurationInSeconds = 15L

    @Singleton
    @Provides
    fun provideHTTPClient(): OkHttpClient {

        return OkHttpClient
            .Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder().addHeader(
                    name = Constants.APP_ID_KEY,
                    value = Constants.APP_ID_VALUE
                ).build()

                chain.proceed(request = request)
            }
            .readTimeout(timeOutDurationInSeconds, TimeUnit.SECONDS)
            .connectTimeout(timeOutDurationInSeconds, TimeUnit.SECONDS)
            .build()

    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideScalarConverterFactory(): ScalarsConverterFactory = ScalarsConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
        scalarsConverterFactory: ScalarsConverterFactory
    ): Retrofit {

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(scalarsConverterFactory)
            .addConverterFactory(gsonConverterFactory)
            .build()

    }

    @Singleton
    @Provides
    fun provideHealthCareApi(
        retrofit: Retrofit
    ): HealthCareApi {
        return retrofit.create(HealthCareApi::class.java)
    }

}