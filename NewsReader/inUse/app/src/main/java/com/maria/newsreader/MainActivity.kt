package com.maria.newsreader

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.fragment_detalles.*
import java.util.stream.Collectors

class MainActivity : AppCompatActivity(), ListaFragment.ItemSeleccionable {

    var noticias: List<Noticia> = ArrayList()
    var titulos: List<String> = ArrayList()
    var contenido: List<String> = ArrayList()

    override fun seHaEscogidoUnItem(posicion: Int) {
        tvTitulo.text = titulos[posicion]
        tvDetalles.text = contenido[posicion]

        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            val manager = supportFragmentManager
            manager.beginTransaction()
                .show(manager.findFragmentById(R.id.fragment2)!!)
                .hide(manager.findFragmentById(R.id.fragment)!!)
                .addToBackStack(null)
                .commit()
        } else {
            val manager = supportFragmentManager
            manager.beginTransaction()
                .show(manager.findFragmentById(R.id.fragment2)!!)
                .show(manager.findFragmentById(R.id.fragment)!!)
                .commit()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            val manager = supportFragmentManager
            manager.beginTransaction()
                .hide(manager.findFragmentById(R.id.fragment2)!!)
                .show(manager.findFragmentById(R.id.fragment)!!)
                .commit()
        } else {
            val manager = supportFragmentManager
            manager.beginTransaction()
                .show(manager.findFragmentById(R.id.fragment2)!!)
                .show(manager.findFragmentById(R.id.fragment)!!)
                .commit()
        }

        noticias = NoticiasUtils().execute().get()
        titulos = noticias.parallelStream().map(Noticia::titulo).collect(Collectors.toList())
        contenido = noticias.parallelStream().map(Noticia::texto).collect(Collectors.toList())
    }
}
