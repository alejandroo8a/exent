package com.example.alien.excent.module

import android.content.Context
import com.example.alien.excent.BuildConfig
import com.example.alien.excent.data.SubjectSupplier
import com.example.alien.excent.network.CannedNetworkApi
import com.example.alien.excent.network.NetworkApi
import com.example.alien.excent.network.adapters.ActionTypeAdapter
import com.example.alien.excent.network.adapters.NullToEmptyStringAdapter
import com.example.alien.excent.network.adapters.StringDateAdapter
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton
import com.example.alien.excent.preferences.auth.AuthPreferences
import io.reactivex.subjects.CompletableSubject
import okhttp3.Response
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
class NetworkModule {

    enum class DataSource {
        REAL,
        MIXED,
        CANNED
    }

    companion object {
        private const val KEY_API_BASE_URL = "key_api_base_url"
    }

    val currentSource = DataSource.CANNED

    val HEADER_NAME_AUTHORIZATION = "Authorization"
    val HEADER_VALUE_BEARER = "Bearer "
    val HEADER_NAME_TOKEN_EXPIRED = "Token-Expired"
    val HEADER_VALUE_TOKEN_EXPIRED = "true"

    @Provides
    @Singleton
    fun providesOkHttpClient(authPreferences: AuthPreferences, subjectSupplier: SubjectSupplier): OkHttpClient {
        val okhttpclientBuilder = OkHttpClient.Builder()
                .addInterceptor(createAuthInterceptor(authPreferences))
                .addInterceptor(createLogoutInterceptor(authPreferences, subjectSupplier.logoutSubject!!))
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .connectTimeout(1, TimeUnit.MINUTES)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            okhttpclientBuilder.addInterceptor(logging)
        }

        return okhttpclientBuilder.build()
    }

    @Provides
    @Singleton
    fun providesMoshi(): Moshi {
        return Moshi.Builder()
                //.add(ActionTypeAdapter())
                //.add(StringDateAdapter())
                .add(NullToEmptyStringAdapter())
                .build()
    }

    @Provides
    @Named(KEY_API_BASE_URL)
    fun provideReferralsApiBaseUrl(): String {
        return "https://econocheckwebv2.azurewebsites.net/api/"
    }

    @Provides
    @Singleton
    fun providesRetrofit(@Named(KEY_API_BASE_URL) baseUrl: String, okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    fun providesNetworkApi(retrofit: Retrofit, context: Context, moshi: Moshi): NetworkApi {
        when (currentSource) {
            DataSource.CANNED -> return CannedNetworkApi(context, moshi)
            //MIXED -> return MixedNetworkApi(context, moshi, retrofit.create<T>(NetworkApi::class.java))
            else -> return retrofit.create(NetworkApi::class.java)
        }
    }

    private fun createAuthInterceptor(authPreferences: AuthPreferences): Interceptor {
        return Interceptor {
            val authToken = authPreferences.getAuthToken()

            val request = it.request()
            val requestBuilder = request.newBuilder()
            if (request.headers().values(HEADER_NAME_AUTHORIZATION).isEmpty() && authToken != null) {
                requestBuilder.addHeader(HEADER_NAME_AUTHORIZATION, HEADER_VALUE_BEARER + authToken!!)
            } else {
                Timber.d("Auth token is null; not adding it to header")
            }
            it.proceed(requestBuilder.build())
        }
    }

    private fun createLogoutInterceptor(authPreferences: AuthPreferences, logoutSubject: CompletableSubject): Interceptor {
        return Interceptor {
            val request = it.request()
            val response = it.proceed(request)
            val authToken = authPreferences.getAuthToken()
            if (authToken != null && responseIndicatesExpiredAuthToken(response)) {
                Timber.d("Invalid auth token. Logging out...")
                logoutSubject.onComplete()
            }
            response
        }
    }

    private fun responseIndicatesExpiredAuthToken(response: Response): Boolean {
        val isExpired = response.header(HEADER_NAME_TOKEN_EXPIRED, null)
        return isExpired != null && isExpired == HEADER_VALUE_TOKEN_EXPIRED
    }
}