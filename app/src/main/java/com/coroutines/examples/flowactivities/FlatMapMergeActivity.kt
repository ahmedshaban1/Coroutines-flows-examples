package com.coroutines.examples.flowactivities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.coroutines.examples.databinding.ActivitySimpleFlowBinding
import com.coroutines.examples.helpers.MyHandlers
import com.coroutines.examples.helpers.gone
import com.coroutines.examples.helpers.visible
import com.coroutines.examples.models.Comment
import com.coroutines.examples.models.PostModel
import com.coroutines.examples.network.ExampleApi
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.koin.android.ext.android.inject
/*
* the same as flatmapconcat but with different mode the main difference is collect all incoming value into one flow and value
* emitted as soon as possible no need to wait any thing to complete so flatmapmerge faster than flatmapconcat but the usage of
* them depend on your use case
*
* */

@FlowPreview
class FlatMapMergeActivity  : AppCompatActivity(), MyHandlers {
    val api: ExampleApi by inject()
    val binding: ActivitySimpleFlowBinding by lazy {
        ActivitySimpleFlowBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.title = "Flat Map Merge Flow"
        binding.handlers = this
    }

    override fun onDoSomeWorkClicked(view: View) {
        binding.loading.visible()
        //flow must run in Coroutine scope
        //run this Coroutine in IO scope because it is large job
        CoroutineScope(Dispatchers.IO).launch {
            //here i used  flatMapConcat operator
            getPosts()
                .flatMapMerge{ post ->
                    requestPostCommentsPostID(post)
                }.collect { postComments ->
                    //switch to main thread do bind data
                    withContext(Dispatchers.Main) {
                        postComments.forEach{
                            binding.dataViewText.append("post id ${it.postId} with commenter email  ${it.email} \n")
                        }
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
            for (i in 0..2) {
                emit(results[i])
                //some delay between each emit
                delay(200)
            }
        }
    }

    private fun requestPostCommentsPostID(postModel: PostModel): Flow<List<Comment>> {
        return flow {
            //some delay because api response very fast
            delay(500)
            emit(api.getPostsComments(postModel.id!!))
        }
    }


}