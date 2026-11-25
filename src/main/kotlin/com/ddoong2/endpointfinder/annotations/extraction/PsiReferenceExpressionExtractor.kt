package com.ddoong2.endpointfinder.annotations.extraction

import com.intellij.psi.PsiReferenceExpression
import com.ddoong2.endpointfinder.annotations.extraction.PsiExpressionExtractor.extractExpression

class PsiReferenceExpressionExtractor : PsiAnnotationValueExtractor<PsiReferenceExpression> {

    override fun extract(value: PsiReferenceExpression): List<String> = listOf(extractExpression(value))
}
