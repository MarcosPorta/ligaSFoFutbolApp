package com.marcosporta.ligasfofutbol

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import java.util.*

class PosicionesActivity : AppCompatActivity() {

    lateinit var mAdView : AdView
    val testId= Arrays.asList("572A1A67BA6623DBD9D945D4043174CB")
    val configuracion= RequestConfiguration.Builder().setTestDeviceIds(testId).build()
    var tbPosiciones: TableLayout?=null
    lateinit var spinnerZona:Spinner
    lateinit var spinnerCat:Spinner
    lateinit var spinnerTor:Spinner
    var zonaSeleccionada:String = "Zona"
    var categoriaSeleccionada:String = "Categoria"
    var torneoSeleccionado:String = "Torneo"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posiciones)

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
            //Zona
        spinnerZona = findViewById(R.id.sp_zonaPos)
        val listaZona = resources.getStringArray(R.array.zonas)

        val adaptadorZona = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,listaZona)
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

        val adaptadorCat = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,listaCat)
        spinnerCat.adapter = adaptadorCat

        spinnerCat.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                categoriaSeleccionada = spinnerCat.selectedItem.toString()
                //Funcionalidad para realizar las consultas.
                consultasPosiciones(zonaSeleccionada,categoriaSeleccionada,torneoSeleccionado)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        spinnerTor = findViewById(R.id.sp_torneoPos)
        val listaTor = resources.getStringArray(R.array.torneos)

        val adaptadorTor = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,listaTor)
        spinnerTor.adapter = adaptadorTor

        spinnerTor.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                torneoSeleccionado = spinnerTor.selectedItem.toString()
                //Funcionalidad para realizar las consultas.
                consultasPosiciones(zonaSeleccionada,categoriaSeleccionada,torneoSeleccionado)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        }

        private fun consultasPosiciones(zona: String, categoria:String, torneo:String){
            if (torneoSeleccionado != "Torneo" && zonaSeleccionada != "Zona" && categoriaSeleccionada != "Categoria"){
                var url = "https://marcosporta.site/ligasfcoapp/pos$zona$categoria$torneo.php"
                Toast.makeText(this,"url recibida: $url", Toast.LENGTH_LONG).show()
                tbPosiciones=findViewById(R.id.tbPosiciones)
                tbPosiciones?.removeAllViews()
            }

            }
}