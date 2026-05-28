package com.equipo8.picobotella.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.equipo8.picobotella.databinding.FragmentHomeBinding

/**
 * Fragmento Principal del Juego.
 * Pertenece a la HU: Juego de la Botella.
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Configuración inicial del juego
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
