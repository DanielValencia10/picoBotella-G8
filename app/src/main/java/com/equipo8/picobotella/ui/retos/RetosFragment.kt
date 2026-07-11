package com.equipo8.picobotella.ui.retos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.equipo8.picobotella.data.local.AppDatabase
import com.equipo8.picobotella.data.repository.RetoRepository
import com.equipo8.picobotella.databinding.FragmentRetosBinding
import com.equipo8.picobotella.ui.dialogs.AgregarRetoDialog
import com.equipo8.picobotella.ui.dialogs.EditarRetoDialog
import com.equipo8.picobotella.ui.dialogs.EliminarRetoDialog

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

        // Conectar base de datos Room al ViewModel
        val db = AppDatabase.getDatabase(requireContext())
        val repository = RetoRepository(db.retoDao())
        val factory = RetosViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[RetosViewModel::class.java]

        configurarRecyclerView()
        configurarObservadores()
        configurarBotones()
    }

    // RecyclerView con LinearLayoutManager para habilitar scroll
    private fun configurarRecyclerView() {
        adapter = RetosAdapter(
            emptyList(),
            onEditClick = { reto ->
                // lanza diálogo editar (HU 8.0) Pendiente
                EditarRetoDialog(reto) { retoActualizado ->
                    viewModel.editarReto(retoActualizado)
                }.show(childFragmentManager, "EditarRetoDialog")
            },
            onDeleteClick = { reto ->
                // lanza diálogo eliminar (HU 9.0) Pendiente
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
        // Botón atrás regresa al home (el audio se restablece vía HomeFragment.onResume)
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
