@file:Suppress("PackageName")

package me.xap3y.spitus.Utils.strucs

data class CallBackResult(
    val success: Boolean,
    val runTime: Long,
    val errorCode: Int?,
    val errorMessage: String?,
    val result: Any? = null
)
