package com.ddoong2.endpointfinder.contributor

import com.ddoong2.endpointfinder.RequestMappingItem
import com.ddoong2.endpointfinder.annotations.MappingAnnotation.Companion.mappingAnnotation
import com.ddoong2.endpointfinder.annotations.MappingAnnotation.Companion.supportedAnnotations
import com.ddoong2.endpointfinder.annotations.micronaut.MICRONAUT_PACKAGE_NAME
import com.ddoong2.endpointfinder.annotations.spring.SPRING_PACKAGE_NAME
import com.ddoong2.endpointfinder.annotations.ws.JAKARTA_WS_PACKAGE
import com.ddoong2.endpointfinder.annotations.ws.JAVAX_WS_PACKAGE
import com.ddoong2.endpointfinder.utils.isMethodAnnotation
import com.intellij.navigation.ChooseByNameContributor
import com.intellij.navigation.NavigationItem
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiAnnotation

abstract class RequestMappingByNameContributor(
    private var navigationItems: List<RequestMappingItem> = emptyList(),
) : ChooseByNameContributor {

    abstract fun getAnnotationSearchers(annotationName: String, project: Project): Sequence<PsiAnnotation>

    override fun getNames(project: Project, includeNonProjectItems: Boolean): Array<String> {
        navigationItems = supportedAnnotations
            .flatMap { annotation -> findRequestMappingItems(project, annotation) }

        return navigationItems
            .map { it.name }
            .distinct()
            .toTypedArray()
    }

    override fun getItemsByName(
        name: String,
        pattern: String,
        project: Project,
        includeNonProjectItems: Boolean,
    ): Array<NavigationItem> {
        return navigationItems
            .filter { it.name == name }
            .toTypedArray()
    }

    private fun findRequestMappingItems(project: Project, annotationName: String): List<RequestMappingItem> {
        return getAnnotationSearchers(annotationName, project)
            .filter { it.isMethodAnnotation() }
            .filter {
                it.qualifiedName!!.contains(MICRONAUT_PACKAGE_NAME)
                    || it.qualifiedName!!.contains(SPRING_PACKAGE_NAME)
                    || it.qualifiedName!!.contains(JAVAX_WS_PACKAGE)
                    || it.qualifiedName!!.contains(JAKARTA_WS_PACKAGE)
            }
            .map { annotation -> mappingAnnotation(annotationName, annotation) }
            .flatMap { mappingAnnotation -> mappingAnnotation.values().asSequence() }
            .toList()
    }
}
