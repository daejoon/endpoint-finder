package com.github.nayacco.restfulhelper

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.psi.PsiJavaFile
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

class RequestMappingItemNavigationTest : BasePlatformTestCase() {

    // 테스트 EDT는 lock을 쥐고 있어 실제 IDE(EDT에 암묵적 read lock 없음)를 재현하지 못하므로,
    // lock이 없는 pooled thread에서 호출해 read action 없이 PSI에 접근하면 실패하도록 한다.
    fun testNavigationTargetResolvesSourceOffsetWithoutCallerReadLock() {
        val file = myFixture.configureByText("UserController.java", "class UserController { void list() {} }") as PsiJavaFile
        val method = file.classes.single().methods.single()
        val item = RequestMappingItem(method, "/users", "GET")

        val target = ApplicationManager.getApplication()
            .executeOnPooledThread(Callable { item.navigationTarget() })
            .get(10, TimeUnit.SECONDS)

        val descriptor = assertInstanceOf(target, OpenFileDescriptor::class.java)
        assertEquals(file.virtualFile, descriptor.file)
        assertEquals(28, descriptor.offset)
    }
}
