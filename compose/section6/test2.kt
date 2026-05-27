package com.example.coroutinelab.section6

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//flow 를 이용하고 싶다..
//구독자가 없다고 하더라도 업무는 진행되고.. 데이터는 발생되게 하고 싶다..
//여러곳에서 데이터를 구독한다고 하더라도.. 처음부터 업무가 다시 실행될 필요는 없고.. 그 순간의 최신 데이터..
//==>hot stream....

val stateFlow = MutableStateFlow(0)

suspend fun changeState(data: Int){
    stateFlow.emit(data)
//    stateFlow.value = data//가능
}

fun main() = runBlocking{
    //구독자 없다.. 그래도 데이터 발행할 수 있다..
    changeState(1)
    changeState(2)
    val job1 = launch {
        stateFlow.collect {
            println("job1 : $it")
        }
    }
    delay(100)
    changeState(3)
    delay(100)
    val job2 = launch {
        stateFlow.collect {
            println("job2 : $it")
        }
    }
    delay(100)
    changeState(4)
    delay(100)
    job1.cancel()
    job2.cancel()
}
//job1 : 2
//job1 : 3
//job2 : 3
//job1 : 4
//job2 : 4