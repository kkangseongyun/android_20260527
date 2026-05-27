package com.example.coroutinelab.section4

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope

val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
    println("exception : ${throwable.message}")
}

//scoping function : coroutine 내부에서 blocking 되는 영역을 묶기 위해서 사용..
//coroutineScope, supervisorScope 모두.. 동시 진행되어야 하는 업무를 묶기 위해서.. 단지.. 부모 코루틴을 blocking
suspend fun something1() = coroutineScope {
    println("thread name : ${Thread.currentThread().name}, ${coroutineContext[CoroutineName]}")
    launch {
        repeat(3){
            delay(300)
            println("coroutine 1 - $it")
        }
    }
    launch {
        repeat(3){
            delay(200)
            println("coroutine 2 - $it")
            if(it == 1) throw Exception("coroutine 1 exception")
        }
    }
}

suspend fun something2() = supervisorScope {
    println("thread2 name : ${Thread.currentThread().name}, ${coroutineContext[CoroutineName]}")
    launch {
        repeat(3){
            delay(300)
            println("coroutine 2-1 - $it")
        }
    }
    launch {
        repeat(3){
            delay(200)
            println("coroutine 2 - 2 - $it")
            if(it == 1) throw Exception("coroutine 2 exception")
        }
    }
}

fun main() = runBlocking{
    val scope1 = CoroutineScope(newSingleThreadContext("myThread1")
            + CoroutineName("myCoroutine1") + exceptionHandler)
    scope1.launch {

        something1()

    }.join()

    println()

    val scope2 = CoroutineScope(newSingleThreadContext("myThread2")
            + CoroutineName("myCoroutine2") + exceptionHandler)
    scope2.launch {

        something2()

    }.join()
}
//coroutineScope 영역내의 하나의 코루틴이 exception 발생하면 전체 취소..
//coroutine 2 - 0
//coroutine 1 - 0
//coroutine 2 - 1
//exception : coroutine 1 exception
//

////supervisorScope 영역내의 하나의 코루틴이 exception 발생, 나머지는 젇ㅇ상 실행..
//thread2 name : myThread2, CoroutineName(myCoroutine2)
//coroutine 2 - 2 - 0
//coroutine 2-1 - 0
//coroutine 2 - 2 - 1
//exception : coroutine 2 exception
//coroutine 2-1 - 1
//coroutine 2-1 - 2