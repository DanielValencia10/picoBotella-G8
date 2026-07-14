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
import com.equipo8.picobotella.viewmodel.HomeViewModel
import com.equipo8.picobotella.view.dialog.MostrarRetoDialog

/**
 * Controlador de la Vista para la Ventana Home Principal.
 * Conecta el giro de la botella con el Diálogo de Reto Aleatorio (HU 11 y 12).
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel
    private var mediaPlayer: MediaPlayer? = null
    private var sonidoBotella: MediaPlayer? = null
    private var anguloActual = 0f
    private var girando = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        configurarAnimacionBoton()
        inicializarMusicaFondo()
        configurarObservadores()
        configurarOyentesEventos()
        configurarToolbar()
    }

    private fun configurarToolbar() {
        if (childFragmentManager.findFragmentById(R.id.toolbarContainer) == null) {
            childFragmentManager.beginTransaction()
                .replace(R.id.toolbarContainer, CustomToolbarFragment())
                .commit()
        }
    }

    private fun configurarObservadores() {
        // Observar el contador para mostrar el número grande en el centro
        viewModel.contadorRegresivo.observe(viewLifecycleOwner) { numero ->
            if (numero > 0) {
                binding.tvContador.text = numero.toString()
                binding.tvContador.visibility = View.VISIBLE
            } else {
                binding.tvContador.visibility = View.GONE
            }
        }

        // Observar cuando llega un reto aleatorio desde el ViewModel
        viewModel.retoAleatorio.observe(viewLifecycleOwner) { reto ->
            reto?.let {
                val dialog = MostrarRetoDialog(it) {
                    viewModel.limpiarRetoAleatorio()
                    viewModel.restaurarAudio()
                }
                dialog.show(childFragmentManager, "MostrarRetoDialog")
            }
        }

        // Si no hay retos en la base de datos
        viewModel.sinRetos.observe(viewLifecycleOwner) { sinRetos ->
            if (sinRetos) {
                Toast.makeText(requireContext(), "No hay retos guardados. ¡Agrega uno!", Toast.LENGTH_SHORT).show()
            }
        }

        // Ocultar botón mientras se está "girando" (contando)
        viewModel.isJugando.observe(viewLifecycleOwner) { jugando ->
            if (jugando) {
                binding.btnPresioname.visibility = View.GONE
            } else {
                binding.btnPresioname.visibility = View.VISIBLE
                configurarAnimacionBoton()
            }
        }

        // Control de audio
        viewModel.isAudioEnabled.observe(viewLifecycleOwner) { habilitado ->
            if (habilitado) {
                mediaPlayer?.takeIf { !it.isPlaying }?.start()
            } else {
                mediaPlayer?.takeIf { it.isPlaying }?.pause()
            }
        }
    }

    private fun girarBotella(onFinish: () -> Unit) {

        if (girando) return

        girando = true

        val vueltas = (5..8).random()

        val nuevoAngulo = anguloActual + vueltas * 360 + (0..359).random()

        val animator = android.animation.ObjectAnimator.ofFloat(
            binding.imgBotella,
            View.ROTATION,
            anguloActual,
            nuevoAngulo.toFloat()
        )

        animator.duration = (3000L..5000L).random()

        animator.addListener(object : android.animation.Animator.AnimatorListener {

            override fun onAnimationStart(animation: android.animation.Animator) {
                mediaPlayer?.pause()
                sonidoBotella?.start()
            }

            override fun onAnimationEnd(animation: android.animation.Animator) {

                anguloActual = nuevoAngulo % 360f
                girando = false

                sonidoBotella?.pause()
                sonidoBotella?.seekTo(0)

                onFinish()
            }

            override fun onAnimationCancel(animation: android.animation.Animator) {}

            override fun onAnimationRepeat(animation: android.animation.Animator) {}
        })

        animator.start()
    }

    private fun configurarOyentesEventos() {
        binding.btnPresioname.setOnClickListener {

            binding.btnPresioname.clearAnimation()
            binding.btnPresioname.visibility = View.GONE

            viewModel.iniciarPartida()

            girarBotella {
                viewModel.iniciarJuego() // Llama a la lógica completa (conteo + reto)
            }
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
            mediaPlayer = MediaPlayer.create(
                requireContext(),
                R.raw.sonido_fondo).apply {
                isLooping = true
            }

            sonidoBotella = MediaPlayer.create(
                requireContext(),
                R.raw.botella_girando
            )

        } catch (e: Exception) { e.printStackTrace() }
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer?.pause()
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.isAudioEnabled.value == true) mediaPlayer?.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release()
        mediaPlayer = null
        _binding = null
    }
}
