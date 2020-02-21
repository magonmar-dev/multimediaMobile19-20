package com.maria.newsreader

class Noticia() {

    var titulo: String = ""
    var link: String = ""
    var texto: String = ""
    var img: String = ""

    override fun toString(): String {
        return "Noticia(titulo='$titulo', link='$link', texto='$texto', img='$img')"
    }
}