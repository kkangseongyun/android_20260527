package com.example.androidlab.ui.myinfo

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyInfoScreen(
    onBack: () -> Unit
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    //...................
    //유저 입력 데이터. 상태 선언..
    var email by rememberSaveable { mutableStateOf("") }

    //입력 순간 이벤트 콜백..
    fun onEditEmailChange(value: String){
        email = value
    }
    //save 이벤트 콜백..
    fun saveEmail(){
        scope.launch {
            snackbarHostState.showSnackbar("이메일이 저장되었습니다.")
        }
    }

    Scaffold(
        //...................
        topBar = {
            TopAppBar(
                title = { Text("내 정보")},
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "뒤로가기")
                    }
                },
                actions = {
                    IconButton({
                        saveEmail()
                    }) {
                        Icon(Icons.Default.Save, contentDescription = "저장")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "프로필 설정",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(24.dp))

            //...................
            OutlinedTextField(
                value = email,//화면에 출력되는 입력 문자열.. 꼭 상태로 선언되어야 한다..
                onValueChange = ::onEditEmailChange,
                label = { Text("이메일")},
                modifier = Modifier.fillMaxWidth()
            )


            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "현재 저장된 이메일: ${if (email.isBlank()) "없음" else email}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    saveEmail()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Save, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("저장")
            }
        }
    }
}
