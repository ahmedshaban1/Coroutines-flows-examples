package com.coroutines.examples.flowactivities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.coroutines.examples.databinding.ActivitySimpleFlowBinding
import com.coroutines.examples.helpers.MyHandlers
import com.coroutines.examples.helpers.gone
import com.coroutines.examples.helpers.visible
import com.coroutines.examples.models.PostModel
import com.coroutines.examples.network.ExampleApi
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import org.koin.android.ext.android.inject


/*
*  Take operator is very simple operator for we example we have flow and inside flow we have 100 emits and i need to get only first 10 emits
* just use take operator as following example
*
*
* */

class TakeFlowActivity : AppCompatActivity(), MyHandlers {
    val api: ExampleApi by inject()
    val binding: ActivitySimpleFlowBinding by lazy {
        ActivitySimpleFlowBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.title = "Take Flow"
        binding.handlers = this
    }

    override fun onDoSomeWorkClicked(view: View) {
        binding.loading.visible()
        //flow must run in Coroutine scope
        //run this Coroutine in IO scope because it is large job
        CoroutineScope(Dispatchers.IO).launch {
            //here i use take operator to just get 10 emits
            getPosts().take(10).collect { post ->
                //switch to main third do bind data
                withContext(Dispatchers.Main) {
                    binding.dataViewText.append("title is : ${post.title}\n")
                    binding.loading.gone()
                }
            }


        }


    }

    private fun getPosts(): Flow<PostModel> {
        return flow {
            //some delay because api response very fast
            delay(1000)
            val result  = api.getPosts()

            result.forEach {
                emit(it)
            }
        }
    }


}