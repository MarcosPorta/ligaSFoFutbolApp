package com.marcosporta.ligasfofutbol

import android.app.Activity
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
                    var cont2 = ""
                    for(i in 0 until jsonArray.length() ){
                        var jsonObject=jsonArray.getJSONObject(i)

                        //Accediendo a un campo de la base de datos (fecha)
                        val fecha = jsonObject.getInt("fecha")
                        val diaHora = jsonObject.getString("diahora")

                        if (fecha != cont && diaHora != cont2 && diaHora != ""){
                            //println("MIRAR ACA ------------> $diaHora y $cont2")
                            val registro2 = LayoutInflater.from(this).inflate(R.layout.table_row_fecha, null, false)
                            val colNumeroFecha=registro2.findViewById<View>(R.id.colNumeroFecha) as TextView
                            val colDiaHora=registro2.findViewById<View>(R.id.colDiaHora) as TextView
                            colNumeroFecha.text=getString(R.string.fecha_para_temp_regular,fecha)
                            colDiaHora.text=jsonObject.getString("diahora")
                            tbFixture?.addView(registro2)
                            cont += 1
                            cont2 = diaHora
                        } else if (fecha != cont){
                            val registro2 = LayoutInflater.from(this).inflate(R.layout.table_row_fecha, null, false)
                            val colNumeroFecha=registro2.findViewById<View>(R.id.colNumeroFecha) as TextView
                            colNumeroFecha.text=getString(R.string.fecha_para_temp_regular,fecha)
                            tbFixture?.addView(registro2)
                            cont += 1
                        } else if (diaHora != cont2 && diaHora != ""){
                            val registro2 = LayoutInflater.from(this).inflate(R.layout.table_row_fecha, null, false)
                            val colDiaHora=registro2.findViewById<View>(R.id.colDiaHora) as TextView
                            colDiaHora.text=jsonObject.getString("diahora")
                            tbFixture?.addView(registro2)
                            cont2 = diaHora
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

        //SPINNERS
            //Zonas
        val spinnerZona: Spinner = findViewById(R.id.sp_zonaPart)
        val listaZona = resources.getStringArray(R.array.zonas)

        val adaptadorZona = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,listaZona)
        spinnerZona.adapter = adaptadorZona

        spinnerZona.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener{
            //Cuando tengo un elemento seleccionado.
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                Toast.makeText(this@PartidosActivity,listaZona[pos],Toast.LENGTH_LONG).show()
            }
            //Cuando NO tengo un elemento seleccionado.
            override fun onNothingSelected(parent: AdapterView<*>) {
                TODO("Not yet implemented")
            }
        }
            //Categorias
        val spinnerCat: Spinner = findViewById(R.id.sp_categoriaPart)
        val listaCat = resources.getStringArray(R.array.categorias)

        val adaptadorCat = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,listaCat)
        spinnerCat.adapter = adaptadorCat

        spinnerCat.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener{
            //Cuando tengo un elemento seleccionado.
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                Toast.makeText(this@PartidosActivity,listaCat[pos],Toast.LENGTH_LONG).show()
            }
            //Cuando NO tengo un elemento seleccionado.
            override fun onNothingSelected(parent: AdapterView<*>) {
                TODO("Not yet implemented")
            }
        }
            //Torneos
        val spinnerTor: Spinner= findViewById(R.id.sp_torneoPart)
        val listaTor = resources.getStringArray(R.array.torneos)

        val adaptadorTor = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,listaTor)
        spinnerTor.adapter = adaptadorTor

        spinnerTor.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener{
            //Cuando tengo un elemento seleccionado.
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                Toast.makeText(this@PartidosActivity,listaTor[pos],Toast.LENGTH_LONG).show()
            }
            //Cuando NO tengo un elemento seleccionado.
            override fun onNothingSelected(parent: AdapterView<*>) {
                TODO("Not yet implemented")
            }
        }

        //Funcionalidad para realizar las consultas.


    }

}

