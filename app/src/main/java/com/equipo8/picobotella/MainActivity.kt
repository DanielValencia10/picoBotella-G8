package com.equipo8.picobotella

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.equipo8.picobotella.databinding.ActivityMainBinding

/**
 * Actividad principal de la aplicación.
 * Pertenece a la HU: Configuración Inicial.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Ocultar ActionBar por defecto
        supportActionBar?.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // El NavHostFragment se encarga de la navegación según nav_graph.xml
    }
}
