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
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import org.koin.android.ext.android.inject
/*
*   filter operator very useful if we have some conditions needed on data,
*  so filter in flow triggered in evey emit and collect triggered if condition in filter operator is true,
*  in our example call getposts apis and emit every post and check if userid in post object equals 1 so, we will collect only posts with
*  user id equals 1
* */

class FilterFlowActivity : AppCompatActivity(), MyHandlers {
    val api: ExampleApi by inject()
    val binding: ActivitySimpleFlowBinding by lazy {
        ActivitySimpleFlowBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.title = "Filter Flow"
        binding.handlers = this
    }

    override fun onDoSomeWorkClicked(view: View) {
        binding.loading.visible()
        //flow must run in Coroutine scope
        //run this Coroutine in IO scope because it is large job
        CoroutineScope(Dispatchers.IO).launch {
            //here i use filter operator to some condition before collecting data
            getPosts().filter { data ->
                data.userId == 1
            }.collect { post ->
                //switch to main third do bind data
                withContext(Dispatchers.Main) {
                    binding.dataViewText.append("post title ${post.title} with user id ${post.userId} \n")
                    binding.loading.gone()
                }
            }


        }


    }

    private fun getPosts(): Flow<PostModel> {
        return flow {
            //some delay because api response very fast
            delay(1000)
            val results = api.getPosts()
            results.forEach {
                emit(it)
                //some delay between each emit
                delay(200)

            }
        }
    }


}
