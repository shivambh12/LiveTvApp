package com.example.livetv.models

import java.io.Serializable

data class channel  (
    val id:Int,
    val name:String,
    val description:String,
    val live_url:String,
    val thumbnail:String,
    val facebook:String,
    val twitter:String,
    val youtube:String,
    val website:String,
    val category:String,
        ):Serializable {
    override fun toString(): String {
        return "channel(id=$id, name='$name', description='$description', thumbnail='$thumbnail', live_url='$live_url', facebook='$facebook', twitter='$twitter', youtube='$youtube', website='$website', category='$category')"
    }
}