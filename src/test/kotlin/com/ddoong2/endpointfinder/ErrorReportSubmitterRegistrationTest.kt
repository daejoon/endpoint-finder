package com.ddoong2.endpointfinder

import com.intellij.diagnostic.JetBrainsMarketplaceErrorReportSubmitter
import com.intellij.openapi.diagnostic.ErrorReportSubmitter
import com.intellij.openapi.extensions.ExtensionPointName
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class ErrorReportSubmitterRegistrationTest : BasePlatformTestCase() {

    // Marketplace의 Exceptions/Freezes 탭은 이 플러그인이 Marketplace 제출기를 등록했을 때만 리포트를 받는다.
    fun testPluginRegistersMarketplaceErrorReportSubmitter() {
        val submitters = ExtensionPointName.create<ErrorReportSubmitter>("com.intellij.errorHandler").extensionList

        val registered = submitters.any {
            it is JetBrainsMarketplaceErrorReportSubmitter
                    && it.pluginDescriptor?.pluginId?.idString == "com.ddoong2.endpointfinder"
        }

        assertTrue("Marketplace error handler must be registered by this plugin", registered)
    }
}
