package com.equipo8.picobotella.view.dialog

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.equipo8.picobotella.R
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

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

        // Botón Cancelar cierra el diálogo sin guardar
        binding.btnCancelar.setOnClickListener {
            dismiss()
        }

        // Guardar solo se habilita si el texto cambió respecto al reto original
        binding.etReto.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val nuevoTexto = s?.toString().orEmpty().trim()
                val fueEditado = nuevoTexto.isNotEmpty() && nuevoTexto != reto.descripcion
                binding.btnGuardar.isEnabled = fueEditado
                binding.btnGuardar.backgroundTintList = ContextCompat.getColorStateList(
                    requireContext(),
                    if (fueEditado) R.color.colorNaranja else android.R.color.darker_gray
                )
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Botón Guardar actualiza el reto en la base de datos y cierra el diálogo
        binding.btnGuardar.setOnClickListener {
            val nuevoTexto = binding.etReto.text.toString().trim()
            if (nuevoTexto.isNotEmpty()) {
                onActualizar(reto.copy(descripcion = nuevoTexto))
                dismiss()
            }
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