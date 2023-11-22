@file:Suppress("PackageName")

package me.xap3y.spitus.Utils.strucs

data class ServerJSON(
    val name: String,
    val address: String,
    val port: Int,
    val token: String,
    var status: Boolean,
)
