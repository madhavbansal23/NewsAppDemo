package com.maddy.newsdemo

import android.app.Application
import android.content.Context
import com.maddy.newsdemo.di.*

class Appcontroller : Application()
{

    lateinit var mApiComponent:ApiComponent

    override fun onCreate() {
        super.onCreate()
        app = this

        mApiComponent = DaggerApiComponent.builder()
            .appModule(AppModule(this))
            .apiHelper(ApiHelper())
            .dBModule(DBModule(this))
            .build()

    }

    companion object {
        lateinit var app: Appcontroller
            private set
    }

}