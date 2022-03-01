package com.marcosporta.ligasfofutbol

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import org.json.JSONException
import java.util.*

class PartidosActivity : AppCompatActivity() {

    lateinit var mAdView : AdView
    val testId= Arrays.asList("572A1A67BA6623DBD9D945D4043174CB")
    val configuracion= RequestConfiguration.Builder().setTestDeviceIds(testId).build()
    var tbFixture: TableLayout?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partidos)

        MobileAds.setRequestConfiguration(configuracion)
        RequestConfiguration.Builder().setTestDeviceIds(testId)
        MobileAds.initialize(this) {}

        //Banner
        mAdView = findViewById(R.id.bannerPartidos)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        //Poner boton regresar y titulo en el Action Bar
        supportActionBar?.title = "Partidos"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tbFixture=findViewById(R.id.tbFixture)
        tbFixture?.removeAllViews()
        var queue=Volley.newRequestQueue(this)
        var url="https://marcosporta.site/ligasfcoapp/registros.php"

        var jsonObjectRequest= JsonObjectRequest(
            Request.Method.GET,url,null,
            { response ->
                try {
                    var jsonArray = response.getJSONArray("data")
                    var cont = 0
                    for(i in 0 until jsonArray.length() ){
                        var jsonObject=jsonArray.getJSONObject(i)

                        //Accediendo a un campo de la base de datos (fecha)
                        val contFecha = jsonObject.getInt("fecha")

                        if (contFecha !== cont){
                            println("MIRAR ACA ---->>>>>>>>   $contFecha y $cont")
                            val registro2 = LayoutInflater.from(this).inflate(R.layout.table_row_fecha, null, false)
                            val colNumeroFecha=registro2.findViewById<View>(R.id.colNumeroFecha) as TextView
                            colNumeroFecha.text=getString(R.string.fecha_para_temp_regular,contFecha)
                            tbFixture?.addView(registro2)
                            cont += 1
                        }

                        val registro=LayoutInflater.from(this).inflate(R.layout.table_row_fixture,null,false)
                        val colEquipoL=registro.findViewById<View>(R.id.colEquipoL) as TextView
                        val colGolesL=registro.findViewById<View>(R.id.colGolesL) as TextView
                        val colGolesV=registro.findViewById<View>(R.id.colGolesV) as TextView
                        val colEquipoV=registro.findViewById<View>(R.id.colEquipoV) as TextView
                        colEquipoL.text=jsonObject.getString("equipo_l")
                        colGolesL.text=jsonObject.getString("goles_l")
                        colGolesV.text=jsonObject.getString("goles_v")
                        colEquipoV.text=jsonObject.getString("equipo_v")
                        tbFixture?.addView(registro)
                    }
                }catch (e: JSONException){
                    e.printStackTrace()
                }
            }, { error ->
                Toast.makeText(this,"Error $error ", Toast.LENGTH_LONG).show()
        }
        )
        queue.add(jsonObjectRequest)

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

