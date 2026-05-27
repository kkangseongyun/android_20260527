package com.example.coroutinelab.section1

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


//suspend 함수는 suspend 함수내에서만 호출이 가능하다..
//개발자 함수도 suspend 예약어로 선언 가능..
//코루틴에 의해 실행될 함수를 만들때.. thread blocking 상황이 없다고 하더라도 suspend 권장..
suspend fun suspendFun(no: Int): Int {
    var sum = 0
    for(i in 1..10){
        delay(100L)
        sum += i
    }
//    normalFun(10)//ok...//suspend 에서 일반 함수 호출 가능..
    return sum
}

fun normalFun(no: Int): Int {
    var sum = 0
    for(i in 1..10){
//        delay(100L)
        Thread.sleep(100L)
        sum += i
    }
//    suspendFun(10)//error...
    return sum
}

//runBlocking : main 에서 suspend 함수(delay, launch 등) 을 사용할 목적으로만 이용..
fun main() = runBlocking{
    normalFun(10)//ok
    suspendFun(10)//error..
    println()
}