package com.example.coroutinelab.section1

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis

//runBlocking... main 함수, test code 에서 suspend 함수 호출목적으로만 사용할 것을 권장
fun main() = runBlocking {
    println("main step 1")

    //coroutine build, 구동...
    //launch : 가장 대표적인 coroutine builder
    val job = launch {
        var sum = 0
        for(i in 1..10){
            delay(100)
            sum += i
        }
        println("sum : $sum")
    }
    println("main step 2")
    job.join()//coroutine 종료까지 대기하라...
    //main step 1
    //main step 2
    //sum : 55

    //vs thread 성능 비교...
    val count = 10_000
    var time = measureTimeMillis {
        val threadJobs = List<Thread>(count){
            thread {
                Thread.sleep(1000)
                print(".")
            }
        }
        threadJobs.forEach { it.join() }
    }
    println()
    println("thread $count, total time : $time")

    time = measureTimeMillis {
        val coroutineJobs = List(count){
            launch {
//                Thread.sleep(1000)
                delay(1000)
                print(".")
            }
        }
        coroutineJobs.forEach { it.join() }
    }
    println()
    println("coroutine $count, total time : $time")
    //thread 10000, total time : 2080
    //coroutine 10000, total time : 1103

    //non - blocking...............
    //코드를 실행시키는 스레드를 blocking 하지 않는다.. 다른 업무를 처리할 수도 있다..
    listOf("one", "two", "three").forEachIndexed { index, value ->
        launch(Dispatchers.Default) { //코루틴을 실행시키는 스레드 지정...
            println("coutine... $value start : ${Thread.currentThread().name}")
//            Thread.sleep(100L + index * 100L)
            delay(100L + index * 100L)
            println("coutine... $value end : ${Thread.currentThread().name}")
        }
    }

    Thread.sleep(2000)

    //Thread.sleep(100L + index * 100L)........................
    //coroutine 을 이용했지만.. coroutine 을 실행시키는 thread 는 blocking 되었다..
    //coroutine 을 이용했다고 무조건 thread 가 non-blocking 되는 것이 아니라..
    //suspend 함수가 실행되면서.. 그 부분을 실행시키는 thread 가 non - blocking 될 가능성..
    //coutine... one start : DefaultDispatcher-worker-1
    //coutine... three start : DefaultDispatcher-worker-3
    //coutine... two start : DefaultDispatcher-worker-2
    //coutine... one end : DefaultDispatcher-worker-1
    //coutine... two end : DefaultDispatcher-worker-2
    //coutine... three end : DefaultDispatcher-worker-3

    //delay(100L + index * 100L)......................
    //시작은 3개의 thread 로...
    //끝날때는 1개의 thread 로...
    //==>thread 가 non - blocking...
    //coutine... two start : DefaultDispatcher-worker-2
    //coutine... one start : DefaultDispatcher-worker-1
    //coutine... three start : DefaultDispatcher-worker-3
    //coutine... one end : DefaultDispatcher-worker-1
    //coutine... two end : DefaultDispatcher-worker-1
    //coutine... three end : DefaultDispatcher-worker-1
}