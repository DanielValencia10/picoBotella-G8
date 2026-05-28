package com.equipo8.picobotella.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.equipo8.picobotella.databinding.DialogEliminarRetoBinding
import com.equipo8.picobotella.model.Reto

/**
 * Dialog para confirmar la eliminación de un reto.
 * Pertenece a la HU: Gestión de Retos.
 */
class EliminarRetoDialog(
    private val reto: Reto,
    private val onEliminar: (Reto) -> Unit
) : DialogFragment() {

    private var _binding: DialogEliminarRetoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogEliminarRetoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnEliminar.setOnClickListener {
            onEliminar(reto)
            dismiss()
        }
        binding.btnCancelar.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
