package com.infinity.focusverse

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FocusVerseApp: Application() {
    override fun onCreate(){
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}

////public void onCreate ()
////
////Called when the application is starting, before any activity, service, or receiver objects (excluding content providers) have been created.
////Implementations should be as quick as possible (for example using lazy initialization of state) since the time spent in this function directly impacts the performance of starting the first activity, service, or receiver in a process.
////If you override this method, be sure to call super.onCreate().