package com.example.coroutinelab.section3

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main() = runBlocking{
    println("main start...")

    //정상적으로 cancel 되는 코루틴...
    //delay() 같은 coroutine api 에서 제공되는 함수가 내부적으로 cancel 된 것인지를 판단해서 exception 발생시키기
    //때문에 캔슬된다..
//    val job = launch(Dispatchers.IO) {
//        var i = 0
//        while (i < 5){
//            i++
//            delay(500)
//            println("coroutine : $i")
//        }
//    }
    //main start...
    //coroutine : 1
    //main : coroutine cancel...
    //main end....


    //외부에서 cancel 명령이 내려졌지만.. 캔슬 되지 않고 정상적으로 실행된다..
    //정상 실행.....
//    val job = launch(Dispatchers.IO) {
//        var start = System.currentTimeMillis()
//        var i = 0
//        while (i < 5){
//            if(System.currentTimeMillis() >= start) {
//                i++
//                println("coroutine : $i")
//                start += 500
//            }
//        }
//    }
    //main start...
    //coroutine : 1
    //coroutine : 2
    //coroutine : 3
    //main : coroutine cancel...
    //coroutine : 4
    //coroutine : 5
    //main end....

    //코루틴 api 를 사용하지 않았을 때, 외부 요청에 의해 취소되는 코루틴을 만들려면...
    //isActive 값을 확인해서.. 캔슬 요청을 받은것인지 확인한 후 직접 exception 발생시켜야..
//    val job = launch(Dispatchers.IO) {
//        var start = System.currentTimeMillis()
//        var i = 0
//        while (i < 5){
//            if(isActive) {
//                if (System.currentTimeMillis() >= start) {
//                    i++
//                    println("coroutine : $i")
//                    start += 500
//                }
//            }else {
//                throw CancellationException()
//            }
//        }
//    }
    //main start...
    //coroutine : 1
    //coroutine : 2
    //coroutine : 3
    //main : coroutine cancel...
    //main end....


    //cancel 에 의해 exception 발생시켜서 종료 시키고 싶기는 한데..
    //마지막으로 처리해야 하는 코드가 있다..
//    val job = launch(Dispatchers.IO) {
//        try {
//            var start = System.currentTimeMillis()
//            var i = 0
//            while (i < 5) {
//                if (isActive) {
//                    if (System.currentTimeMillis() >= start) {
//                        i++
//                        println("coroutine : $i")
//                        start += 500
//                    }
//                } else {
//                    throw CancellationException()
//                }
//            }
//        }finally {
//            repeat(3){
//                //exception 이 발생하는 코드가 작성되어 있다면..
//                //finally 가 정상실행되지 않을 수 있다..
//                println("coroutine finally... $it")
//                delay(100)
//            }
//        }
//    }
    //main start...
    //coroutine : 1
    //coroutine : 2
    //coroutine : 3
    //main : coroutine cancel...
    //coroutine finally... 0
    //main end....

    //canel 에 의해 exception 이 발생하는 코드가 있다고 하더라도.. 정상적으로 실행되게 하고 싶다..
    val job = launch(Dispatchers.IO) {
        try {
            var start = System.currentTimeMillis()
            var i = 0
            while (i < 5) {
                if (isActive) {
                    if (System.currentTimeMillis() >= start) {
                        i++
                        println("coroutine : $i")
                        start += 500
                    }
                } else {
                    throw CancellationException()
                }
            }
        }finally {
            //withContext : 코루틴에 의해 실행되는 특정 영역의 실행 정보를 교체하려고..
            withContext(NonCancellable) {
                repeat(3) {
                    //exception 이 발생하는 코드가 작성되어 있다면..
                    //finally 가 정상실행되지 않을 수 있다..
                    println("coroutine finally... $it")
                    delay(100)
                }
            }
        }
    }
    //main start...
    //coroutine : 1
    //coroutine : 2
    //coroutine : 3
    //main : coroutine cancel...
    //coroutine finally... 0
    //coroutine finally... 1
    //coroutine finally... 2
    //main end....


    delay(1000)
    println("main : coroutine cancel...")
    job.cancelAndJoin()
    println("main end....")
}