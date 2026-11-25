package com.ddoong2.endpointfinder.contributor

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiAnnotation
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class RequestMappingByNameContributorTest : BasePlatformTestCase() {

    // 검색 팝업은 키 입력마다 getNames를 부르므로, PSI가 그대로면 프로젝트 전체 어노테이션 스캔을 반복하지 않아야 한다.
    fun testGetNamesDoesNotRescanWhenPsiIsUnchanged() {
        val contributor = CountingContributor()

        contributor.getNames(project, false)
        val scansAfterFirstCall = contributor.scans
        contributor.getNames(project, false)

        assertEquals(scansAfterFirstCall, contributor.scans)
    }

    fun testGetNamesRescansAfterPsiChange() {
        val contributor = CountingContributor()

        contributor.getNames(project, false)
        val scansAfterFirstCall = contributor.scans
        myFixture.configureByText("UserController.java", "class UserController {}")
        contributor.getNames(project, false)

        assertEquals(scansAfterFirstCall * 2, contributor.scans)
    }

    private class CountingContributor : RequestMappingByNameContributor() {
        var scans = 0

        override fun getAnnotationSearchers(annotationName: String, project: Project): Sequence<PsiAnnotation> {
            scans++
            return emptySequence()
        }
    }
}
