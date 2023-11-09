package me.xap3y.spitus.ui.servers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import me.xap3y.spitus.databinding.FragmentHomeBinding
import me.xap3y.spitus.listeners.FABonClick
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import me.xap3y.spitus.Utils.DataManager
import me.xap3y.spitus.Utils.DataManagerViewModelFactory
import me.xap3y.spitus.Utils.ServerJSON
import me.xap3y.spitus.Utils.StorageManager
import me.xap3y.spitus.ui.servers.utils.ServerRowAdapter
import me.xap3y.spitus.ui.settings.SettingsViewModel

class ServersFragment : Fragment(), ServerRowAdapter.TaskAdapterInterface {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var dataManager: DataManager
    private lateinit var taskAdapter: ServerRowAdapter
    private lateinit var serverList: MutableList<ServerJSON>
    private lateinit var root: View

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

        fab.setOnClickListener(FABonClick(fab))

        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

    }

    private fun getJsonServers() {
        serverList.clear()
        val parser = StorageManager.parseJsonString(dataManager.getString("json"))
        for (server in parser.servers) {
            Log.d("LOOP ServersFragment.kt:68", "Server name: ${server.name}, Address: ${server.address}, PORT: ${server.port}")
            serverList.add(ServerJSON(server.name, server.address, server.port, server.token))
        }
    }

    private fun init() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        serverList = mutableListOf()
        taskAdapter = ServerRowAdapter(serverList)
        taskAdapter.setListener(this)
        binding.recyclerView.adapter = taskAdapter
        getJsonServers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDeleteItemClicked(serverData: ServerJSON, position: Int) {
        Snackbar.make(root, "Delete item clicked", Snackbar.LENGTH_SHORT)
            .setAction("Action", null).show()
    }

    override fun onEditItemClicked(serverData: ServerJSON, position: Int) {
        Snackbar.make(root, "Edit item clicked", Snackbar.LENGTH_SHORT)
            .setAction("Action", null).show()
    }
}