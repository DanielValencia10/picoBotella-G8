package com.equipo8.picobotella.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.equipo8.picobotella.databinding.DialogAgregarRetoBinding

/**
 * Dialog para agregar un nuevo reto.
 * Pertenece a la HU: Gestión de Retos.
 */
class AgregarRetoDialog(private val onGuardar: (String) -> Unit) : DialogFragment() {

    private var _binding: DialogAgregarRetoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogAgregarRetoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnGuardar.setOnClickListener {
            val texto = binding.etReto.text.toString()
            if (texto.isNotEmpty()) {
                onGuardar(texto)
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
