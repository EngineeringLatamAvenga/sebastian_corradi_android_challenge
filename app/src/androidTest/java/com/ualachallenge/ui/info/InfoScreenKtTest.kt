package com.ualachallenge.ui.info

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import com.ualachallenge.R
import org.junit.Rule
import org.junit.Test

class InfoScreenKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testScreenIsDisplayed() {
        // Set the content for the test
        composeTestRule.setContent {
            InfoScreen(){}
        }

        // Check if the Text with the specified text is displayed
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        // Retrieve the string resource
        val backMessage = context.getString(R.string.back)

        composeTestRule
            .onNodeWithText(backMessage)
            .assertExists("The screen title was not found")
    }
}
