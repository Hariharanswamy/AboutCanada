package com.hariharan.aboutcanada

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hariharan.aboutcanada.ui.main.MainFragment

/**
 * Launcher activity of the app.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}