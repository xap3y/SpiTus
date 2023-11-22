package me.xap3y.spitus.ui.servers

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import me.xap3y.spitus.databinding.FragmentHomeBinding
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.JsonParser
import me.xap3y.spitus.Utils.DataManager
import me.xap3y.spitus.Utils.DataManagerViewModelFactory
import me.xap3y.spitus.Utils.Logger
import me.xap3y.spitus.Utils.SafeCallBack
import me.xap3y.spitus.Utils.strucs.ServerJSON
import me.xap3y.spitus.Utils.strucs.ServersARR
import me.xap3y.spitus.Utils.StorageManager
import me.xap3y.spitus.Utils.StorageManager.Companion.removeFromJsonArr
import me.xap3y.spitus.Utils.strucs.CallBackResult
import me.xap3y.spitus.Utils.strucs.WSClient
import me.xap3y.spitus.Utils.strucs.WSRes
import me.xap3y.spitus.ui.servers.utils.ServerRowAdapter
import java.net.URI

@Suppress("DEPRECATED_IDENTITY_EQUALS")
class ServersFragment : Fragment(), ServerRowAdapter.TaskAdapterInterface {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var dataManager: DataManager
    private lateinit var taskAdapter: ServerRowAdapter
    private lateinit var serverList: MutableList<ServerJSON>
    private lateinit var newServersList: MutableList<ServerJSON>
    private lateinit var root: View
    private lateinit var gson: Gson
    private lateinit var callRes: CallBackResult
    private lateinit var serverData: ServersARR
    private lateinit var serverData2: ServersARR

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        dataManager = DataManager(requireContext())

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        root = binding.root

        val fab = binding.fab

        fab.setOnClickListener {
            Logger.logger(Logger.DEBUG, "FAB", "Refresh button, TODO")

            var serverUri: URI
            var wsClient: WSClient
            var receivedMessage: WSRes
            var serverToUpdate: ServerJSON?

            for(server in serverList) {
                serverUri = URI("ws://${server.address}:${server.port}")
                wsClient = WSClient(serverUri)
                wsClient.connect()
                serverToUpdate = serverList.find{ it.name == server.name }
                if (serverToUpdate == null) continue
                Thread.sleep(300)
                callRes = SafeCallBack.Callback {
                    receivedMessage = gson.fromJson(wsClient.serverResponse.toString(), WSRes::class.java)
                    if (receivedMessage.online != null) serverToUpdate.status = receivedMessage.online!!
                    else serverToUpdate.status = false
                }
                if (callRes.success !== true){
                    serverToUpdate.status = false
                }
                wsClient.close()
            }
            taskAdapter.notifyDataSetChanged()
        }

        //fab.setOnClickListener {
        //    loadFragment(DeveloperFragment())
        //}

        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gson = Gson()
        init()
        getJsonServers()
    }

    private fun getJsonServers() {
        serverList.clear()
        serverData = StorageManager.parseJsonString(dataManager.getString("json"))
        serverData2 = serverData
        //serverList = serverData.servers.toMutableList()

        for(server in serverData.servers) {
            serverList.add(ServerJSON(server.name, server.address, server.port, server.token, server.status))
        }
    }

    private fun init() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        serverList = mutableListOf()
        newServersList = mutableListOf()
        for(server in serverList){
            Logger.logger(Logger.DEBUG, "ServerList loop", "Found server with name: ${server.name}")
        }
        taskAdapter = ServerRowAdapter(serverList)
        taskAdapter.setListener(this)
        binding.recyclerView.adapter = taskAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onDeleteItemClicked(serverData: ServerJSON, position: Int) {

        Snackbar.make(root, "Server deleted", Snackbar.LENGTH_SHORT)
            .setAction("Action", null).show()

        serverList.removeIf { it.name == serverData.name && it.address == serverData.address }

        val updatedServersJSON: String = gson.toJson(removeFromJsonArr(serverData2, serverData.name))
        dataManager.saveString("json", updatedServersJSON)

        // Re-declare variable serverData2 with updated list
        serverData2 = StorageManager.parseJsonString(dataManager.getString("json"))
        //Log.d("String after REM", updatedServersJSON.toString())
        //Log.d("String before REM", dataManager.getString("json"))

        taskAdapter.notifyDataSetChanged()
    }

    override fun onEditItemClicked(serverData: ServerJSON, position: Int) {
        Snackbar.make(root, "TODO", Snackbar.LENGTH_SHORT)
            .setAction("Action", null).show()
    }
}
