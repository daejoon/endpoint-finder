package com.ddoong2.endpointfinder.contributor

import com.ddoong2.endpointfinder.utils.isMethodAnnotation
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class KotlinRequestMappingContributorTest : BasePlatformTestCase() {

    // Kotlin 소스의 어노테이션도 Java와 같은 PsiAnnotation 계약(FQN·속성값·부착 대상)으로 읽혀야 공통 추출 로직을 쓸 수 있다.
    fun testGetAnnotationSearchersReturnsKotlinMethodAnnotationAsPsiAnnotation() {
        myFixture.addFileToProject(
            "org/springframework/web/bind/annotation/GetMapping.java",
            """
            package org.springframework.web.bind.annotation;
            public @interface GetMapping { String[] value() default {}; }
            """.trimIndent(),
        )
        myFixture.configureByText(
            "UserController.kt",
            """
            import org.springframework.web.bind.annotation.GetMapping

            class UserController {
                @GetMapping("/users")
                fun list() {}
            }
            """.trimIndent(),
        )

        val annotations = KotlinRequestMappingContributor().getAnnotationSearchers("GetMapping", project).toList()

        assertEquals(1, annotations.size)
        val annotation = annotations.single()

        assertEquals("org.springframework.web.bind.annotation.GetMapping", annotation.qualifiedName)
        assertTrue(annotation.isMethodAnnotation())
        assertEquals("{\"/users\"}", annotation.findAttributeValue("value")?.text)
    }
}
