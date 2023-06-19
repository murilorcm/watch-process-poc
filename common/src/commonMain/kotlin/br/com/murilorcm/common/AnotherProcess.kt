package br.com.murilorcm.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.util.*
import java.util.regex.Pattern

object AnotherProcess {
    private var dir: File? = null

    private val env by lazy {
        when {
            System.getProperty("os.name").lowercase(Locale.getDefault())
                .contains("win") -> File("C:\\Users\\muril\\git\\env\\windo\\")

            System.getProperty("java.runtime.name").lowercase(Locale.getDefault()).contains("android") -> {
                dir
            }

            else -> null
        }
    }

    private val command by lazy {
        when {
            System.getProperty("os.name").lowercase(Locale.getDefault()).contains("win") -> {
                "common/src/commonMain/resources/criar-arquivo-0-10000.exe"
            }

            System.getProperty("java.runtime.name").lowercase(Locale.getDefault()).contains("android") -> {
                "/criar-arquivo-0-10000"
            }

            else -> null
        }
    }

    private var process: Process? = null
    private val outputFile by lazy {
        File(env, "log.txt")
    }

    fun executeProcess() {
        if (command != null) {
            CoroutineScope(Dispatchers.IO).launch {
                try {

                    val a = ResourceList().getResources(Pattern.compile(".*criar.*"))

                    a.forEach {
                        println(it)
                    }

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
        return "$process" to process?.isAlive
    }

    fun onDestroy() {
        process?.destroy()
    }

    fun fillInitials(dir: File?) {
        this.dir = dir
    }
}