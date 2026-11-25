package com.ddoong2.endpointfinder.annotations.spring

import com.ddoong2.endpointfinder.annotations.UrlFormatter
import com.ddoong2.endpointfinder.utils.dropFirstEmptyStringIfExists

object SpringUrlFormatter : UrlFormatter {

    override fun format(classMapping: String, methodMapping: String, param: String): String {
        val classPathSeq = classMapping.splitToSequence('/').filterNot { it.isBlank() }
        val methodPathList = methodMapping.split('/').dropFirstEmptyStringIfExists()
        val path = (classPathSeq + methodPathList).joinToString(separator = "/", prefix = "/").replace("\${", "{")
        return path + if (param.isNotBlank()) " params=$param" else ""
    }
}
