package com.ddoong2.endpointfinder.annotations.extraction

import com.intellij.psi.PsiAnnotationMemberValue
import com.ddoong2.endpointfinder.annotations.extraction.PsiExpressionExtractor.extractExpression
import com.ddoong2.endpointfinder.utils.unquote

class PsiAnnotationMemberValueExtractor : PsiAnnotationValueExtractor<PsiAnnotationMemberValue> {

    override fun extract(value: PsiAnnotationMemberValue): List<String> {
        val element = extractExpression(value)
        return when {
            element.isNotBlank() -> listOf(element)
            value.text.isNotBlank() -> listOf(value.text.unquote())
            else -> emptyList()
        }
    }
}
