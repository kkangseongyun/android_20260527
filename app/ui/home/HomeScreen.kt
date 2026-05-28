package com.example.androidlab.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(
    onNavigateToBlogList: () -> Unit,
    onNavigateToMyInfo: () -> Unit
){
    //여러 화면 구성을 세로방향으로 나열..
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("HomeScreen")

        Button(
            onClick = onNavigateToBlogList
        ) {
            Text("Go BlogList")
        }
        Button(
            onClick = onNavigateToMyInfo
        ) {
            Text("Go MyInfo")
        }
    }
}