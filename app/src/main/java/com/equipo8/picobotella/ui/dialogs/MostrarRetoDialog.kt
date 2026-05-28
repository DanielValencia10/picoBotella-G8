package com.equipo8.picobotella.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.equipo8.picobotella.databinding.DialogMostrarRetoBinding
import com.equipo8.picobotella.model.Reto

/**
 * Dialog para mostrar un reto aleatorio seleccionado.
 * Pertenece a la HU: Juego de la Botella.
 */
class MostrarRetoDialog(private val reto: Reto) : DialogFragment() {

    private var _binding: DialogMostrarRetoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogMostrarRetoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvRetoSeleccionado.text = reto.descripcion
        binding.btnCerrar.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
