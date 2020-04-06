package vahan.com.weatherate.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import vahan.com.weatherate.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setHomeButtonEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
