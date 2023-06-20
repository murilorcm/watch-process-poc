package br.com.murilorcm.common

import java.io.File
import java.io.IOException

class ExtractResoruce {

    fun getExtractedResources(
        resourceName: String,
        outputPath: String
    ): String? {
        try {

            File(outputPath).mkdirs()
            val outputFile = File("$outputPath/$resourceName").also {
                if (it.exists()) it.delete()
            }

            val stream = javaClass.getResourceAsStream("/$resourceName")
            stream.use { inputStream ->
                outputFile.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }

            return if (outputFile.exists()) outputFile.absolutePath else null
        } catch (e: IOException) {
            throw Error(e)
        }
    }
}