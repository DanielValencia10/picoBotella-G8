package com.equipo8.picobotella.ui.dialogs

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.equipo8.picobotella.R
import com.equipo8.picobotella.databinding.DialogAgregarRetoBinding

/**
 * Dialog para agregar un nuevo reto.
 * Cumple con los criterios de la HU Gestión de Retos.
 */
class AgregarRetoDialog(private val onGuardar: (String) -> Unit) : DialogFragment() {

    private var _binding: DialogAgregarRetoBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogAgregarRetoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Botón Cancelar cierra el diálogo
        binding.btnCancelar.setOnClickListener {
            dismiss()
        }

        // Habilitar/Deshabilitar botón Guardar según el texto
        binding.etReto.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val esValido = !s.isNullOrBlank()
                binding.btnGuardar.isEnabled = esValido
                
                if (esValido) {
                    binding.btnGuardar.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorNaranja))
                } else {
                    binding.btnGuardar.setTextColor(Color.GRAY)
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Botón Guardar guarda el reto y cierra el diálogo
        binding.btnGuardar.setOnClickListener {
            val texto = binding.etReto.text.toString().trim()
            if (texto.isNotEmpty()) {
                onGuardar(texto)
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
