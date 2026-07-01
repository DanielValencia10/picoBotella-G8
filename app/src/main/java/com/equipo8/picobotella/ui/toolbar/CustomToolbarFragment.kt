package com.equipo8.picobotella.ui.toolbar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.equipo8.picobotella.R
import com.equipo8.picobotella.data.repository.RetoRepository
import com.equipo8.picobotella.databinding.FragmentCustomToolbarBinding
import com.equipo8.picobotella.ui.home.HomeViewModel
import com.equipo8.picobotella.ui.home.HomeViewModelFactory

/**
 * Fragmento para la Toolbar personalizada.
 * Pertenece a la HU: Toolbar Personalizada.
 * Criterios:
 * C1 - Fondo negro con bordes redondeados y componentes naranja (definido en XML)
 * C2 - Ícono de estrella -> HU 4.0: Calificar la aplicación
 * C3 - Ícono de audio de fondo (interruptor ON/OFF)
 * C4 - Ícono de instrucciones -> HU 5.0: Instrucciones del juego
 * C5 - Ícono de retos -> HU 6.0: Agregar y listar retos
 * C6 - Ícono de compartir -> HU 10: Compartir aplicación
 * C7 - Animación sutil de touch antes de navegar
 */
class CustomToolbarFragment : Fragment() {

    private var _binding: FragmentCustomToolbarBinding? = null
    private val binding get() = _binding!!

    // Se comparte la instancia del HomeViewModel con el HomeFragment (fragmento padre),
    // ya que el estado del audio de fondo (C3) vive ahí (HU2: Ventana Home Principal).
    private val homeViewModel: HomeViewModel by viewModels(
        ownerProducer = { requireParentFragment() },
        factoryProducer = { HomeViewModelFactory(RetoRepository()) }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomToolbarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configurarAudio()
        configurarNavegacion()
    }

    // C3: interruptor de audio de fondo, encendido por defecto
    private fun configurarAudio() {
        homeViewModel.isAudioEnabled.observe(viewLifecycleOwner) { habilitado ->
            binding.btnAudio.setImageResource(
                if (habilitado) R.drawable.ic_volume_on else R.drawable.ic_volume_off
            )
        }
        binding.btnAudio.setOnClickListener {
            animarClick(it) {
                homeViewModel.toggleAudio()
            }
        }
    }

    private fun configurarNavegacion() {
        // C2: HU 4.0 - Calificar la aplicación
        binding.btnCalificar.setOnClickListener {
            animarClick(it) {
                findNavController().navigate(R.id.action_home_to_calificar)
            }
        }

        // C4: HU 5.0 - Instrucciones del juego
        binding.btnInstrucciones.setOnClickListener {
            animarClick(it) {
                findNavController().navigate(R.id.action_home_to_instrucciones)
            }
        }

        // C5: HU 6.0 - Agregar y listar retos
        binding.btnRetos.setOnClickListener {
            animarClick(it) {
                findNavController().navigate(R.id.action_home_to_retos)
            }
        }

        // C6: HU 10 - Compartir aplicación
        binding.btnCompartir.setOnClickListener {
            animarClick(it) {
                findNavController().navigate(R.id.action_home_to_compartir)
            }
        }
    }

    // C7: animación sutil de touch (escala) antes de continuar con la navegación
    private fun animarClick(view: View, alFinalizar: () -> Unit) {
        view.animate()
            .scaleX(0.8f)
            .scaleY(0.8f)
            .setDuration(100)
            .withEndAction {
                view.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(100)
                    .withEndAction { alFinalizar() }
                    .start()
            }
            .start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
