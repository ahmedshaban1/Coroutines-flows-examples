package com.coroutines.examples.flowactivities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.coroutines.examples.databinding.ActivitySimpleFlowBinding
import com.coroutines.examples.helpers.MyHandlers
import com.coroutines.examples.helpers.gone
import com.coroutines.examples.helpers.visible
import com.coroutines.examples.models.PostModel
import com.coroutines.examples.models.UserModel
import com.coroutines.examples.network.ExampleApi
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.zip
import org.koin.android.ext.android.inject


/*
* this time for zip operator this operator very important to do magic work
* i will explain with example directly imagine we have two or more api  calls or large job
* and we need to wait all calls and combine it in one data class and use it , zip operator solve this problem
* Our example call two apis one for posts and another for users and i need to combine two responses in one list
* each item in list represents one user and user's posts
*
*
* */

class ZipFlowActivity : AppCompatActivity(), MyHandlers {
    val api: ExampleApi by inject()
    val binding: ActivitySimpleFlowBinding by lazy {
        ActivitySimpleFlowBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.title = "Zip Flow"
        binding.handlers = this
    }

    override fun onDoSomeWorkClicked(view: View) {
        binding.loading.visible()
        //flow must run in Coroutine scope
        //run this Coroutine in IO scope because it is large job
        CoroutineScope(Dispatchers.IO).launch {
            //here i use zip operator to combine two flows posts flow and user flow
            //hint: number of emits of each flow must be the same because results mapped by order
            //that mean results in first emit in posts flow wait the results of first emit in users flow
            // and  the emit in posts flow wait the results of the first emit in users flow
            //in our example i emit one time in every flow
            getPosts().zip(getUsers()) { posts, users ->
                val list: MutableList<UserPosts> = mutableListOf()
                users.forEach { user ->
                    list.add(
                        UserPosts(
                            user = user,
                            posts = posts.filter { it.userId == user.id }.toMutableList()
                        )
                    )
                }
                list
            }.collect { results ->
                //switch to main third do bind data
                withContext(Dispatchers.Main) {
                    //display data
                    results.forEach {
                        binding.dataViewText.append("${it.user.name} number of posts ${it.posts.size}\n")
                    }

                    binding.loading.gone()
                }
            }


        }


    }

    private fun getPosts(): Flow<List<PostModel>> {
        return flow {
            //some delay because api response very fast
            delay(1000)
            emit(api.getPosts())
        }
    }


    private fun getUsers(): Flow<List<UserModel>> {
        return flow {
            //some delay because api response very fast
            delay(1000)
            emit(api.getUsers())
        }
    }

    data class UserPosts(val user: UserModel, val posts: MutableList<PostModel>)


}