package com.ddoong2.endpointfinder.annotations

import com.ddoong2.endpointfinder.RequestMappingItem

object UnknownAnnotation : MappingAnnotation {
    private val mappingItems = emptyList<RequestMappingItem>()
    override fun values(): List<RequestMappingItem> = mappingItems
}
