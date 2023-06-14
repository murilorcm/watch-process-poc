package br.com.murilorcm.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.util.*

object AnotherProcess {
    private val env = when {
        System.getProperty("os.name").lowercase(Locale.getDefault())
            .contains("win") -> File("C:\\Users\\muril\\git\\env\\windo\\")

        System.getProperty("java.runtime.name").lowercase(Locale.getDefault()).contains("android") -> File("")
        else -> null
    }

    private val command = when {
        System.getProperty("os.name").lowercase(Locale.getDefault()).contains("win") -> {
            "${env?.absolutePath}\\criar-arquivo-0-10000.exe"
        }

        System.getProperty("java.runtime.name").lowercase(Locale.getDefault()).contains("android") -> {
            ""
        }

        else -> null
    }

    private var process: Process? = null
    private val outputFile = File(env, "log.txt")

    fun executeProcess() {
        if (command != null) {
            CoroutineScope(Dispatchers.Default).launch {
                try {
                    process = ProcessBuilder().run {
                        redirectOutput(ProcessBuilder.Redirect.appendTo(outputFile))
                        redirectError(ProcessBuilder.Redirect.appendTo(outputFile))
                        command(command)
                        start()
                    }

                } catch (e: Exception) {
                    println(e.message)
                }
            }
        }
    }

    fun processStatus(): Pair<String?, Boolean?> {
        return "${process?.pid()}" to process?.isAlive
    }

    fun onDestroy() {
        process?.destroy()
    }
}