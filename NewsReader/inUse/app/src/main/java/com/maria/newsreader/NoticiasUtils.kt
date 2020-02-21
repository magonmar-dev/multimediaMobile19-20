package com.maria.newsreader

import android.os.AsyncTask
import android.util.Log
import org.jsoup.Jsoup
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.xml.sax.InputSource
import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory

class NoticiasUtils: AsyncTask<Void, Int, List<Noticia>>() {

    override fun doInBackground(vararg p0: Void?): List<Noticia> {

        var lista: MutableList<Noticia> = ArrayList()

        try {
            val dbFactory = DocumentBuilderFactory.newInstance()
            val dBuilder = dbFactory.newDocumentBuilder()

            val url = URL("https://e00-marca.uecdn.es/rss/futbol/futbol-femenino.xml")
            val doc = dBuilder.parse(InputSource(url.openStream()))
            doc.documentElement.normalize()
            val nList = doc.getElementsByTagName("item")

            for (temp in 0 until nList.length) {
                val nNode = nList.item(temp)
                if (nNode.nodeType == Node.ELEMENT_NODE) {
                    val eElement = nNode as Element
                    val n = Noticia()
                    n.titulo = eElement.getElementsByTagName("title").item(0).textContent
                    n.link = eElement.getElementsByTagName("guid").item(0).textContent
                    n.texto = leerHTML(n.link)
                    lista.add(n)
                }
            }
        } catch (e: Exception) {
            Log.d("DEBUG","ERROR "+e.message)
            e.printStackTrace()
        }

        return lista
    }

    fun leerHTML(link: String) : String {

        var texto = ""
        try {
            val doc = Jsoup.connect(link).get()
            doc.select(".news-item:first-of-type div:nth-child(5) p")
                .forEach { p -> texto += p.text() + "\n\n" }
        } catch (e: Exception) {
            Log.d("DEBUG","ERROR "+e.message)
            e.printStackTrace()
        }
        return texto
    }
}