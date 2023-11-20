package me.xap3y.spitus.ui.servers.utils

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.xap3y.spitus.Utils.strucs.ServerJSON
import me.xap3y.spitus.databinding.EachServerItemBinding

class ServerRowAdapter(private val list:MutableList<ServerJSON>) :
RecyclerView.Adapter<ServerRowAdapter.ServerViewHolder>(){

    private var listener:TaskAdapterInterface? = null
    fun setListener(listener:TaskAdapterInterface) {
        this.listener = listener
    }
    inner class ServerViewHolder(val binding:EachServerItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServerViewHolder {
        val binding = EachServerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ServerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ServerViewHolder, position: Int) {
        with(holder) {
            with(list[position]){
                binding.serverName.text = this.name
                binding.ipPort.text = "${this.address}:${this.port}"
                //binding.status.baseline = R.color.purple_200

                // Set status dot color:
                //val imageView: ImageView = binding.status
                //val vectorDrawable: Drawable? = ContextCompat.getDrawable()

                binding.editTask.setOnClickListener {
                    listener?.onEditItemClicked(this , position)
                }

                binding.deleteTask.setOnClickListener {
                    listener?.onDeleteItemClicked(this , position)
                }
            }

        }
    }
    interface TaskAdapterInterface{
        fun onDeleteItemClicked(serverData: ServerJSON, position : Int)
        fun onEditItemClicked(serverData: ServerJSON, position: Int)
    }
}