package com.equipo8.picobotella.view.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
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
        isCancelable = false
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

        binding.tvRetoSeleccionado.text = reto.descripcion
        cargarPokemonAleatorio()

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
                    // Reemplazamos 'http' por 'https' porque la API de Biuni usa http y Android lo bloquea
                    val imageUrl = pokemonAleatorio.img.replace("http://", "https://")
                    
                    withContext(Dispatchers.Main) {
                        Glide.with(requireContext())
                            .load(imageUrl)
                            .transition(DrawableTransitionOptions.withCrossFade()) // Animación suave al aparecer
                            .into(binding.ivPokemon)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.85).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
