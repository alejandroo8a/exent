package com.example.alien.excent.network

import android.content.Context
import android.os.Environment
import com.example.alien.excent.network.login.signin.SignInRequest
import com.example.alien.excent.network.login.signin.SignInResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber

import java.io.*
import java.lang.reflect.Type

class CannedNetworkApi(private val context: Context, private val moshi: Moshi): NetworkApi {

    private val mediaTypeJson = MediaType.parse("application/json")
    private val emptyJsonResponse = ResponseBody.create(mediaTypeJson, "{}")

    override fun submitSignIn(signInRequest: SignInRequest): Single<SignInResponse> {
        return when (signInRequest.user) {
            "success" -> readFileForSingle("cannedData/SignInResponse.json", SignInResponse::class.java)
            "error" -> Single.error(HttpException(Response.error<ResponseBody>(500, emptyJsonResponse)))
            "connection" -> Single.error(HttpException(Response.error<ResponseBody>(408, emptyJsonResponse)))
            else -> Single.error(HttpException(Response.error<ResponseBody>(401, emptyJsonResponse)))
        }
    }

    private fun <C> readFileForSingle(filePath: String, responseClass: Class<C>): Single<C> {
        val type = Types.newParameterizedType(responseClass)
        return readFileForSingle(filePath, type)
    }

    private fun <C> readFileForSingle(filePath: String, type: Type): Single<C> {
        return try {
            val json = readFileForString(filePath)
            val adapter:JsonAdapter<C> = moshi.adapter(type)
            Single.fromCallable{ adapter.fromJson(json)}
        } catch (e: IOException) {
            Single.error(e)
        }
    }

    @Throws(IOException::class)
    private fun readFileForString(filePath: String): String {
        val jsonFile: InputStream = if (isExternalPathValid(filePath)) {
            FileInputStream(File(context.getExternalFilesDir(null), filePath))
        } else {
            context.assets.open(filePath)
        }

        return parseStream(jsonFile)
    }

    private fun isExternalPathValid(relativePath: String): Boolean {
        if (isExternalStorageMounted()) {
            val externalDir = context.getExternalFilesDir(null)
            if (externalDir != null
                    && externalDir.exists()
                    && externalDir.canRead()) {
                val file = File(context.getExternalFilesDir(null), relativePath)
                Timber.i("MOCK - Looking for file: %s", file.absolutePath)
                if (file.exists()) {
                    Timber.i("MOCK - External data found - using instead of in-app mocked data for file: %s", relativePath)
                    return true
                }
            }
        }
        Timber.i("MOCK - No external data found - using in-app mocked data for file: %s", relativePath)
        return false
    }

    @Throws(IOException::class)
    private fun parseStream(stream: InputStream): String {
        return stream.bufferedReader().use(BufferedReader::readText)
    }

    private fun isExternalStorageMounted(): Boolean {
        val state = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED.equals(state)
    }
}
