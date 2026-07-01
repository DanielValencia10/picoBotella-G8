package com.equipo8.picobotella.ui.home

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.equipo8.picobotella.R
import com.equipo8.picobotella.data.repository.RetoRepository
import com.equipo8.picobotella.databinding.FragmentHomeBinding
import com.equipo8.picobotella.ui.toolbar.CustomToolbarFragment

/**
 * Controlador de la Vista (Fragment) para la Ventana Home Principal.
 * Pertenece a la HU2: Ventana Home Principal
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = RetoRepository()
        val factory = HomeViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        configurarAnimacionBoton()
        inicializarMusicaFondo()
        configurarObservadores()
        configurarOyentesEventos()
        configurarToolbar()
    }

    // HU: Toolbar Personalizada - se monta como fragmento hijo dentro de fragment_home.xml
    private fun configurarToolbar() {
        if (childFragmentManager.findFragmentById(R.id.toolbarContainer) == null) {
            childFragmentManager.beginTransaction()
                .replace(R.id.toolbarContainer, CustomToolbarFragment())
                .commit()
        }
    }

    private fun configurarAnimacionBoton() {
        val animation = AlphaAnimation(1.0f, 0.2f).apply {
            duration = 600
            repeatMode = AlphaAnimation.REVERSE
            repeatCount = AlphaAnimation.INFINITE
        }
        binding.btnPresioname.startAnimation(animation)
    }

    private fun inicializarMusicaFondo() {
        try {
            mediaPlayer = MediaPlayer.create(requireContext(), R.raw.sonido_fondo).apply {
                isLooping = true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun configurarObservadores() {
        viewModel.contadorRegresivo.observe(viewLifecycleOwner) { numero ->
            binding.tvContador.text = numero.toString()

            if (numero == 0) {
                binding.tvContador.text = "¡Ya!"
            }
        }

        // HU: Toolbar Personalizada (C3) - el interruptor de audio pausa/reanuda la música real
        viewModel.isAudioEnabled.observe(viewLifecycleOwner) { habilitado ->
            if (habilitado) {
                mediaPlayer?.takeIf { !it.isPlaying }?.start()
            } else {
                mediaPlayer?.takeIf { it.isPlaying }?.pause()
            }
        }
    }

    private fun configurarOyentesEventos() {
        binding.btnPresioname.setOnClickListener {
            viewModel.iniciarContador()
        }
    }

    override fun onPause() {
        super.onPause()

        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.pause()
        }
    }

    override fun onResume() {
        super.onResume()

        if (viewModel.isAudioEnabled.value == true && mediaPlayer?.isPlaying == false) {
            mediaPlayer?.start()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release()
        mediaPlayer = null
        _binding = null
    }
}