package com.example.restaurantfinder

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.restaurantfinder.ui.screens.home.HomeScreen
import com.example.restaurantfinder.ui.screens.home.HomeViewModel
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
//@HiltAndroidTest
class HomeScreenTest {

//    @get:Rule
//    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createComposeRule()

//    private val viewModel: HomeViewModel = hiltViewModel()

    @MockK
    lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
//        hiltRule.inject()
        // mock the viewModel and its LiveData/State
        viewModel = mockk()

        // compose UI for testing
        composeTestRule.setContent {
            HomeScreen(viewModel = viewModel)
        }
    }

    @Test
    fun testPostcodeSearch() {

        composeTestRule.setContent {
            HomeScreen(viewModel = viewModel)
        }


        composeTestRule.onNodeWithText("Enter UK postcode").performTextInput("SW1A 1AA")


        composeTestRule.onNodeWithText("Search").performClick()


        composeTestRule.onNodeWithContentDescription("Loading").assertIsDisplayed()


        viewModel.setRestaurants(mockRestaurants)


        composeTestRule.onNodeWithText("Restaurant 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Restaurant 2").assertIsDisplayed()
    }

    @Test
    fun testInvalidPostcodeError() {

        composeTestRule.setContent {
            HomeScreen(viewModel = viewModel)
        }


        composeTestRule.onNodeWithText("Enter UK postcode").performTextInput("1234")


        composeTestRule.onNodeWithText("Search").performClick()

        composeTestRule.onNodeWithText("Invalid postcode format.").assertIsDisplayed()
    }

    @Test
    fun testPostcodeField_input() {
        composeTestRule.onNodeWithTag("postcodeField")
            .performTextInput("SW1A 1AA")

        composeTestRule.onNodeWithTag("postcodeField")
            .assertTextEquals("SW1A 1AA")
    }

    @Test
    fun testSubmitButton_click() {
        composeTestRule.onNodeWithTag("postcodeField")
            .performTextInput("SW1A 1AA")

        composeTestRule.onNodeWithTag("submitButton")
            .performClick()

        // Ensure that the searchRestaurants method was called or snackbar message is shown
        verify { viewModel.searchRestaurants("SW1A 1AA") }
    }

    @After
    fun tearDown() {
        // Clean up mocks
    }
}