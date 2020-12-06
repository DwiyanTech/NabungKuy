package com.dwiyanstudio.nabungkuy.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dwiyanstudio.nabungkuy.R
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nabung_splash)
        val dispose = Observable.interval(2, TimeUnit.SECONDS).take(1)
            .subscribe {
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        CompositeDisposable().add(dispose)
    }
}