package br.com.murilorcm.common

import java.io.File
import java.io.IOException
import java.util.regex.Pattern
import java.util.zip.ZipEntry
import java.util.zip.ZipException
import java.util.zip.ZipFile

class ResourceList {

    fun getResources(pattern: Pattern): Collection<String> {
        val retval = mutableListOf<String>()
        val classPath = System.getProperty("java.class.path", ".")
        val classPathElements = classPath.split(File.pathSeparator)

        for (element in classPathElements) {
            retval.addAll(getResources(element, pattern));
        }
        return retval;
    }

    private fun getResources(element: String, pattern: Pattern): Collection<String> {
        val retval = mutableListOf<String>()
        val file = File(element)

        if (!file.isDirectory) {
            retval.addAll(getResourcesFromJarFile(file, pattern))
        } else {
            retval.addAll(getResourcesFromDirFile(file, pattern))
        }

        return retval
    }

    private fun getResourcesFromDirFile(file: File, pattern: Pattern): Collection<String> {
        TODO("Not yet implemented")
    }

    private fun getResourcesFromJarFile(file: File, pattern: Pattern): Collection<String> {
        val retval = mutableListOf<String>()
        var zf: ZipFile? = null

        try {
            zf = ZipFile(file)
            val e = zf.entries()
            while (e.hasMoreElements()) {
                val ze: ZipEntry = e.nextElement() as ZipEntry
                val fileName: String = ze.name
                val accept = pattern.matcher(fileName).matches()
                if (accept) {
                    retval.add(fileName)
                }
            }
        } catch (e: ZipException) {
            throw Error(e)
        } catch (e: IOException) {
            throw Error(e)
        } finally {
            zf?.close()
        }
        return retval
    }
}