package me.xap3y.spitus.Utils

import android.util.Log

class Logger {
    companion object{
        inline fun logger(level: Int, prefix: String, text: String, e: String? = null) {
            when (level) {
                Log.DEBUG -> Log.d(prefix, text)
                Log.INFO -> Log.i(prefix, text)
                Log.WARN -> Log.w(prefix, text)
                Log.VERBOSE -> Log.v(prefix, text)
                Log.ERROR ->  {
                    if (!e.isNullOrBlank()) {
                        Log.e(prefix, "$text Cause: $e")
                    } else {
                        Log.e(prefix, "$text Cause: UNKNOWN")
                    }

                }
            }
        }
        const val DEBUG = Log.DEBUG
        const val ERROR = Log.ERROR
        const val INFO = Log.INFO
        const val WARN = Log.WARN
        const val VERBOSE = Log.VERBOSE
    }
}