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
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.util.Key
import com.intellij.psi.PsiAnnotation
import com.intellij.psi.util.CachedValue
import com.intellij.psi.util.CachedValueProvider
import com.intellij.psi.util.CachedValuesManager
import com.intellij.psi.util.PsiModificationTracker

abstract class RequestMappingByNameContributor : ChooseByNameContributor {

    // Java·Kotlin 구현체가 서로의 결과를 덮어쓰지 않도록 구현체 인스턴스마다 캐시 key를 따로 둔다.
    private val itemsCacheKey =
        Key.create<CachedValue<List<RequestMappingItem>>>("${javaClass.name}.requestMappingItems")

    abstract fun getAnnotationSearchers(annotationName: String, project: Project): Sequence<PsiAnnotation>

    override fun getNames(project: Project, includeNonProjectItems: Boolean): Array<String> {
        return requestMappingItems(project)
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
        return requestMappingItems(project)
            .filter { it.name == name }
            .toTypedArray()
    }

    /**
     * 검색 팝업은 키 입력마다 getNames/getItemsByName을 부르므로 프로젝트 전체 스캔 결과를 캐시한다.
     * 항목이 PSI와 모듈 이름(location 문자열)을 들고 있으므로 PSI나 프로젝트 구조가 바뀌면 다시 스캔한다.
     * 동시에 들어오는 검색 요청끼리 공유 mutable 상태 없이 같은 결과를 본다.
     */
    private fun requestMappingItems(project: Project): List<RequestMappingItem> =
        CachedValuesManager.getManager(project).getCachedValue(project, itemsCacheKey, {
            CachedValueProvider.Result.create(
                supportedAnnotations.flatMap { annotation -> findRequestMappingItems(project, annotation) },
                PsiModificationTracker.MODIFICATION_COUNT,
                ProjectRootManager.getInstance(project),
            )
        }, false)

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
