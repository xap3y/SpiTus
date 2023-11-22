package me.xap3y.spitus.Utils

import com.google.gson.Gson
import me.xap3y.spitus.Utils.strucs.ServerJSON
import me.xap3y.spitus.Utils.strucs.ServersARR

class StorageManager {
    companion object{
        fun createDefaultJsonString(): String {
            val server = ServerJSON("Lobby", "89.103.79.152", 3002, "nutella", false)
            val server2 = ServerJSON("AuthMe", "142.251.36.142", 3000, "qwerty", false)
            val serverList = ServersARR(listOf(server, server2))

            val gson = Gson()
            return gson.toJson(serverList)
        }

        fun parseJsonString(jsonString: String): ServersARR {
            val gson = Gson()
            return gson.fromJson(jsonString, ServersARR::class.java)
        }

        fun removeFromJsonArr(jsonArr: ServersARR, filter: String): ServersARR {
            return ServersARR(jsonArr.servers.filter { it.name != filter })
        }
    }
}