package com.ualachallenge.info

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.ualachallenge.ui.info.InfoScreen
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
        composeTestRule
            .onNodeWithText("Volver")
            .assertExists("The screen title was not found")
    }
}
