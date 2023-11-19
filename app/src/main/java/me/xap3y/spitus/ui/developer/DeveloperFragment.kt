package me.xap3y.spitus.ui.developer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import me.xap3y.spitus.Utils.DataManager
import me.xap3y.spitus.databinding.FragmentDeveloperBinding
import me.xap3y.spitus.listeners.SaveListener

class DeveloperFragment : Fragment() {

    private var _binding: FragmentDeveloperBinding? = null

    private val binding get() = _binding!!
    private lateinit var dataManager: DataManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataManager = DataManager(requireContext())

        _binding = FragmentDeveloperBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val saveButton = binding.save
        saveButton.setOnClickListener(SaveListener(saveButton, dataManager, binding, requireContext()))

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}