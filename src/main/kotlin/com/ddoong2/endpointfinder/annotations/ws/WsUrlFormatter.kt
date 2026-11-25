package com.ddoong2.endpointfinder.annotations.ws

import com.ddoong2.endpointfinder.annotations.UrlFormatter
import com.ddoong2.endpointfinder.utils.dropFirstEmptyStringIfExists

object WsUrlFormatter : UrlFormatter {

    override fun format(classMapping: String, methodMapping: String, param: String): String {
        val classPathSeq = classMapping.splitToSequence('/').filterNot { it.isBlank() }
        val methodPathList = methodMapping.split('/').dropFirstEmptyStringIfExists()
        return (classPathSeq + methodPathList).joinToString(separator = "/", prefix = "/")
    }
}
