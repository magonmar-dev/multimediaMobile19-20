package com.maria.newsreader

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.ListFragment
import java.util.stream.Collectors

/**
 * A simple [Fragment] subclass.
 */
class ListaFragment : ListFragment() {

    interface ItemSeleccionable {
        fun seHaEscogidoUnItem(posicion: Int)
    }

    var miActivity: ItemSeleccionable? = null
    var noticias: List<Noticia> = ArrayList()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        noticias = NoticiasUtils().execute().get()
        val titulos = noticias.parallelStream().map(Noticia::titulo).collect(Collectors.toList())
        Log.d("DEBUG",titulos.size.toString() + titulos[0])
        listAdapter = ArrayAdapter<String>(context!!,
            android.R.layout.simple_list_item_activated_1, titulos)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ItemSeleccionable) {
            miActivity = context
        } else {
            throw RuntimeException("$context debe implementar ItemSeleccionable")
        }
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        miActivity?.seHaEscogidoUnItem(position)
    }
}
