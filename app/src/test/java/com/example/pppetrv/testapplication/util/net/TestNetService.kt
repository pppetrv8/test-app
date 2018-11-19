package com.example.pppetrv.testapplication.util.net

import com.example.pppetrv.testapplication.net.NetService
import com.example.pppetrv.testapplication.net.ServiceApi

import retrofit2.Retrofit
import retrofit2.mock.BehaviorDelegate
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior

import javax.inject.Inject

class TestNetService @Inject constructor(): NetService() {

    override fun getApiService(isXml: Boolean, nullable: Boolean): ServiceApi? {
        val delegate = getMockApiService(isXml, nullable)
        return TestServiceApi(delegate)
    }

    private fun getMockApiService(isXml: Boolean, nullable: Boolean): BehaviorDelegate<ServiceApi> {
        return if (isXml) {
            val serviceApiXml = getMockRetrofitInstanceGson(nullable).create(ServiceApi::class.java)
            serviceApiXml
        } else {
            val serviceApiGson = getMockRetrofitInstanceXml(nullable).create(ServiceApi::class.java)
            serviceApiGson
        }
    }

    private fun getMockRetrofit(retrofit: Retrofit?): MockRetrofit {
        val behavior = NetworkBehavior.create()
        return MockRetrofit.Builder(retrofit)
                .networkBehavior(behavior)
                .build()
    }

    private fun getMockRetrofitInstanceGson(nullable: Boolean): MockRetrofit {
        val retrofit = getRetrofitInstanceGson(nullable)
        return getMockRetrofit(retrofit)
    }

    private fun getMockRetrofitInstanceXml(nullable: Boolean): MockRetrofit {
        val retrofit = getRetrofitInstanceXml(nullable)
        return getMockRetrofit(retrofit)
    }
}
