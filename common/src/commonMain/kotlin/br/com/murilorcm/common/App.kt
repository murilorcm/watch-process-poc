package br.com.murilorcm.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun App(dir: File? = null) {
    var processMessage by remember { mutableStateOf(State.RUN.message) }

    var isProcessRunning by remember { mutableStateOf(false) }

    AnotherProcess.fillInitials(dir)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                if (isProcessRunning) {
                    AnotherProcess.onDestroy()
                    processMessage = State.RUN.message


                    isProcessRunning = false
                } else {
                    AnotherProcess.executeProcess()
                    processMessage = State.STOP.message + AnotherProcess.processStatus().first

                    isProcessRunning = true
                }

            }) {
                Text(
                    text = if (isProcessRunning) State.STOP.button else State.RUN.button
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(processMessage)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = if (isProcessRunning) "ta vivo" else "ta morto"
            )
        }
    }

    rememberCoroutineScope().launch {
        while (true) {
            val processStaus = AnotherProcess.processStatus()
            isProcessRunning = processStaus.second ?: false

            processMessage = if (isProcessRunning) {
                State.STOP.message + processStaus.first
            } else {
                State.RUN.message
            }

            delay(500)
        }
    }
}

enum class State(val button: String, val message: String) {
    RUN("Run", "waiting..."),
    STOP("Stop", "running in:")
}