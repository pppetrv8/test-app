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
        return TestServiceApi()
    }
}
