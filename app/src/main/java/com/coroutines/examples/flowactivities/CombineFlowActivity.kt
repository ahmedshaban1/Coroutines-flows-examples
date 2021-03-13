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
import kotlinx.coroutines.flow.*
import org.koin.android.ext.android.inject

/*
*
* the same as Zip operator with simple difference
* the different is zip operator wait for another flow to emit its values combine operator did not do this just get last emitted value of another flow and collect it for example
*  i have flow contain numbers 1,2,3 and emit every 1000 ms and another flow contain one,two,three values and emit value after 2000 ms
*  when collect for first time will emit 1 and wait for value from another flow that emit one value so collect has 1 and one value
* for second emit will emit value 2 and collect will be has value with 2 and one why ? because combine does not wait for second emit get last emitted value
* this the difference between zip and combine
* */

class CombineFlowActivity : AppCompatActivity() , MyHandlers {
    val api: ExampleApi by inject()
    val binding: ActivitySimpleFlowBinding by lazy {
        ActivitySimpleFlowBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.title = "Combine Flow"
        binding.handlers = this
    }

    override fun onDoSomeWorkClicked(view: View) {
        binding.loading.visible()
        //flow must run in Coroutine scope
        //run this Coroutine in IO scope because it is large job
        CoroutineScope(Dispatchers.IO).launch {
            //here i use combine operator to combine two flows posts flow and user flow
            //hint: number of emits of each flow must be the same because results mapped by order
            getPosts().combine(getUsers()) { posts, users ->
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
                //switch to main thread do bind data
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
            delay(1000)
            emit(api.getPosts())
            delay(1000)
            emit(api.getPosts())
        }
    }


    private fun getUsers(): Flow<List<UserModel>> {
        return flow {
            //some delay because api response very fast
            delay(2000)
            emit(api.getUsers())
            delay(2000)
            emit(api.getUsers())
            delay(2000)
            emit(api.getUsers())
        }
    }

    data class UserPosts(val user: UserModel, val posts: MutableList<PostModel>)


}