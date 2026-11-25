package com.ddoong2.endpointfinder

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.WriteAction
import com.intellij.psi.PsiJavaFile
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class RequestMappingItemPresentationTest : BasePlatformTestCase() {

    // 렌더러는 EDT에서 location을 매번 읽는다. 그때 read lock을 요구하면 write action이 끝날 때까지 UI가 멈추므로,
    // write action을 쥔 채로 다른 스레드에서 읽어도 즉시 반환되어야 한다.
    fun testLocationStringDoesNotWaitForWriteAction() {
        val file =
            myFixture.configureByText("UserController.java", "class UserController { void list() {} }") as PsiJavaFile
        val method = file.classes.single().methods.single()
        val item = RequestMappingItem(method, "/users", "GET")

        val location = WriteAction.compute<String?, RuntimeException> {
            val future = ApplicationManager.getApplication()
                .executeOnPooledThread(Callable { item.presentation.locationString })
            try {
                future.get(3, TimeUnit.SECONDS)
            } catch (e: TimeoutException) {
                null
            }
        }

        assertEquals("UserController.list (LIGHT_IDEA_TEST_CASE)", location)
    }
}
