package com.equipo8.picobotella.view.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.equipo8.picobotella.R
import com.equipo8.picobotella.data.remote.RetrofitClient
import com.equipo8.picobotella.databinding.DialogMostrarRetoBinding
import com.equipo8.picobotella.model.Reto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Dialog para mostrar un reto aleatorio con imagen de Pokémon.
 * HU 12: Mostrar reto aleatorio.
 */
class MostrarRetoDialog(
    private val reto: Reto,
    private val onDialogDismiss: () -> Unit = {}
) : DialogFragment() {

    private var _binding: DialogMostrarRetoBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false  // C6: No se cierra al hacer click fuera
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogMostrarRetoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // C3: Mostrar descripción del reto
        binding.tvRetoSeleccionado.text = reto.descripcion

        // C2: Cargar Pokémon aleatorio desde la API
        cargarPokemonAleatorio()

        // C5: Botón Cerrar -> cierra el diálogo y ejecuta callback
        binding.btnCerrar.setOnClickListener {
            onDialogDismiss()
            dismiss()
        }
    }

    private fun cargarPokemonAleatorio() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.pokemonApi.getPokemons()
                val pokemons = response.pokemon
                if (pokemons.isNotEmpty()) {
                    val pokemonAleatorio = pokemons.random()
                    withContext(Dispatchers.Main) {
                        Glide.with(requireContext())
                            .load(pokemonAleatorio.img)
                            .placeholder(R.drawable.ic_botella_background)
                            .error(R.drawable.ic_botella_background)
                            .into(binding.ivPokemon)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        "Error al cargar Pokémon: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.9).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        // Fondo transparente para que se vea el fondo negro degradado del diálogo
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        // No permitir cerrar al hacer click fuera
        dialog?.setCanceledOnTouchOutside(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}