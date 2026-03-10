package app.migrosone.network.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import app.migrosone.contract.BaseUrl
import app.migrosone.network.TimeoutType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RetrofitModule {

    @Provides
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(TimeoutType.DEFAULT_CON_TIMEOUT.timeout, TimeUnit.SECONDS)
            .readTimeout(TimeoutType.DEFAULT_TIMEOUT.timeout, TimeUnit.SECONDS)
            .writeTimeout(TimeoutType.DEFAULT_TIMEOUT.timeout, TimeUnit.SECONDS)
            .followSslRedirects(true)
            .addInterceptor(
                ChuckerInterceptor.Builder(context)
                    .alwaysReadResponseBody(true)
                    .build()
            )
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        @BaseUrl baseUrl: String,
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    fun provideGsonConverterFactory(gson: Gson): Converter.Factory {
        return GsonConverterFactory.create(gson)
    }
}
