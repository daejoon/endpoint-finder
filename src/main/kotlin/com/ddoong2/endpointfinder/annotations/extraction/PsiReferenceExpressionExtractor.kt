package com.ddoong2.endpointfinder.annotations.extraction

import com.ddoong2.endpointfinder.annotations.extraction.PsiExpressionExtractor.extractExpression
import com.intellij.psi.PsiReferenceExpression

class PsiReferenceExpressionExtractor : PsiAnnotationValueExtractor<PsiReferenceExpression> {

    override fun extract(value: PsiReferenceExpression): List<String> = listOf(extractExpression(value))
}
