package com.marcosporta.ligasfofutbol

import android.content.Intent
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

class PosicionesInfActivity : AppCompatActivity() {

    lateinit var mAdView : AdView
    val testId= Arrays.asList("572A1A67BA6623DBD9D945D4043174CB")
    val configuracion= RequestConfiguration.Builder().setTestDeviceIds(testId).build()
    var tbPosiciones: TableLayout?=null
    lateinit var spinnerZona: Spinner
    lateinit var spinnerCat: Spinner
    lateinit var spinnerTor: Spinner
    lateinit var spinnerPos: Spinner
    var zonaSeleccionada:String = "Zona"
    var categoriaSeleccionada:String = "Categoria"
    var torneoSeleccionado:String = "Torneo"
    var moiSeleccionado:String = "MayoInf"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posiciones_inf)

        MobileAds.setRequestConfiguration(configuracion)
        RequestConfiguration.Builder().setTestDeviceIds(testId)
        MobileAds.initialize(this) {}

        //Banner
        mAdView = findViewById(R.id.bannerPosiciones)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        //Poner boton regresar y titulo en el Action Bar
        supportActionBar?.title = "Posiciones"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //SPINNERS
        //Mayores o inferiores.
        spinnerPos = findViewById(R.id.sp_posiciones)
        val listaMayInf = resources.getStringArray(R.array.mayinf)

        val adaptadorMayInf = ArrayAdapter(this,R.layout.spinner_style,listaMayInf)
        spinnerPos.adapter = adaptadorMayInf

        spinnerPos.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                moiSeleccionado = spinnerPos.selectedItem.toString()
                if (moiSeleccionado == "Mayores"){

                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        //Zona
        spinnerZona = findViewById(R.id.sp_zonaPos)
        val listaZona = resources.getStringArray(R.array.zonas)

        val adaptadorZona = ArrayAdapter(this,R.layout.spinner_style,listaZona)
        spinnerZona.adapter = adaptadorZona

        spinnerZona.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                zonaSeleccionada = spinnerZona.selectedItem.toString()

                //Funcionalidad para realizar las consultas.
                consultasPosiciones(zonaSeleccionada,categoriaSeleccionada,torneoSeleccionado)
            }
            //Cuando NO tengo un elemento seleccionado.
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        //Categoria
        spinnerCat = findViewById(R.id.sp_categoriaPos)
        val listaCat = resources.getStringArray(R.array.categorias)

        val adaptadorCat = ArrayAdapter(this,R.layout.spinner_style,listaCat)
        spinnerCat.adapter = adaptadorCat

        spinnerCat.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                categoriaSeleccionada = spinnerCat.selectedItem.toString()
                //Funcionalidad para realizar las consultas.
                consultasPosiciones(zonaSeleccionada,categoriaSeleccionada,torneoSeleccionado)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        spinnerTor = findViewById(R.id.sp_torneoPos)
        val listaTor = resources.getStringArray(R.array.torneos)

        val adaptadorTor = ArrayAdapter(this,R.layout.spinner_style,listaTor)
        spinnerTor.adapter = adaptadorTor

        spinnerTor.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                torneoSeleccionado = spinnerTor.selectedItem.toString()
                //Funcionalidad para realizar las consultas.
                consultasPosiciones(zonaSeleccionada,categoriaSeleccionada,torneoSeleccionado)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun consultasPosiciones(zona: String, categoria:String, torneo:String){
        if (torneoSeleccionado == "Clausura" && zonaSeleccionada != "Zona" &&
            (categoriaSeleccionada != "Categoria" && categoriaSeleccionada != "Primera" && categoriaSeleccionada != "Reserva")){
            Toast.makeText(this,"No hay tabla para:\n$zona $categoria $torneo", Toast.LENGTH_LONG).show()
        }
        else if (torneoSeleccionado != "Torneo" && zonaSeleccionada != "Zona" && categoriaSeleccionada != "Categoria"){
            var url = "https://marcosporta.site/ligasfcoapp/pos$zona$categoria$torneo.php"
            Toast.makeText(this,"$zona $categoria $torneo", Toast.LENGTH_LONG).show()
            tbPosiciones=findViewById(R.id.tbPosiciones)
            tbPosiciones?.removeAllViews()

            var queue= Volley.newRequestQueue(this)

            var jsonObjectRequest= JsonObjectRequest(
                Request.Method.GET,url,null,
                { response ->
                    try {
                        var jsonArray = response.getJSONArray("data")
                        for(i in 0 until jsonArray.length() ){
                            var jsonObject=jsonArray.getJSONObject(i)

                            val registro= LayoutInflater.from(this).inflate(R.layout.table_row_posiciones,null,false)
                            val colEquipo=registro.findViewById<View>(R.id.colEquipo) as TextView
                            val colpj=registro.findViewById<View>(R.id.colpj) as TextView
                            val colpg=registro.findViewById<View>(R.id.colpg) as TextView
                            val colpe=registro.findViewById<View>(R.id.colpe) as TextView
                            val colpp=registro.findViewById<View>(R.id.colpp) as TextView
                            val colpts=registro.findViewById<View>(R.id.colpts) as TextView
                            val coldif=registro.findViewById<View>(R.id.coldif) as TextView
                            colEquipo.text=jsonObject.getString("nombre")
                            colpj.text=jsonObject.getString("pj")
                            colpg.text=jsonObject.getString("pg")
                            colpe.text=jsonObject.getString("pe")
                            colpp.text=jsonObject.getString("pp")
                            colpts.text=jsonObject.getString("pts")
                            coldif.text=jsonObject.getString("dif")
                            tbPosiciones?.addView(registro)
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

    fun clickMayoresPos(view: View) {
        val intent = Intent(this,PosicionesActivity::class.java)
        startActivity(intent)
    }
}