package com.equipo8.picobotella.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.equipo8.picobotella.databinding.FragmentCompartirBinding

/**
 * Fragmento para compartir la aplicación.
 * Pertenece a la HU: Compartir App.
 */
class CompartirFragment : Fragment() {

    private var _binding: FragmentCompartirBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompartirBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Lógica para compartir (Intent.ACTION_SEND)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}