package com.ddoong2.endpointfinder.contributor

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiAnnotation
import com.intellij.psi.search.GlobalSearchScope.projectScope
import org.jetbrains.kotlin.idea.stubindex.KotlinAnnotationsIndex
import org.jetbrains.uast.UAnnotation
import org.jetbrains.uast.toUElementOfType

class KotlinRequestMappingContributor : com.ddoong2.endpointfinder.contributor.RequestMappingByNameContributor() {

    override fun getAnnotationSearchers(annotationName: String, project: Project): Sequence<PsiAnnotation> {
        return KotlinAnnotationsIndex[annotationName, project, projectScope(project)]
            .asSequence()
            // toLightAnnotation()은 2026.3부터 deprecated(KT-85385)라 UAST가 만들어 주는 Java 관점 PsiAnnotation을 쓴다.
            .mapNotNull { it.toUElementOfType<UAnnotation>()?.javaPsi }
    }
}
