package com.ddoong2.endpointfinder.annotations.extraction

import com.ddoong2.endpointfinder.annotations.extraction.PsiExpressionExtractor.extractExpression
import com.intellij.psi.PsiBinaryExpression

class PsiBinaryExpressionExtractor : PsiAnnotationValueExtractor<PsiBinaryExpression> {

    override fun extract(value: PsiBinaryExpression) =
        listOf(extractExpression(value.lOperand) + extractExpression(value.rOperand!!))
}
