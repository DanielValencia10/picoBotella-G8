package com.equipo8.picobotella.ui.toolbar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.equipo8.picobotella.databinding.FragmentCustomToolbarBinding

/**
 * Fragmento para la Toolbar personalizada.
 * Pertenece a la HU: Toolbar Personalizada.
 */
class CustomToolbarFragment : Fragment() {

    private var _binding: FragmentCustomToolbarBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomToolbarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
