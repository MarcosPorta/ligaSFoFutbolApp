package com.marcosporta.ligasfofutbol

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TableLayout
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import java.util.*

class CampeonesActivity : AppCompatActivity() {

    lateinit var mAdView : AdView
    val testId= Arrays.asList("572A1A67BA6623DBD9D945D4043174CB")
    val configuracion= RequestConfiguration.Builder().setTestDeviceIds(testId).build()
    var tbCampeones: TableLayout?=null
    lateinit var spinnerCampeones1: Spinner
    lateinit var spinnerCampeones2: Spinner

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
    }
}