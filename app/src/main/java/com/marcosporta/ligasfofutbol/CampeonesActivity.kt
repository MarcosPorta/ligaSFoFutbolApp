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
    lateinit var spinnerCampeones: Spinner
    //lateinit var spinnerCampeones2: Spinner
    var zonaSeleccionada:String = "Zona"
    //var moiSeleccionada:String = "Mayores"

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
        spinnerCampeones = findViewById(R.id.sp_camp)
        val listaCamp1 = resources.getStringArray(R.array.zonasCampeones)
        val adaptadorCamp1 = ArrayAdapter(this,R.layout.spinner_style,listaCamp1)
        spinnerCampeones.adapter = adaptadorCamp1

        /*spinnerCampeones2 = findViewById(R.id.sp_camp_2)
        val listaCamp2 = resources.getStringArray(R.array.mayinf)
        val adaptadorCamp2 = ArrayAdapter(this,R.layout.spinner_style,listaCamp2)
        spinnerCampeones2.adapter = adaptadorCamp2*/

        //Funcionalidad a los spinners
        spinnerCampeones.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                zonaSeleccionada = spinnerCampeones.selectedItem.toString()
                cunsultasCampeones(zonaSeleccionada)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        /*spinnerCampeones2.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                moiSeleccionada = spinnerCampeones2.selectedItem.toString()
                cunsultasCampeones(zonaSeleccionada,moiSeleccionada)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }*/
    }

    private fun cunsultasCampeones(zona: String){
        //Ver los casos
        if (zonaSeleccionada!="Zona"){
            val url = "https://marcosporta.site/ligasfcoapp/camp$zona.php"
            Toast.makeText(this,url, Toast.LENGTH_LONG).show()

            tbCampeones=findViewById(R.id.tbCampeones)
            tbCampeones?.removeAllViews()

            val queue= Volley.newRequestQueue(this)

            val jsonObjectRequest= JsonObjectRequest(
                Request.Method.GET,url,null,
                { response ->
                    try {
                        val jsonArray = response.getJSONArray("data")
                        var mayoinfL = ""
                        for(i in 0 until jsonArray.length() ) {
                            val jsonObject = jsonArray.getJSONObject(i)

                            //Accediendo a valores de la BD
                            val mayoinfBD = jsonObject.getString("mayoinf")
                            println("MIRAR ACA ------------> $i $mayoinfBD // ")

                            if (mayoinfBD != mayoinfL){
                                println("MIRAR ACA ---> ENTRE ACA")
                                val registromoi=LayoutInflater.from(this).inflate(R.layout.text_mayinf,null,false)
                                val moi=registromoi.findViewById<View>(R.id.tituloMoi) as TextView
                                moi.text=jsonObject.getString("mayoinf")
                                tbCampeones?.addView(registromoi)
                                mayoinfL = mayoinfBD
                            }
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
