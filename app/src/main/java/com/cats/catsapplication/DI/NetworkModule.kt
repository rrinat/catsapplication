package com.cats.catsapplication.DI

import com.cats.catsapplication.api.ApiKeyInterceptor
import com.cats.catsapplication.api.ApiService
import com.cats.catsapplication.core.data.repository.FileRepository
import com.cats.catsapplication.core.data.repository.FileRepositoryImpl
import com.cats.catsapplication.core.domain.interactor.FileInteractor
import com.cats.catsapplication.core.domain.interactor.FileInteractorImpl
import com.cats.catsapplication.core.utils.FileProvider
import com.cats.catsapplication.core.utils.ResourceProvider
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetworkModule (private val endpoint: String,
                     private val apiKey: String) {

    companion object {
        private const val TIME_OUT = 120L
    }

    @Provides
    internal fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        builder.readTimeout(TIME_OUT, TimeUnit.SECONDS)
        builder.writeTimeout(TIME_OUT, TimeUnit.SECONDS)
        builder.addInterceptor(ApiKeyInterceptor(apiKey))
        return builder.build()
    }

    @Provides
    internal fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    internal fun provideCallAdapterFactory(): CallAdapter.Factory =
        RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())

    @Provides
    internal fun provideRetrofit(client: OkHttpClient,
                        gson: Gson,
                        factory: CallAdapter.Factory): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(endpoint)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addCallAdapterFactory(factory)
            .build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    internal fun provideFileRepository(apiService: ApiService,
                                       fileProvider: FileProvider,
                                       resourceProvider: ResourceProvider
    ): FileRepository = FileRepositoryImpl(apiService, fileProvider, resourceProvider)

    @Provides
    fun provideFileInteractor(fileRepository: FileRepository): FileInteractor = FileInteractorImpl(fileRepository)
}