package com.coroutines.examples.flowactivities.parallel_requests

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.coroutines.examples.R
import com.coroutines.examples.helpers.gone
import com.coroutines.examples.helpers.visible
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.scope.lifecycleScope
import org.koin.android.viewmodel.ext.android.viewModel

class ParallelRequestsActivity : AppCompatActivity() {
    val viewModel:ParallelRequestsViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parallel_requests)
        val resultTv = findViewById<TextView>(R.id.results)
        val loadingView = findViewById<ProgressBar>(R.id.loading)
        viewModel.loadUserDataAndComments()

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.state.collect{
                    when(it){
                        is Resource.Error -> {}
                        Resource.Loading -> {
                            loadingView.visible()
                        }
                        is Resource.Success -> {
                            it.data.forEach {
                                loadingView.gone()
                                resultTv.append("${it.title}\nnumber of post comments ${it.comments.size}\n\n")
                            }
                        }
                    }
            }
        }


    }
}

