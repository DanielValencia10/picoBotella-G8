package com.equipo8.picobotella.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.equipo8.picobotella.databinding.FragmentInstruccionesBinding

/**
 * Fragmento que muestra las instrucciones del juego.
 * HU 5.0: Instrucciones del juego
 * Criterios:
 * C1 - Pausar audio al entrar si estaba ON
 * C2 - Fondo gris oscuro (definido en XML)
 * C3 - Toolbar con título y flecha atrás
 * C4 - Título ¿Cómo se juega? (definido en XML)
 * C5 - Descripción reglas (definido en XML)
 * C6 - Título ¿Quién gana? (definido en XML)
 * C7 - Descripción ganador (definido en XML)
 * C8 - Animación de triunfo Lottie (definido en XML)
 */
class InstruccionesFragment : Fragment() {

    private var _binding: FragmentInstruccionesBinding? = null
    private val binding get() = _binding!!

    // TODO: descomentar cuando Persona 2 cree HomeViewModel
    // private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInstruccionesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configurarToolbar()  // C3
        manejarAudio()       // C1
    }

    // C3: toolbar con título y flecha atrás
    private fun configurarToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            restablecerAudio()
            findNavController().popBackStack()
        }
    }

    // C1: pausar audio al entrar si estaba encendido
    private fun manejarAudio() {
        // conectar con HomeViewModel cuando Persona 2 lo cree
        // if (homeViewModel.audioEncendido.value == true) {
        //     homeViewModel.pausarAudio()
        // }
    }

    // C3: reanudar audio al salir si estaba encendido
    private fun restablecerAudio() {
        // conectar con HomeViewModel cuando Persona 2 lo cree
        // if (homeViewModel.audioEncendido.value == true) {
        //     homeViewModel.reanudarAudio()
        // }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}