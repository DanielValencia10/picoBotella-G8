package com.equipo8.picobotella.ui.retos

import android.view.LayoutInflater
import android.view.View
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

        // Animación de touch antes de ejecutar la acción
        holder.binding.btnEditar.setOnClickListener { view ->
            animarToque(view) { onEditClick(reto) }
        }
        holder.binding.btnEliminar.setOnClickListener { view ->
            animarToque(view) { onDeleteClick(reto) }
        }
    }

    override fun getItemCount(): Int = retos.size

    fun updateData(newRetos: List<Reto>) {
        retos = newRetos
        notifyDataSetChanged()
    }

    private fun animarToque(view: View, accion: () -> Unit) {
        view.animate()
            .scaleX(0.8f).scaleY(0.8f).setDuration(100)
            .withEndAction {
                view.animate()
                    .scaleX(1f).scaleY(1f).setDuration(100)
                    .withEndAction(accion).start()
            }.start()
    }
}
