package me.xap3y.spitus.Utils

import com.google.gson.Gson

class StorageManager {
    companion object{
        fun createJsonString(): String {
            val server = ServerJSON("Lobby", "192.168.1.102", 25570, "145060We#")
            val server2 = ServerJSON("AuthMe", "192.168.1.11", 25572, "145060We#")
            val serverList = ServersARR(listOf(server, server2))

            val gson = Gson()
            return gson.toJson(serverList)
        }

        fun parseJsonString(jsonString: String): ServersARR {
            val gson = Gson()
            return gson.fromJson(jsonString, ServersARR::class.java)
        }
    }
}