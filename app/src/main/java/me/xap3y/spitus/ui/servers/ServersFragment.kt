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
import me.xap3y.spitus.Utils.strucs.ServerJSON
import me.xap3y.spitus.Utils.strucs.ServersARR
import me.xap3y.spitus.Utils.StorageManager
import me.xap3y.spitus.Utils.StorageManager.Companion.removeFromJsonArr
import me.xap3y.spitus.ui.servers.utils.ServerRowAdapter

class ServersFragment : Fragment(), ServerRowAdapter.TaskAdapterInterface {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var dataManager: DataManager
    private lateinit var taskAdapter: ServerRowAdapter
    private lateinit var serverList: MutableList<ServerJSON>
    private lateinit var root: View
    private lateinit var gson: Gson
    private lateinit var jsonParser: JsonParser
    private lateinit var serverData: ServersARR
    private lateinit var serverData2: ServersARR
    private lateinit var navController: NavController

    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        dataManager = DataManager(requireContext())

        val factory = DataManagerViewModelFactory(dataManager)
        val serversViewModel =
            ViewModelProvider(this, factory)[ServersViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        root = binding.root

        val jsonString = dataManager.getString("json")
        Log.d("test224", jsonString)
        val fab = binding.fab

        //fab.setOnClickListener(FABonClick(fab))

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
            serverList.add(ServerJSON(server.name, server.address, server.port, server.token))
        }
    }

    private fun init() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        serverList = mutableListOf()
        for(server in serverList){
            Log.d("ServersFragment.kt:83", "Server name: ${server.name}")
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
        Snackbar.make(root, "Delete item clicked", Snackbar.LENGTH_SHORT)
            .setAction("Action", null).show()
        Log.d("TEST", serverData.name)
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
        Snackbar.make(root, "Edit item clicked", Snackbar.LENGTH_SHORT)
            .setAction("Action", null).show()
    }
}
