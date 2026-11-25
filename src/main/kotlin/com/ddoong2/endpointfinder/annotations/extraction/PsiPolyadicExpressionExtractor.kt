package com.ddoong2.endpointfinder.annotations.extraction

import com.intellij.psi.PsiPolyadicExpression
import com.ddoong2.endpointfinder.annotations.extraction.PsiExpressionExtractor.extractExpression

class PsiPolyadicExpressionExtractor : PsiAnnotationValueExtractor<PsiPolyadicExpression> {

    override fun extract(value: PsiPolyadicExpression) = listOf(extractExpression(value))
}
