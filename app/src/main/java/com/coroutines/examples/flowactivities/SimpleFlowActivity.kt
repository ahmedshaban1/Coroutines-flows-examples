package com.coroutines.examples.flowactivities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.coroutines.examples.databinding.ActivitySimpleFlowBinding
import com.coroutines.examples.helpers.MyHandlers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
/* Flow is :
* An asynchronous data stream that sequentially emits values and completes normally or with an exception.
**/


/*
*  the following example explain simple flow hwo ti work
*  for example we are waiting some friends and to come the parity and when everyone come
*  the parity manager say his or her name so we are waiting 4 friends ahmed,mohamed,ali and ashraf
*  the duration between each one 1 second for example
*
* */
class SimpleFlowActivity : AppCompatActivity(), MyHandlers {
    val binding: ActivitySimpleFlowBinding by lazy {
        ActivitySimpleFlowBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.title = "Simple Flow"
        binding.handlers = this
    }

    override fun onDoSomeWorkClicked(view: View) {
        //flow must run in Coroutine scope
        //run this Coroutine in main scope because it is sample job
        CoroutineScope(Dispatchers.Main).launch {
            //call collect function to collect every emit data
            getFriendsFlow().collect{friend ->
                binding.dataViewText.append("Your friend $friend arrived\n")
            }
            //code run after flow finish its work
            binding.dataViewText.append("Your friends arrived ")

        }

    }

    private fun getFriendsFlow(): Flow<String> {
        //create flow emit 4 friends one by one sequentially and delay one second between each emit
        return flow {
            emit("Ahmed")
            delay(1000)
            emit("Mohamed")
            delay(1000)
            emit("ali")
            delay(1000)
            emit("ashraf")

        }
    }


}