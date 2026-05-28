package com.equipo8.picobotella.ui.retos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.equipo8.picobotella.databinding.ItemRetoBinding
import com.equipo8.picobotella.model.Reto

/**
 * Adaptador para el RecyclerView de retos.
 * Pertenece a la HU: Gestión de Retos.
 */
class RetosAdapter(
    private var retos: List<Reto>,
    private val onEditClick: (Reto) -> Unit,
    private val onDeleteClick: (Reto) -> Unit
) : RecyclerView.Adapter<RetosAdapter.RetoViewHolder>() {

    class RetoViewHolder(val binding: ItemRetoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RetoViewHolder {
        val binding = ItemRetoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RetoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RetoViewHolder, position: Int) {
        val reto = retos[position]
        holder.binding.tvDescripcion.text = reto.descripcion
        holder.binding.btnEditar.setOnClickListener { onEditClick(reto) }
        holder.binding.btnEliminar.setOnClickListener { onDeleteClick(reto) }
    }

    override fun getItemCount(): Int = retos.size

    fun updateData(newRetos: List<Reto>) {
        retos = newRetos
        notifyDataSetChanged()
    }
}
