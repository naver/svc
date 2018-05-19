package com.naver.android.svc.sample

import android.app.Application
import com.naver.android.svc.SvcConfig

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        SvcConfig.debugMode = true
    }

}