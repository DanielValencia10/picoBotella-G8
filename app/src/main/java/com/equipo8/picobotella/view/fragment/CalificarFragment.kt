package com.equipo8.picobotella.view.fragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.equipo8.picobotella.R
import com.equipo8.picobotella.databinding.FragmentCalificarBinding

/**
 * Fragmento para calificar la aplicación.
 * HU 4.0: Calificar la aplicación
 * Criterios:
 * C1 - Fondo gris oscuro y toolbar con título y flecha atrás (definido en XML)
 * C2 - Selector de estrellas (1 a 5) para calificar
 * C3 - Botón para enviar la calificación
 * C4 - Como la app no está publicada en Google Play, al enviar se simula el flujo
 *      redirigiendo a la ficha de Nequi en la Play Store
 */
class CalificarFragment : Fragment() {

    private var _binding: FragmentCalificarBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalificarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configurarToolbar()
        configurarEnvioCalificacion()
    }

    // C1: toolbar con flecha atrás
    private fun configurarToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    // C3 y C4: valida la selección de estrellas y redirige a la Play Store simulada
    private fun configurarEnvioCalificacion() {
        binding.btnEnviarCalificacion.setOnClickListener {
            val calificacion = binding.ratingBar.rating

            if (calificacion <= 0f) {
                Toast.makeText(
                    requireContext(),
                    R.string.calificar_seleccion_vacia,
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            Toast.makeText(
                requireContext(),
                R.string.calificar_agradecimiento,
                Toast.LENGTH_SHORT
            ).show()

            abrirPlayStoreSimulada()
        }
    }

    private fun abrirPlayStoreSimulada() {
        val url = getString(R.string.url_play_store_simulada)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), url, Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}