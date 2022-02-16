package com.coroutines.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.coroutines.examples.databinding.ActivityMainBinding
import com.coroutines.examples.flowactivities.*
import com.coroutines.examples.flowactivities.full_network_example.FullNetworkExampleActivity
import com.coroutines.examples.flowactivities.parallel_requests.ParallelRequestsActivity
import com.coroutines.examples.helpers.openActivity
import com.coroutines.examples.helpers.showSnackbar

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
            openActivity(FilterFlowActivity::class.java)
        }

        binding.flatmapconactbtn.setOnClickListener {
            openActivity(FlatMapConcatActivity::class.java)
        }


        binding.merge.setOnClickListener {
            openActivity(FlatMapMergeActivity::class.java)
        }

        binding.parallelRequestsBtn.setOnClickListener {
            openActivity(ParallelRequestsActivity::class.java)
        }


        binding.catchFlowBtn.setOnClickListener {
           // openActivity(CatchFlowActivity::class.java)
            showSnackbar("under development")

        }

        binding.networkingFlowBtn.setOnClickListener {
            openActivity(FullNetworkExampleActivity::class.java)
        }


    }


}