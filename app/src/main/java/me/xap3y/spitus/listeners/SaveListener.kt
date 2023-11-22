package me.xap3y.spitus.listeners

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Button
import com.google.gson.Gson
import me.xap3y.spitus.Utils.DataManager
import me.xap3y.spitus.Utils.Keyboard
import me.xap3y.spitus.Utils.strucs.ServerJSON
import me.xap3y.spitus.Utils.strucs.ServersARR
import me.xap3y.spitus.Utils.MaterialD
import me.xap3y.spitus.Utils.StorageManager
import me.xap3y.spitus.databinding.FragmentDeveloperBinding
import kotlin.Exception


class SaveListener(private val button: Button, private val dataManager: DataManager, private val binding: FragmentDeveloperBinding, context2: Context) : View.OnClickListener {
    private lateinit var serverList: MutableList<ServerJSON>
    private lateinit var name: String
    private var port: Int = 0;
    private lateinit var address: String
    private lateinit var token: String
    private lateinit var gson: Gson
    private val context = context2
    override fun onClick(view: View?) {
        if (view != null) {

            name = binding.name.text.toString()
            address = binding.address.text.toString()
            port = try {
                binding.port.text.toString().toInt()
            } catch (e: Exception) {
                -1
            }

            Keyboard.hide(context, view)

            if (name.isNullOrBlank()) {
                MaterialD.snackBar(context, "Name cannot be empty!", view)
            } else if (address.isNullOrBlank()) {
                MaterialD.snackBar(context, "Address cannot be empty!", view)
            } else if (port == -1) {
                MaterialD.snackBar(context, "Port number cannot be empty!", view)
            } else if (port<1 || port>65535) {
                MaterialD.snackBar(context, "Port number cannot be below 1 and higher then 65535!", view)
            } else {
                try {
                    gson = Gson()
                    val data2 = StorageManager.parseJsonString(dataManager.getString("json"))
                    serverList = mutableListOf()
                    for(server in data2.servers) {
                        if (server.name == binding.name.text.toString()) {
                            MaterialD.snackBar(context, "This name already exits!", view)
                            return
                        }
                        serverList.add(ServerJSON(server.name, server.address, server.port, server.token, server.status))
                    }
                } catch (e: Exception) {
                    MaterialD.snackBar(context,"Error while creating server", view)
                }
                try {
                    serverList.add(ServerJSON(name, address, port, "test", false))
                    val data3 = ServersARR(serverList)
                    val updatedServersJSON: String = gson.toJson(data3)
                    dataManager.saveString("json", updatedServersJSON)
                    ///MaterialD.snackBar(context, "Server saved", view)
                    MaterialD.dialog(context, "New server", "Server was created successfully.", null, null, true)
                } catch (e: Exception) {
                    //MaterialD.snackBar(context, "Cannot create server!", view)
                    MaterialD.dialog(context, "New server", "dataManager: Cannot create server! LOG: ${e.message}", null, null, true)
                }
            }
        } else {
            Log.d("FABonClick", "ERROR! `view` is null! (FABonClick:10,15)")
        }
    }
}