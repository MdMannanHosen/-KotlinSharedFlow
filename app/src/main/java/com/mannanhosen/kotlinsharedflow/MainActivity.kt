package com.mannanhosen.kotlinsharedflow
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mannanhosen.kotlinsharedflow.ui.theme.KotlinSharedFlowTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlinSharedFlowTheme {
                lifecycleScope.launch(Dispatchers.Main) {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        val result = producer()

                        result.collect {
                            Log.d("Producer", "item 1- $it")
                        }
                    }
                }

                    lifecycleScope.launch(Dispatchers.Main) {
                        repeatOnLifecycle(Lifecycle.State.STARTED) {
                            val result = producer()

                            result.collect {
                                Log.d("Producer", "item 2- $it")
                            }
                        }
                    }
                }
            }
        }

        fun producer(): Flow<Int> {
            val mutableSharedFlow = MutableSharedFlow<Int>()
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                    list.forEach {
                        mutableSharedFlow.emit(it)
                        delay(1000)
                    }
                }
            }
            return mutableSharedFlow
        }

    }




