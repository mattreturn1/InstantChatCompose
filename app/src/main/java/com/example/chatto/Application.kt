package com.example.chatto

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
the annotation @HiltAndroidApp will take care of injecting members into the Application class
 */
@HiltAndroidApp
class Application : Application()