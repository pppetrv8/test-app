package com.example.pppetrv.testapplication.net

import com.example.pppetrv.testapplication.net.dto.BankDepartmentDto
import com.example.pppetrv.testapplication.net.simplexml.CustomSimpleXmlConverterFactory
import com.example.pppetrv.testapplication.util.Constants.SERVER_LINK
import com.example.pppetrv.testapplication.util.Constants.TLS_VER
import com.google.gson.GsonBuilder
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.simpleframework.xml.convert.AnnotationStrategy
import org.simpleframework.xml.core.Persister
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.inject.Inject
import javax.net.ssl.*

open class NetService @Inject constructor() {

    private var retrofitGson: Retrofit? = null
    private var retrofitXml: Retrofit? = null

    private var serviceApiGson: ServiceApi? = null
    private var serviceApiXml: ServiceApi? = null

    open fun getCurrencyRates(bankId: Int): Flowable<BankDepartmentDto> {
        val call = getApiService(true, false)?.getCurrencyRates()
        return wrapCallToFlowable(call)
    }

    fun <T> wrapCallToFlowable(call: Call<T>?): Flowable<T> {
        return Flowable.create({ e ->
            e.setCancellable {call?.cancel()}
            call?.enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>?, r: Response<T>?) {
                    if (r?.body() == null) {
                        e.onError(Throwable("Response is null"))
                    } else {
                        e.onNext(r.body() as T)
                    }
                    e.onComplete()
                }
                override fun onFailure(call: Call<T>?, t: Throwable?) {
                    var ex = t
                    if (ex == null) {
                        ex = Throwable("Exception was empty")
                    }
                    e.onError(ex)
                }
            })
        }, BackpressureStrategy.BUFFER)
    }

    open fun getApiService(isXml: Boolean, nullable: Boolean): ServiceApi? {
        return if (isXml) {
            if (serviceApiXml == null) {
                serviceApiXml = getRetrofitInstanceXml(nullable)?.create(ServiceApi::class.java)
            }
            serviceApiXml
        } else {
            if (serviceApiGson == null) {
                serviceApiGson = getRetrofitInstanceGson(nullable)?.create(ServiceApi::class.java)
            }
            serviceApiGson
        }
    }

    open fun getRetrofitInstanceGson(nullable: Boolean): Retrofit? {
        if (retrofitGson == null) {
            val builder = GsonBuilder().setLenient()
            if (nullable) {
                builder.serializeNulls()
            }
            val gson = builder.create()
            val gsonFactory = GsonConverterFactory.create(gson)
            retrofitGson = createRetrofit(SERVER_LINK, gsonFactory, false)
        }
        return retrofitGson
    }

    open fun getRetrofitInstanceXml(nullable: Boolean): Retrofit? {
        if (retrofitXml == null) {
            val builder = GsonBuilder().setLenient()
            if (nullable) {
                builder.serializeNulls()
            }
            val xmlFactory = CustomSimpleXmlConverterFactory.createNonStrict(Persister(AnnotationStrategy()))
            retrofitXml = createRetrofit(SERVER_LINK, xmlFactory, false)
        }
        return retrofitXml
    }

    open fun createRetrofit(baseUrl: String, factory: Converter.Factory, nullable: Boolean): Retrofit? {
        val builder = GsonBuilder().setLenient()
        if (nullable) {
            builder.serializeNulls()
        }
        val client = getUnsafeOkHttpClient()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(factory)
            .build()
    }

    protected open fun getUnsafeOkHttpClient(): OkHttpClient? {
        try {
            var builder = OkHttpClient.Builder()
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder = builder.addInterceptor(interceptor)
            val trustManager = MyTrustManager()
            val sslFactory = getSslSocketFactory(trustManager)
            builder = builder.sslSocketFactory(sslFactory, trustManager)
            builder = builder.hostnameVerifier(NullHostNameVerifier())
            return builder.build()
        } catch (e: Exception) {
            //throw RuntimeException(e)
            Timber.e(e)
        }
        return null
    }

    fun getSslSocketFactory(trustManager: X509TrustManager): javax.net.ssl.SSLSocketFactory? {
        var sslContext: SSLContext? = null
        try {
            // Create an SSLContext that uses our TrustManager
            sslContext = SSLContext.getInstance(TLS_VER)
            val trustManagers = arrayOf<TrustManager>(trustManager)
            sslContext?.init(null, trustManagers, null)
            sslContext.socketFactory
        } catch (e: NoSuchAlgorithmException) {
            Timber.e(e)
        } catch (e: KeyManagementException) {
            Timber.e(e)
        }
        return sslContext?.socketFactory
    }

    class MyTrustManager : X509TrustManager {

        @Throws(CertificateException::class)
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {

        }

        @Throws(CertificateException::class)
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {

        }

        override fun getAcceptedIssuers(): Array<X509Certificate?> {
            return arrayOfNulls<X509Certificate?>(0)

        }
    }

    class NullHostNameVerifier : HostnameVerifier {
        override fun verify(hostname: String, session: SSLSession): Boolean {
            return true
        }
    }
}

