package com.spartak.recipesapp.di

import com.spartak.recipesapp.data.network.api.RecipeApi
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
object NetworkModule {

    @Provides
    fun provideClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor {
            val original: Request = it.request()
            val originalHttpUrl: HttpUrl = original.url
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter(RecipeApi.API_KEY_PARAMETER, RecipeApi.API_KEY)
                .build()
            val request = original.newBuilder().url(url).build()
            it.proceed(request)
        }.build()
    }

    @Provides
    fun provideRetrofitBuilder(client: OkHttpClient): Retrofit.Builder = Retrofit.Builder()
        .client(client)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())

    @Provides
    fun provideRecipeApi(retrofit: Retrofit.Builder): RecipeApi =
        retrofit.baseUrl(RecipeApi.BASE_URL).build().create(RecipeApi::class.java)
}