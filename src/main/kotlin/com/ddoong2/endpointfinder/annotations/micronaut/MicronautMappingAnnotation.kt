package com.ddoong2.endpointfinder.annotations.micronaut

import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiLiteralExpression
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiReferenceExpression
import com.ddoong2.endpointfinder.RequestMappingItem
import com.ddoong2.endpointfinder.annotations.MappingAnnotation
import com.ddoong2.endpointfinder.annotations.PathAnnotation
import com.ddoong2.endpointfinder.annotations.UrlFormatter
import com.ddoong2.endpointfinder.annotations.extraction.PsiExpressionExtractor.extractExpression
import com.ddoong2.endpointfinder.model.Path
import com.ddoong2.endpointfinder.model.PathParameter
import com.ddoong2.endpointfinder.utils.fetchAnnotatedMethod
import com.ddoong2.endpointfinder.utils.unquote

abstract class MicronautMappingAnnotation(
    private val psiAnnotation: PsiAnnotation,
    private val urlFormatter: UrlFormatter = MicronautUrlFormatter
) : MappingAnnotation {

    override fun values(): List<RequestMappingItem> =
        fetchRequestMappingItem(psiAnnotation, psiAnnotation.fetchAnnotatedMethod(), extractMethod())

    abstract fun extractMethod(): String

    private fun fetchRequestMappingItem(annotation: PsiAnnotation, psiMethod: PsiMethod, method: String): List<RequestMappingItem> {
        val classMapping = fetchMappingFromClass(psiMethod)
        val methodMapping = fetchMappingFromMethod(annotation, psiMethod)
        return listOf(RequestMappingItem(psiMethod, urlFormatter.format(classMapping, methodMapping), method))
    }

    private fun fetchMappingFromClass(psiMethod: PsiMethod): String {
        return psiMethod
            .containingClass
            ?.modifierList
            ?.annotations
            ?.flatMap { extractPathFromMicronautPsiAnnotations(it) }
            ?.firstOrNull() ?: ""
    }

    private fun extractPathFromMicronautPsiAnnotations(psiAnnotation: PsiAnnotation) = when (psiAnnotation.qualifiedName) {
        CONTROLLER_ANNOTATION -> PathAnnotation(psiAnnotation).fetchMappings(ATTRIBUTE_NAME)
        else -> emptyList()
    }

    private fun fetchMappingFromMethod(annotation: PsiAnnotation, method: PsiMethod): String {
        val parametersNameWithType = method
            .parameterList
            .parameters
            .mapNotNull {
                PathParameter(it).extractParameterNameWithType(PATH_VARIABLE_ANNOTATION, ::extractParameterNameFromAnnotation)
                    ?: Pair(it.name, it.type.presentableText.unquote())
            }
            .toMap()

        return PathAnnotation(annotation).fetchMappings(ATTRIBUTE_NAME)
            .map { Path(it).addPathVariablesTypes(parametersNameWithType).toFullPath() }
            .firstOrNull() ?: ""
    }

    private fun extractParameterNameFromAnnotation(annotation: PsiAnnotation, defaultValue: String): String {
        return when (val pathVariableValue = annotation.findAttributeValue(ATTRIBUTE_NAME)) {
            is PsiLiteralExpression -> {
                val expression = extractExpression(pathVariableValue)
                expression.ifBlank { defaultValue }
            }
            is PsiReferenceExpression -> {
                val expression = extractExpression(pathVariableValue)
                expression.ifBlank { defaultValue }
            }
            else -> defaultValue
        }
    }

    companion object {
        private const val CONTROLLER_ANNOTATION = "io.micronaut.http.annotation.Controller"
        private const val ATTRIBUTE_NAME = "value"
        private const val PATH_VARIABLE_ANNOTATION = "io.micronaut.http.annotation.PathVariable"
    }
}
