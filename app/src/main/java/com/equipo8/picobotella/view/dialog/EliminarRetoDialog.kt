package com.equipo8.picobotella.view.dialog

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogEliminarRetoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvDescripcionReto.text = reto.descripcion

        // Texto "SI" elimina el reto de la base de datos y cierra el diálogo
        binding.tvSi.setOnClickListener {
            onEliminar(reto)
            dismiss()
        }

        // Texto "NO" cierra el diálogo sin eliminar
        binding.tvNo.setOnClickListener {
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.9).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
