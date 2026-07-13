package com.equipo8.picobotella.view.fragment

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.equipo8.picobotella.R
import com.equipo8.picobotella.databinding.FragmentHomeBinding
import com.equipo8.picobotella.view.dialog.MostrarRetoDialog
import com.equipo8.picobotella.viewmodel.HomeViewModel

/**
 * Controlador de la Vista (Fragment) para la Ventana Home Principal.
 * Pertenece a la HU2: Ventana Home Principal
 * HU 11: Giro de botella aleatorio
 * HU 12: Mostrar reto aleatorio
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

        // Inicialización del ViewModel sin Factory
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

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
        // HU 11 C5: Contador regresivo 3,2,1,0
        viewModel.contadorRegresivo.observe(viewLifecycleOwner) { numero ->
            binding.tvContador.text = numero.toString()

            if (numero == 0) {
                binding.tvContador.text = getString(R.string.ya)
            }
        }

        // HU 3 C3: Control de audio desde la toolbar
        viewModel.isAudioEnabled.observe(viewLifecycleOwner) { habilitado ->
            if (habilitado) {
                mediaPlayer?.takeIf { !it.isPlaying }?.start()
            } else {
                mediaPlayer?.takeIf { it.isPlaying }?.pause()
            }
        }

        // HU 11 C7: Ocultar/mostrar botón según estado del juego
        viewModel.isJugando.observe(viewLifecycleOwner) { jugando ->
            if (jugando) {
                binding.btnPresioname.visibility = View.GONE
                // HU 11 C8: Pausar audio de fondo mientras se juega
                mediaPlayer?.takeIf { it.isPlaying }?.pause()
            } else {
                binding.btnPresioname.visibility = View.VISIBLE
            }
        }

        // HU 12: Cuando se obtiene un reto aleatorio, mostrar el diálogo
        viewModel.retoAleatorio.observe(viewLifecycleOwner) { reto ->
            if (reto != null) {
                val dialogFragment = MostrarRetoDialog(
                    reto = reto,
                    onDialogDismiss = {
                        viewModel.restaurarAudio()
                    }
                )
                dialogFragment.show(childFragmentManager, "MostrarRetoDialog")

                // Limpiar el LiveData para que no se vuelva a mostrar
                viewModel.limpiarRetoAleatorio()
            }
        }

        // Mostrar mensaje si no hay retos disponibles
        viewModel.sinRetos.observe(viewLifecycleOwner) { noHayRetos ->
            if (noHayRetos) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.sin_retos_disponibles),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun configurarOyentesEventos() {
        // HU 11 C1: Al presionar el botón, iniciar el juego
        binding.btnPresioname.setOnClickListener {
            viewModel.iniciarJuego()
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