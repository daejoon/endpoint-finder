package com.ddoong2.endpointfinder

import com.intellij.ide.util.PsiNavigationSupport
import com.intellij.navigation.ItemPresentation
import com.intellij.navigation.NavigationItem
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.ReadAction
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.util.Computable
import com.intellij.pom.Navigatable
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMethod

class RequestMappingItem(val psiElement: PsiElement, private val urlPath: String, private val requestMethod: String) :
    NavigationItem {

    private val navigationElement = psiElement.navigationElement as? Navigatable

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

        override fun getLocationString(): String {

            return ApplicationManager.getApplication().runReadAction(Computable {
                val psiElement = this@RequestMappingItem.psiElement
                val fileName = psiElement.containingFile?.name
                when (psiElement) {
                    is PsiMethod -> (psiElement.containingClass?.name ?: fileName ?: "unknownFile") + "." + psiElement.name + getPresentModuleName()
                    is PsiClass -> psiElement.name ?: fileName ?: ("unknownFile" + getPresentModuleName())
                    else -> "unknownLocation"
                }
            })
        }

        // Return icon based on HTTP method
        override fun getIcon(b: Boolean) = when (this@RequestMappingItem.requestMethod) {
            "GET" -> RequestMapperIcons.GET
            "POST" -> RequestMapperIcons.POST
            "PUT" -> RequestMapperIcons.PUT
            "DELETE" -> RequestMapperIcons.DELETE
            "PATCH" -> RequestMapperIcons.PATCH
            else -> RequestMapperIcons.DEFAULT
        }

        private fun getPresentModuleName(): String {
            val moduleName = getModuleName()
            if (moduleName.isEmpty()) {
                return ""
            }
            val names = moduleName.split(""".""")
            return " (" + if (names.size >= 2) { names[names.size - 2].uppercase() } else { names[0].uppercase() } + ")"
        }

        private fun getModuleName(): String {
            return ApplicationManager.getApplication().runReadAction(Computable {
                val element = this@RequestMappingItem.psiElement
                val file = element.containingFile.originalFile.virtualFile
                val module = ProjectRootManager.getInstance(element.project).fileIndex.getModuleForFile(file)

                module?.name ?: ""
            })
        }
    }
}
