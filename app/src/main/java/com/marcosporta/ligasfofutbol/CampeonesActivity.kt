package com.marcosporta.ligasfofutbol

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import org.json.JSONException
import java.util.*

class CampeonesActivity : AppCompatActivity() {

    lateinit var mAdView : AdView
    val testId= Arrays.asList("572A1A67BA6623DBD9D945D4043174CB")
    val configuracion= RequestConfiguration.Builder().setTestDeviceIds(testId).build()
    var tbCampeones: TableLayout?=null
    lateinit var spinnerCampeones1: Spinner
    lateinit var spinnerCampeones2: Spinner
    var zonaSeleccionada:String = "Zona"
    var moiSeleccionada:String = "Mayores"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_campeones)

        MobileAds.setRequestConfiguration(configuracion)
        RequestConfiguration.Builder().setTestDeviceIds(testId)
        MobileAds.initialize(this) {}

        //Banner
        mAdView = findViewById(R.id.bannerCampeones)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        //Poner boton regresar y titulo en el Action Bar
        supportActionBar?.title = "Campeones"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Spinner
        spinnerCampeones1 = findViewById(R.id.sp_camp_1)
        val listaCamp1 = resources.getStringArray(R.array.zonasCampeones)
        val adaptadorCamp1 = ArrayAdapter(this,R.layout.spinner_style,listaCamp1)
        spinnerCampeones1.adapter = adaptadorCamp1

        spinnerCampeones2 = findViewById(R.id.sp_camp_2)
        val listaCamp2 = resources.getStringArray(R.array.mayinf)
        val adaptadorCamp2 = ArrayAdapter(this,R.layout.spinner_style,listaCamp2)
        spinnerCampeones2.adapter = adaptadorCamp2

        //Funcionalidad a los spinners
        spinnerCampeones1.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                zonaSeleccionada = spinnerCampeones1.selectedItem.toString()
                cunsultasCampeones(zonaSeleccionada,moiSeleccionada)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
            }

        spinnerCampeones2.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                moiSeleccionada = spinnerCampeones2.selectedItem.toString()
                cunsultasCampeones(zonaSeleccionada,moiSeleccionada)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun cunsultasCampeones(zona: String, categoria:String){
        //Ver los casos
        if (zonaSeleccionada!="Zona"){
            var url = "https://marcosporta.site/ligasfcoapp/camp$zona$categoria.php"
            Toast.makeText(this,url, Toast.LENGTH_LONG).show()

            tbCampeones=findViewById(R.id.tbCampeones)
            tbCampeones?.removeAllViews()

            val queue= Volley.newRequestQueue(this)

            val jsonObjectRequest= JsonObjectRequest(
                Request.Method.GET,url,null,
                { response ->
                    try {
                        val jsonArray = response.getJSONArray("data")
                        for(i in 0 until jsonArray.length() ) {
                            val jsonObject = jsonArray.getJSONObject(i)
                            val registro=LayoutInflater.from(this).inflate(R.layout.text_titulo,null,false)
                            val registro2=LayoutInflater.from(this).inflate(R.layout.table_row_campeones,null,false)
                            val tituloCamp=registro.findViewById<View>(R.id.tituloCamp) as TextView
                            val campeon=registro2.findViewById<View>(R.id.colEquipoCamp) as TextView
                            val fechaTitulo=registro2.findViewById<View>(R.id.colFechaTit) as TextView
                            tituloCamp.text=jsonObject.getString("titulo")
                            campeon.text=jsonObject.getString("equipo")
                            fechaTitulo.text=jsonObject.getString("fecha")
                            tbCampeones?.addView(registro)
                            tbCampeones?.addView(registro2)
                        }
                    }catch (e: JSONException){
                        e.printStackTrace()
                    }
                }, { error ->
                    Toast.makeText(this,"Error $error ", Toast.LENGTH_LONG).show()
                }
            )
            queue.add(jsonObjectRequest)
        }
    }
}
