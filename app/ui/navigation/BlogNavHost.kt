package com.example.androidlab.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.androidlab.ui.blog.detail.BlogDetailScreen
import com.example.androidlab.ui.blog.edit.BlogEditScreen
import com.example.androidlab.ui.blog.list.BlogListScreen
import com.example.androidlab.ui.home.HomeScreen
import com.example.androidlab.ui.myinfo.MyInfoScreen

//상태를 유지해서.. 상태 값에 따라 출력되는 composable 을 교체하여.. 화면 전환 처리하는..
//composable
//==>NavHost 를 이용해서 화면 전환 처리할 수도..
@Composable
fun BlogNavHost(){
    //화면 전환 정보가 데이터로 유지되어야 한다.. ==>변수 선언
    //데이터가 변경되면 화면갱신(re-composition) 이 발생해야 한다. ==>상태 선언..
    //재구성시에.. 상태 값이 유지되어야 하는가? ==>remember
    //화면 회전시에 유지되어야 하는 데이터인가? ==>rememberSavable

    var currentScreen by rememberSaveable { mutableStateOf("home") }

    when(currentScreen){
        "home" -> HomeScreen(
            onNavigateToBlogList = { currentScreen = "blogList"},
            onNavigateToMyInfo = { currentScreen = "myInfo" }
        )
        "myInfo" -> MyInfoScreen(
            onBack = { currentScreen = "home"}
        )
        "blogList" -> BlogListScreen(
            onBack = { currentScreen = "home"},
            onNavigateToBlogEdit = { currentScreen = "blogEdit"},
            onNavigateToBlogDetail = { currentScreen = "blogDetail"}
        )
        "blogEdit" -> BlogEditScreen(
            onBack = { currentScreen = "blogList"}
        )
        "blogDetail" -> BlogDetailScreen(
            onBack = { currentScreen = "blogList"}
        )
    }
}