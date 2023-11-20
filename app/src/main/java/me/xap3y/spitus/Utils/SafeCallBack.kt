@file:Suppress("PackageName")

package me.xap3y.spitus.Utils

import me.xap3y.spitus.Utils.strucs.CallBackResult
import kotlin.system.measureTimeMillis

class SafeCallBack {
    companion object{
        fun <T> Callback(callback: () -> T): CallBackResult {
            return try {
                var result: Any? = null;
                val executionTime = measureTimeMillis {
                    result = callback.invoke()
                }
                return CallBackResult(true, executionTime, null, null, result)
            } catch (e: Exception) {
                return CallBackResult(false, 0L, null, e.message)
            }
        }
    }
}