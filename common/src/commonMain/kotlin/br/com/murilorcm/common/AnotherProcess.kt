package br.com.murilorcm.common

import br.com.murilorcm.common.Utils.Companion.getOsType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class AnotherProcess(env: File) {

    companion object {
        private var process: Process? = null
    }

    private val resourceName = when (getOsType()) {
        OSType.WINDOWS -> "criar-arquivo-0-10000.exe"
        OSType.ANDROID -> "criar-arquivo-0-10000.so"
    }

    private val command = ExtractResoruce().getExtractedResources(resourceName, env.absolutePath)
    private val outputFile = File(env, "log.txt")

    fun executeProcess() {
        if (command != null) {
            CoroutineScope(Dispatchers.IO).launch {
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
        return "$process" to process?.isAlive
    }

    fun onDestroy() {
        process?.destroy()
    }
}