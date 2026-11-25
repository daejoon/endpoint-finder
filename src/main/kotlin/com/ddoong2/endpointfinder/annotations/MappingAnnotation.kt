package com.ddoong2.endpointfinder.annotations

import com.ddoong2.endpointfinder.RequestMappingItem
import com.ddoong2.endpointfinder.annotations.micronaut.Delete
import com.ddoong2.endpointfinder.annotations.micronaut.Get
import com.ddoong2.endpointfinder.annotations.micronaut.Head
import com.ddoong2.endpointfinder.annotations.micronaut.Options
import com.ddoong2.endpointfinder.annotations.micronaut.Patch
import com.ddoong2.endpointfinder.annotations.micronaut.Post
import com.ddoong2.endpointfinder.annotations.micronaut.Put
import com.ddoong2.endpointfinder.annotations.spring.DeleteMapping
import com.ddoong2.endpointfinder.annotations.spring.GetMapping
import com.ddoong2.endpointfinder.annotations.spring.PatchMapping
import com.ddoong2.endpointfinder.annotations.spring.PostMapping
import com.ddoong2.endpointfinder.annotations.spring.PutMapping
import com.ddoong2.endpointfinder.annotations.spring.RequestMapping
import com.ddoong2.endpointfinder.annotations.ws.DELETE
import com.ddoong2.endpointfinder.annotations.ws.GET
import com.ddoong2.endpointfinder.annotations.ws.HEAD
import com.ddoong2.endpointfinder.annotations.ws.OPTIONS
import com.ddoong2.endpointfinder.annotations.ws.PATCH
import com.ddoong2.endpointfinder.annotations.ws.POST
import com.ddoong2.endpointfinder.annotations.ws.PUT
import com.intellij.psi.PsiAnnotation

interface MappingAnnotation {

    fun values(): List<RequestMappingItem>

    companion object {
        val supportedAnnotations = listOf(
            RequestMapping::class.java.simpleName,
            GetMapping::class.java.simpleName,
            PostMapping::class.java.simpleName,
            PutMapping::class.java.simpleName,
            PatchMapping::class.java.simpleName,
            DeleteMapping::class.java.simpleName,

            GET::class.java.simpleName,
            PUT::class.java.simpleName,
            POST::class.java.simpleName,
            OPTIONS::class.java.simpleName,
            HEAD::class.java.simpleName,
            DELETE::class.java.simpleName,
            PATCH::class.java.simpleName,

            Delete::class.java.simpleName,
            Get::class.java.simpleName,
            Head::class.java.simpleName,
            Options::class.java.simpleName,
            Patch::class.java.simpleName,
            Post::class.java.simpleName,
            Put::class.java.simpleName
        )

        fun mappingAnnotation(annotationName: String, psiAnnotation: PsiAnnotation): MappingAnnotation {
            return when (annotationName) {
                RequestMapping::class.java.simpleName -> RequestMapping(psiAnnotation)
                GetMapping::class.java.simpleName -> GetMapping(psiAnnotation)
                PostMapping::class.java.simpleName -> PostMapping(psiAnnotation)
                PutMapping::class.java.simpleName -> PutMapping(psiAnnotation)
                PatchMapping::class.java.simpleName -> PatchMapping(psiAnnotation)
                DeleteMapping::class.java.simpleName -> DeleteMapping(psiAnnotation)

                GET::class.java.simpleName -> GET(psiAnnotation)
                PUT::class.java.simpleName -> PUT(psiAnnotation)
                POST::class.java.simpleName -> POST(psiAnnotation)
                OPTIONS::class.java.simpleName -> OPTIONS(psiAnnotation)
                HEAD::class.java.simpleName -> HEAD(psiAnnotation)
                DELETE::class.java.simpleName -> DELETE(psiAnnotation)
                PATCH::class.java.simpleName -> PATCH(psiAnnotation)

                Get::class.java.simpleName -> Get(psiAnnotation)
                Put::class.java.simpleName -> Put(psiAnnotation)
                Post::class.java.simpleName -> Post(psiAnnotation)
                Options::class.java.simpleName -> Options(psiAnnotation)
                Head::class.java.simpleName -> Head(psiAnnotation)
                Delete::class.java.simpleName -> Delete(psiAnnotation)
                Patch::class.java.simpleName -> Patch(psiAnnotation)

                else -> UnknownAnnotation
            }
        }
    }
}
