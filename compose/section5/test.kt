package com.example.coroutinelab.section5

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking{
//    val channel = Channel<Int>()
    //send 0
    //receive 0
    //receive 1
    //send 1
    //receive 2
    //send 2
    //receive 3
    //send 3
    //receive 4
    //send 4

    //buffer.....
//    val channel = Channel<Int>(capacity = 2)
    //send 0
    //receive 0
    //send 1
    //send 2
    //receive 1
    //send 3
    //receive 2
    //send 4
    //receive 3
    //receive 4

    val channel = Channel<Int>(capacity = 2, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    //==>발행은 5번, 구독은 4번... 
    //send 0
    //receive 0
    //send 1
    //send 2
    //receive 1
    //send 3
    //send 4
    //receive 3
    //receive 4

    launch {
        repeat(5){
            channel.send(it)
            println("send $it")
            delay(100)
        }
    }

    for(data in channel){
        println("receive $data")
        delay(300)
    }
}