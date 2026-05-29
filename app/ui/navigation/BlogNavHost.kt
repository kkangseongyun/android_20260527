package com.example.androidlab.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.androidlab.ui.blog.detail.BlogDetailScreen
import com.example.androidlab.ui.blog.edit.BlogEditScreen
import com.example.androidlab.ui.blog.list.BlogListScreen
import com.example.androidlab.ui.home.HomeScreen
import com.example.androidlab.ui.myinfo.MyInfoScreen

////상태를 유지해서.. 상태 값에 따라 출력되는 composable 을 교체하여.. 화면 전환 처리하는..
////composable
////==>NavHost 를 이용해서 화면 전환 처리할 수도..
//@Composable
//fun BlogNavHost(){
//    //화면 전환 정보가 데이터로 유지되어야 한다.. ==>변수 선언
//    //데이터가 변경되면 화면갱신(re-composition) 이 발생해야 한다. ==>상태 선언..
//    //재구성시에.. 상태 값이 유지되어야 하는가? ==>remember
//    //화면 회전시에 유지되어야 하는 데이터인가? ==>rememberSavable
//
//    var currentScreen by rememberSaveable { mutableStateOf("home") }
//
//    when(currentScreen){
//        "home" -> HomeScreen(
//            onNavigateToBlogList = { currentScreen = "blogList"},
//            onNavigateToMyInfo = { currentScreen = "myInfo" }
//        )
//        "myInfo" -> MyInfoScreen(
//            onBack = { currentScreen = "home"}
//        )
//        "blogList" -> BlogListScreen(
//            onBack = { currentScreen = "home"},
//            onNavigateToBlogEdit = { currentScreen = "blogEdit"},
//            onNavigateToBlogDetail = { currentScreen = "blogDetail"}
//        )
//        "blogEdit" -> BlogEditScreen(
//            onBack = { currentScreen = "blogList"}
//        )
//        "blogDetail" -> BlogDetailScreen(
//            onBack = { currentScreen = "blogList"}
//        )
//    }
//}

//화면 이름 지정..
//enum class 로 해도 되고.. 화면 이름에 약간의 유틸리티 함수 추가하려고..
sealed class Screen(val route: String){
    object Home: Screen("home")
    object MyInfo: Screen("my_info")
    object BlogList: Screen("blog_list")
    //화면 전환시에 데이터 전달..
    object BlogEdit: Screen("blog_edit?postId={postId}"){
        //개발자 임의 함수.. 화면 전환 요청시에.. 화면의 이름을 만드는 것을 편하게 만들게 하기 위해서..
        fun createRoute(postId: Long? = null) =
            if(postId != null) "blog_edit?postId=$postId" else "blog_edit"
    }
    object BlogDetail: Screen("blog_detail/{postId}"){
        fun createRoute(postId: Long) = "blog_detail/$postId"
    }
}

@Composable
fun BlogNavHost(navController: NavHostController = rememberNavController()){
    //conposable 이다..
    //단지 NavHostController 에 의해 화면 전환 정보를 stack 구조로 유지하는 역할을 한다..
    NavHost(
        navController = navController,//이곳에 명시한 controller 의 화면 전환 명령을 자동 감지해서 stack 관리..
        startDestination = Screen.Home.route
    ){
        //화면 등록..
        composable(Screen.Home.route){
            HomeScreen(
                onNavigateToMyInfo = { navController.navigate(Screen.MyInfo.route)},
                onNavigateToBlogList = { navController.navigate(Screen.BlogList.route)}
            )
        }
        composable(Screen.MyInfo.route){
            MyInfoScreen(
                onBack = { navController.popBackStack()}
            )
        }
        composable(Screen.BlogList.route){
            BlogListScreen(
                onNavigateToBlogDetail = { postId ->
                    navController.navigate(Screen.BlogDetail.createRoute(postId))
                },
                onNavigateToBlogEdit = {
                    navController.navigate(Screen.BlogEdit.createRoute())
                },
                onBack = { navController.popBackStack()}
            )
        }

        composable(
            route = Screen.BlogEdit.route,
            //화면 전환 요청시에 전달할 데이터를 받아야 해서..
            arguments = listOf(
                navArgument("postId"){
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ){ backStackEntry ->
            //실제 화면 전환 요청시에 전달된 데이터를 추출..
            val postId = backStackEntry.arguments?.getLong("postId") ?: -1L
            BlogEditScreen(
                postId = if(postId == -1L) null else postId,
                onBack = { navController.popBackStack()},
                onSaved = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.BlogDetail.route,
            //화면 전환 요청시에 전달할 데이터를 받아야 해서..
            arguments = listOf(
                navArgument("postId"){
                    type = NavType.LongType
                }
            )
        ){ backStackEntry ->
            //실제 화면 전환 요청시에 전달된 데이터를 추출..
            val postId = backStackEntry.arguments?.getLong("postId") ?: return@composable
            BlogDetailScreen(
                postId = postId,
                onBack = { navController.popBackStack()},
                onDeleted = { navController.popBackStack() },
                onEdit = { navController.navigate(Screen.BlogEdit.createRoute(postId))}
            )
        }
    }
}














