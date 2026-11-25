package com.ddoong2.endpointfinder.annotations

interface UrlFormatter {

    fun format(classMapping: String, methodMapping: String, param: String = ""): String
}
