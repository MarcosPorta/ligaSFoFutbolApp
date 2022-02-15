package com.marcosporta.ligasfofutbol

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner

class PartidosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partidos)

        //Poner boton regresar y titulo en el Action Bar
        supportActionBar?.title = "Partidos"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val spinner: Spinner= findViewById(R.id.sp_fechaPart)

        ArrayAdapter.createFromResource(this,R.array.fechas,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        val spinner2: Spinner = findViewById(R.id.sp_categoriaPart)

        ArrayAdapter.createFromResource(this,R.array.categorias,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner2.adapter = adapter
        }

        val spinner3: Spinner = findViewById(R.id.sp_zonaPart)

        ArrayAdapter.createFromResource(this,R.array.zonas,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner3.adapter = adapter
        }


    }
}