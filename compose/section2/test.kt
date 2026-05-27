package com.example.coroutinelab.section2

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext

//coroutine scope 선언...
class MyScope: CoroutineScope {

    //스코프의 job... 코루틴의 Job 은 스포프 job 의 subset 개념
    val scopeJob = Job()

    val exceptionHandler = CoroutineExceptionHandler { context, exception ->
        println("coroutine exception : ${context[CoroutineName.Key]} : $exception")
    }

    //코루틴 설정 정보...
    override val coroutineContext: CoroutineContext
        get() = CoroutineName("my-scope") + Dispatchers.Default + scopeJob + exceptionHandler
}

fun main() = runBlocking{
    //스코프 선언..
    val myScope = MyScope()
    //스코프 내에서 필요한 만큼 코루틴 만들어 실행..
    //리턴 되는 job 은 개별 코루틴 제어...
    val job1: Job = myScope.launch {
        repeat(3){
            delay(300)
            println("first coroutine $it")
        }
    }

    val job2: Job = myScope.launch {
        repeat(3){
            delay(200)
            println("second coroutine $it")
        }
    }

    delay(500)
    //개별 코루틴 제어..
//    job1.cancel()
    //second coroutine 0
    //first coroutine 0
    //second coroutine 1
    //second coroutine 2

    //scope 의 job 으로 scope 내의 모든 코루틴 제어..
//    myScope.scopeJob.cancel()
    //second coroutine 0
    //first coroutine 0
    //second coroutine

    delay(500)

    myScope.launch {
        delay(300)
        throw Exception("error.. coroutine...1")
    }

    delay(2000)

    println()
}