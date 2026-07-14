package com.equipo8.picobotella.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.equipo8.picobotella.R
import com.equipo8.picobotella.databinding.FragmentCustomToolbarBinding
import com.equipo8.picobotella.viewmodel.HomeViewModel

/**
 * Fragmento para la Toolbar personalizada.
 * Pertenece a la HU: Toolbar Personalizada.
 */
class CustomToolbarFragment : Fragment() {

    private var _binding: FragmentCustomToolbarBinding? = null
    private val binding get() = _binding!!

    // Se comparte la instancia del HomeViewModel con el HomeFragment (fragmento padre).
    // Como HomeViewModel es un AndroidViewModel, el sistema lo crea automáticamente sin Factory.
    private val homeViewModel: HomeViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
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
        binding.btnCalificar.setOnClickListener {
            animarClick(it) {
                findNavController().navigate(R.id.action_home_to_calificar)
            }
        }

        binding.btnInstrucciones.setOnClickListener {
            animarClick(it) {
                findNavController().navigate(R.id.action_home_to_instrucciones)
            }
        }

        binding.btnRetos.setOnClickListener {
            animarClick(it) {
                findNavController().navigate(R.id.action_home_to_retos)
            }
        }

        // C6: HU 10 - Compartir aplicación (bottom sheet nativo del SO)

        binding.btnCompartir.setOnClickListener {
            animarClick(it) {
                val mensaje = getString(R.string.mensaje_compartir)
                val link = getString(R.string.link_compartir)
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_SUBJECT, getString(R.string.titulo_compartir))
                    putExtra(Intent.EXTRA_TEXT, "$mensaje\n\n$link")
                }
                startActivity(Intent.createChooser(intent, getString(R.string.compartir)))
            }
        }
    }

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
