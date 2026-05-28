package com.example.androidlab.ui.blog.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun BlogListScreen(
    onBack: () -> Unit,
    onNavigateToBlogDetail: () -> Unit,
    onNavigateToBlogEdit: () -> Unit
){
    //여러 화면 구성을 세로방향으로 나열..
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("BlogListScreen")
        Button(
            onClick = onBack
        ) {
            Text("Go Back")
        }
        Button(
            onClick = onNavigateToBlogDetail
        ) {
            Text("Go Detail")
        }
        Button(
            onClick = onNavigateToBlogEdit
        ) {
            Text("Go Edit")
        }
    }
}