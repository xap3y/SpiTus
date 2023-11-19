package me.xap3y.spitus.Utils
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.lang.Exception
import java.net.InetSocketAddress
import me.xap3y.spitus.Utils.Logger.Companion.DEBUG
import me.xap3y.spitus.Utils.Logger.Companion.ERROR
import me.xap3y.spitus.Utils.Logger.Companion.logger

class WSClient(address: InetSocketAddress) : WebSocketServer(address) {
    override fun onOpen(conn: WebSocket?, handshake: ClientHandshake?) {
        TODO("Not yet implemented")
    }

    override fun onClose(conn: WebSocket?, code: Int, reason: String?, remote: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onMessage(conn: WebSocket?, message: String?) {
        if (message != null) logger(DEBUG, "WSClient", message)
    }

    override fun onError(conn: WebSocket?, ex: Exception?) {
        TODO("Not yet implemented")
    }

    override fun onStart() {
        TODO("Not yet implemented")
    }

}