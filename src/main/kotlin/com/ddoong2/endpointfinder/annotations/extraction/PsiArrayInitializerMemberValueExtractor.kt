package com.ddoong2.endpointfinder.annotations.extraction

import com.ddoong2.endpointfinder.annotations.extraction.PsiExpressionExtractor.extractExpression
import com.ddoong2.endpointfinder.utils.unquote
import com.intellij.psi.PsiArrayInitializerMemberValue

class PsiArrayInitializerMemberValueExtractor : PsiAnnotationValueExtractor<PsiArrayInitializerMemberValue> {

    override fun extract(value: PsiArrayInitializerMemberValue): List<String> = value.initializers.map {
        val element = extractExpression(it)
        when {
            element.isNotBlank() -> element
            it.text.isNotBlank() -> it.text.unquote()
            else -> ""
        }
    }
}
