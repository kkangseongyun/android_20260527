package com.example.coroutinelab.section6

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun something(): Flow<Int> = flow {
    repeat(2){
        delay(100)
        println("emit $it")
        emit(it)//데이터 발행...
    }
}

fun main() = runBlocking{
    val myFlow = something()
    println("main step 1")
    //==>cold stream 이다.. flow builder 에 의해 flow 가 만들어졌다고 실행되지 않는다..
    //main step 1

    myFlow.collect {
        println("collect 1 : $it")
    }
    println("main step 2")
    //==>구독자가 생기면 그 순간.. flow 동작..
    //==>flow 자체가 코루틴은 아니다.. 동기 실행이다..
    //emit 0
    //collect 1 : 0
    //emit 1
    //collect 1 : 1
    //main step 2

    println()

    //flow 데이터를 비동기적으로 처리하고 싶다면 직접 코루틴 준비..
    launch {
        myFlow.collect {
            println("collect 2 : $it")
        }
    }

    println("main step 3")

    launch {
        myFlow.collect {
            println("collect 3 : $it")
        }
    }

    println("main step 4")
    //==> 코루틴에 의해 비동기로 flow 이용..
    //==> 여러 구독자가 나타났다.. flow 처음부터 다시 실행..
    //main step 3
    //main step 4
    //emit 0
    //collect 2 : 0
    //emit 0
    //collect 3 : 0
    //emit 1
    //collect 2 : 1
    //emit 1
    //collect 3 : 1

    delay(2000)
}