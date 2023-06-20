package br.com.murilorcm.common

import java.util.*

class Utils {

    companion object {
        fun getOsType(): OSType {
            return when {
                System.getProperty("os.name").lowercase(Locale.getDefault())
                    .contains(OSType.WINDOWS.name.lowercase()) -> OSType.WINDOWS

                System.getProperty("java.runtime.name").lowercase(Locale.getDefault())
                    .contains(OSType.ANDROID.name.lowercase()) -> OSType.ANDROID

                else -> error("unknow os system")
            }
        }
    }
}