package com.equipo8.picobotella.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.equipo8.picobotella.databinding.DialogEditarRetoBinding
import com.equipo8.picobotella.model.Reto

/**
 * Dialog para editar un reto existente.
 * Pertenece a la HU: Gestión de Retos.
 */
class EditarRetoDialog(
    private val reto: Reto,
    private val onActualizar: (Reto) -> Unit
) : DialogFragment() {

    private var _binding: DialogEditarRetoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogEditarRetoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etReto.setText(reto.descripcion)
        binding.btnActualizar.setOnClickListener {
            val nuevoTexto = binding.etReto.text.toString()
            if (nuevoTexto.isNotEmpty()) {
                onActualizar(reto.copy(descripcion = nuevoTexto))
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
