package com.coroutines.examples.flowactivities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.coroutines.examples.databinding.ActivitySimpleFlowBinding
import com.coroutines.examples.helpers.MyHandlers
import com.coroutines.examples.helpers.gone
import com.coroutines.examples.helpers.visible
import com.coroutines.examples.models.PostEntity
import com.coroutines.examples.models.PostModel
import com.coroutines.examples.network.ExampleApi
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.koin.android.ext.android.inject
/*
* in this example we will use retofit to get some posts from api
* the main goal of this example that getting data from api  and map it to view with another data type
*
* how to use this example in real app ->
* assume that we use the model as backend returned and backend developer suddenly change model structure is this case we need to go to app and
* change this model like backend so the solution is two thing one for backend model and one for app and mapping layer between them
* in our example i will user postmodel for back end and postentity for app and user flow to map post model to post entity so if backend change anything
* i just need to change change mapping code only and app will work fine
*
* */
class MapFlowActivity : AppCompatActivity(), MyHandlers {
    val api: ExampleApi by inject()
    val binding: ActivitySimpleFlowBinding by lazy {
        ActivitySimpleFlowBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.title = "Map Flow"
        binding.handlers = this
    }

    override fun onDoSomeWorkClicked(view: View) {
        binding.loading.visible()
        //flow must run in Coroutine scope
        //run this Coroutine in IO scope because it is large job
        CoroutineScope(Dispatchers.IO).launch {
            getPosts().map {dataBeforMapping->
                dataBeforMapping.map {post-> PostEntity(post.body!!,post.title!!) }
            }.collect {dataAfterMapping->
                //switch to main third do bind data
                withContext(Dispatchers.Main){
                    binding.dataViewText.text = dataAfterMapping.toString()
                    binding.loading.gone()
                }
            }


        }


    }

    fun getPosts() : Flow<List<PostModel>>{
        return flow{
            delay(1000)
            emit(api.getPosts())
        }
        //some delay becuase api response very fast
    }


}