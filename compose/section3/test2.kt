package com.example.coroutinelab.section3

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

//launch 는 결과 반환 개념이 없다..
//코루틴에 의해 발생하는 데이터를 코루틴을 이용하는 곳에 전달해야 한다면..
//async - 한번 발생하는 데이터..
//Channel - 연속 발생 데이터
//Flow - 연속 발생 데이터..
fun someFun() = GlobalScope.async(Dispatchers.IO){
    println("coroutine.....")
    delay(300)
    "hello world"
}

fun main() = runBlocking{
    println("main start")
    val deferred: Deferred<String> = someFun()//Job 의 sub type
    val result = deferred.await()
    println("result : $result")
}
//main start
//coroutine.....
//result : hello world