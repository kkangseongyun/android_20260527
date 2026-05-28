package com.example.coroutinelab.section8

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.runBlocking

fun main() = runBlocking{
    val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    list.asFlow()
        .filter {
            it % 2 == 0
        }
        .map {
            it * it
        }
        .take(3)
        .collect {
            println(it)
        }
    //4
    //16
    //36

    list.asFlow()
        .transform {
            if(it % 2 == 0){
                emit("even number : $it")
            }else {
                emit("odd number : $it")
            }
        }
        .collect { println(it) }
}