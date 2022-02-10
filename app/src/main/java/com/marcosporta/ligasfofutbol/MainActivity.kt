package com.marcosporta.ligasfofutbol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun clickPartidos(view: View){
        var intent= Intent(this,PartidosActivity::class.java)
        startActivity(intent)
        //toast
        Toast.makeText(this,"Fixture", Toast.LENGTH_LONG).show()
    }
    fun clickPosiciones(view: View){
        var intent= Intent(this,PosicionesActivity::class.java)
        startActivity(intent)
        //toast
        Toast.makeText(this,"Posiciones",Toast.LENGTH_LONG).show()
    }
}