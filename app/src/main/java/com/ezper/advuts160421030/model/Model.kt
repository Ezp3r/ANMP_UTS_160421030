package com.ezper.advuts160421030.model

data class User(
    val username:String,
    val email:String?,
    val nama_depan:String?,
    val nama_belakang:String?,
    val password:String?
)

data class Berita(
    val id:Int,
    val title:String?,
    val url:String?,
    val author:String?,
    val description:String?
)

data class Detail(
    val id: Int,
    val page:Int?,
    val subtitle:String,
    val isi:String?,
    val beritas_id:Int?
)