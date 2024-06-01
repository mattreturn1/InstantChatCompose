package com.example.chatto

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * the class Application annotated with @HiltAndroidApp which
 * will take care of injecting members into the Application class
 */
@HiltAndroidApp
class Application : Application()