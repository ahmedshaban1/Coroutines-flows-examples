package com.coroutines.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.coroutines.examples.databinding.ActivityMainBinding
import com.coroutines.examples.flowactivities.*
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


        binding.mapFlowBtn.setOnClickListener {
            openActivity(MapFlowActivity::class.java)
        }


        binding.zipFlowBtn.setOnClickListener {
            openActivity(ZipFlowActivity::class.java)
        }

        binding.combineFlowBtn.setOnClickListener {
            openActivity(CombineFlowActivity::class.java)
        }

        binding.takeFlowBtn.setOnClickListener {
            openActivity(TakeFlowActivity::class.java)
        }

        binding.filterFlowBtn.setOnClickListener {
            //openActivity(ZipFlowActivity::class.java)
        }

        binding.reduceFlowBtn.setOnClickListener {
            //openActivity(ZipFlowActivity::class.java)
        }



        binding.flatmapconactbtn.setOnClickListener {
            //openActivity(ZipFlowActivity::class.java)
        }


        binding.merge.setOnClickListener {
            //openActivity(ZipFlowActivity::class.java)
        }

        binding.flatmaplatest.setOnClickListener {
            //openActivity(ZipFlowActivity::class.java)
        }


        binding.catchFlowBtn.setOnClickListener {
            //openActivity(ZipFlowActivity::class.java)
        }

        binding.networkingFlowBtn.setOnClickListener {
            //openActivity(ZipFlowActivity::class.java)
        }


    }


}