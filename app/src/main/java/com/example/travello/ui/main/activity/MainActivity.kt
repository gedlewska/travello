package com.example.travello.ui.main.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.travello.R
import com.example.travello.ui.main.fragment.MainFragment


class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "Main Activity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.container, MainFragment.newInstance(), MainFragment.TAG)
            }
        }
    }
}