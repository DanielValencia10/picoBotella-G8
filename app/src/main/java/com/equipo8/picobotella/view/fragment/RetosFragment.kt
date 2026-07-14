package com.equipo8.picobotella.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.equipo8.picobotella.databinding.FragmentRetosBinding
import com.equipo8.picobotella.view.adapter.RetosAdapter
import com.equipo8.picobotella.view.dialog.AgregarRetoDialog
import com.equipo8.picobotella.view.dialog.EditarRetoDialog
import com.equipo8.picobotella.view.dialog.EliminarRetoDialog
import com.equipo8.picobotella.viewmodel.RetosViewModel

/**
 * Fragmento para la gestión de retos.
 * HU 6.0: Agregar y listar retos.
 */
class RetosFragment : Fragment() {

    private var _binding: FragmentRetosBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RetosViewModel
    private lateinit var adapter: RetosAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRetosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicialización del ViewModel sin Factory (Usa AndroidViewModel internamente)
        viewModel = ViewModelProvider(this)[RetosViewModel::class.java]

        configurarRecyclerView()
        configurarObservadores()
        configurarBotones()
    }

    // RecyclerView con LinearLayoutManager para habilitar scroll
    private fun configurarRecyclerView() {
        adapter = RetosAdapter(
            emptyList(),
            onEditClick = { reto ->
                // lanza diálogo editar (HU 8.0)
                EditarRetoDialog(reto) { retoActualizado ->
                    viewModel.editarReto(retoActualizado)
                }.show(childFragmentManager, "EditarRetoDialog")
            },
            onDeleteClick = { reto ->
                // lanza diálogo eliminar (HU 9.0)
                EliminarRetoDialog(reto) { retoAEliminar ->
                    viewModel.eliminarReto(retoAEliminar)
                }.show(childFragmentManager, "EliminarRetoDialog")
            }
        )
        binding.rvRetos.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRetos.adapter = adapter
    }

    // lista desde Room (ORDER BY id DESC = nuevos primero)
    private fun configurarObservadores() {
        viewModel.listaDeRetos.observe(viewLifecycleOwner) { retos ->
            adapter.updateData(retos)
        }
    }

    private fun configurarBotones() {
        // Botón atrás regresa al home
        binding.btnVolver.setOnClickListener {
            findNavController().navigateUp()
        }

        // FAB naranja abre diálogo agregar reto (HU 7.0)
        binding.fabAgregarReto.setOnClickListener {
            AgregarRetoDialog { descripcion ->
                viewModel.agregarReto(descripcion)
            }.show(childFragmentManager, "AgregarRetoDialog")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
