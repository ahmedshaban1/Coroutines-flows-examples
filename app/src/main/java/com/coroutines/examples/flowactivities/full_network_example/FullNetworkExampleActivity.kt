package com.coroutines.examples.flowactivities.full_network_example

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.coroutines.examples.databinding.ActivitySimpleFlowBinding
import com.coroutines.examples.helpers.MyHandlers
import com.coroutines.examples.helpers.gone
import com.coroutines.examples.helpers.visible
import com.coroutines.examples.models.UserModel
import com.coroutines.examples.network.ExampleApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class FullNetworkExampleActivity : AppCompatActivity(), MyHandlers {
    val api: ExampleApi by inject()
    val binding: ActivitySimpleFlowBinding by lazy {
        ActivitySimpleFlowBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.title = "Network example"
        binding.handlers = this


    }

    override fun onDoSomeWorkClicked(view: View) {
        CoroutineScope(Dispatchers.Main).launch {
            getUsers().collect { response ->
                when (response.status) {
                    Resource.Status.LOADING -> binding.loading.visible()
                    Resource.Status.ERROR -> {
                        binding.loading.gone()
                        //handel error message
                        response.messageType
                    }
                    Resource.Status.SUCCESS -> {
                        binding.loading.gone()
                        binding.dataViewText.text = response.data.toString()
                    }
                }
            }
        }
    }


    fun getUsers(): Flow<Resource<List<UserModel>>> {
        return object : NetworkBoundResource<List<UserModel>>() {
            override suspend fun remoteFetch(): List<UserModel> {
                return api.getUsers()
            }

            override suspend fun saveFetchResult(data: List<UserModel>?) {
                //update local database if need
            }

            override suspend fun localFetch(): List<UserModel> {
                //if you need to load from local database before request
                return emptyList()
            }

            override fun shouldFetch() = true

        }.asFlow()

    }
}