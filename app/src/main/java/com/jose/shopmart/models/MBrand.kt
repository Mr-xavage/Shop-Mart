package com.jose.shopmart.models

data class MBrand(
    val logo: Any? = null,
    val brand_name: String? = null
){
    fun convertToMap(): MutableMap<String,Any>{
        return mutableMapOf(
            "logo" to logo!!,
            "brand_name" to brand_name!!
        )
    }
}
