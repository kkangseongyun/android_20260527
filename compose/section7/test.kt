package com.example.coroutinelab.section7

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//flow 데이터 발행자..
fun something(): Flow<Int> = flow<Int>{
    repeat(3){
        delay(100)
        emit(it)
    }
}

fun main() = runBlocking{
    val flow = something()
    val data1 = flow.first()
    println("data1 : $data1")

    val data2 = flow.first { //리턴 타입은 boolean
        println(it)
        it % 2 != 0
    }
    println("data2 : $data2")
    //data1 : 0
    //0
    //1
    //data2 : 1

    //flow 를 비동기로....
    launch {
        flow.collect {
           println("test1 : $it")
        }
    }
    //위 코드와 동일..
    flow.onEach { println("test2 : $it") }
        .launchIn(this)

    println()
}