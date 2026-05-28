package com.equipo8.picobotella.ui.retos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.equipo8.picobotella.databinding.FragmentRetosBinding

/**
 * Fragmento para la gestión de retos.
 * Pertenece a la HU: Gestión de Retos.
 */
class RetosFragment : Fragment() {

    private var _binding: FragmentRetosBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRetosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Configurar RecyclerView y ViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
