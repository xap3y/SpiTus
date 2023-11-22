@file:Suppress("PackageName")

package me.xap3y.spitus.Utils.strucs
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.lang.Exception
import java.net.InetSocketAddress
import me.xap3y.spitus.Utils.Logger.Companion.DEBUG
import me.xap3y.spitus.Utils.Logger.Companion.logger
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI

class WSClient(address: URI) : WebSocketClient(address) {
    var serverResponse: String? = null
    override fun onOpen(handshakedata: ServerHandshake?) {
        logger(DEBUG, "WSClient", "Opened")
    }

    override fun onMessage(message: String?) {
        serverResponse = message
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        logger(DEBUG, "WSClient", "Closed: $reason")
    }

    override fun onError(ex: Exception?) {
        logger(DEBUG, "WSClient", "Error: ${ex?.message}")
    }

}