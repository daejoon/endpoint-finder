package com.ddoong2.endpointfinder

import com.intellij.ide.util.PsiNavigationSupport
import com.intellij.navigation.ItemPresentation
import com.intellij.navigation.NavigationItem
import com.intellij.openapi.application.ReadAction
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.pom.Navigatable
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMethod

class RequestMappingItem(val psiElement: PsiElement, private val urlPath: String, private val requestMethod: String) :
    NavigationItem {

    private val navigationElement = psiElement.navigationElement as? Navigatable

    /**
     * 목록 렌더러는 EDT에서 행을 그릴 때마다 location을 읽는다.
     * 그때 PSI·모듈 인덱스를 조회하면 read lock 대기와 light class 계산으로 UI freeze가 나므로,
     * 항목을 만드는 시점(검색 contributor의 background read action 안)에 한 번만 계산해 둔다.
     */
    private val locationString: String = computeLocationString(psiElement)

    override fun getName(): String = this.requestMethod + " " + this.urlPath

    override fun getPresentation(): ItemPresentation = RequestMappingItemPresentation()

    override fun navigate(requestFocus: Boolean) = navigationTarget()?.navigate(requestFocus) ?: Unit

    /**
     * 이동할 소스 위치를 read action 안에서 미리 계산한다.
     * 최신 IDE는 EDT 이벤트 처리 중에도 read lock을 암묵적으로 잡지 않으므로,
     * PsiElement.navigate()를 바로 부르면 오프셋 계산(AST 로딩)에서 read access assertion이 발생한다.
     */
    internal fun navigationTarget(): Navigatable? = ReadAction.compute<Navigatable?, RuntimeException> {
        when (val element = navigationElement) {
            is PsiElement -> PsiNavigationSupport.getInstance().getDescriptor(element)
            else -> element
        }
    }

    override fun canNavigate(): Boolean = navigationElement?.canNavigate() ?: false

    override fun canNavigateToSource(): Boolean = true

    override fun toString(): String {
        return "RequestMappingItem(psiElement=$psiElement, urlPath='$urlPath', requestMethod='$requestMethod', navigationElement=$navigationElement)"
    }

    internal inner class RequestMappingItemPresentation : ItemPresentation {

        // HTTP method is represented by icon, so show only URL path in popup text.
        override fun getPresentableText() = this@RequestMappingItem.urlPath

        override fun getLocationString(): String = this@RequestMappingItem.locationString

        // Return icon based on HTTP method
        override fun getIcon(b: Boolean) = when (this@RequestMappingItem.requestMethod) {
            "GET" -> RequestMapperIcons.GET
            "POST" -> RequestMapperIcons.POST
            "PUT" -> RequestMapperIcons.PUT
            "DELETE" -> RequestMapperIcons.DELETE
            "PATCH" -> RequestMapperIcons.PATCH
            else -> RequestMapperIcons.DEFAULT
        }
    }

    private companion object {

        // 호출자가 read access를 가진 상태(검색 contributor의 read action)에서 생성 시 한 번만 호출된다.
        fun computeLocationString(psiElement: PsiElement): String = when (psiElement) {
            is PsiMethod -> (psiElement.containingClass?.name ?: psiElement.containingFile?.name
            ?: "unknownFile") + "." + psiElement.name + presentModuleName(psiElement)

            is PsiClass -> psiElement.name ?: psiElement.containingFile?.name
            ?: ("unknownFile" + presentModuleName(psiElement))

            else -> "unknownLocation"
        }

        private fun presentModuleName(element: PsiElement): String {
            val moduleName = moduleName(element)
            if (moduleName.isEmpty()) {
                return ""
            }
            val names = moduleName.split(""".""")
            return " (" + if (names.size >= 2) {
                names[names.size - 2].uppercase()
            } else {
                names[0].uppercase()
            } + ")"
        }

        private fun moduleName(element: PsiElement): String {
            val file = element.containingFile.originalFile.virtualFile
            val module = ProjectRootManager.getInstance(element.project).fileIndex.getModuleForFile(file)

            return module?.name ?: ""
        }
    }
}
