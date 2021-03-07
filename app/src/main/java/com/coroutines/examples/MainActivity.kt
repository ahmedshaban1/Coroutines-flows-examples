package com.coroutines.examples

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.coroutines.examples.databinding.ActivityMainBinding
import com.coroutines.examples.flowactivities.SimpleFlowActivity
import com.coroutines.examples.helpers.openActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.simpleFlowBtn.setOnClickListener {
            openActivity(SimpleFlowActivity::class.java)
        }

    }


}