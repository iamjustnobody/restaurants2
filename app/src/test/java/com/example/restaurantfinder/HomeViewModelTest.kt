package com.example.restaurantfinder

import androidx.lifecycle.viewModelScope
import com.example.restaurantfinder.data.model.FilterOptions
import com.example.restaurantfinder.data.model.Rating
import com.example.restaurantfinder.data.model.Restaurant
import com.example.restaurantfinder.data.repository.RestaurantRepository
import com.example.restaurantfinder.ui.screens.home.HomeViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import javax.inject.Inject

@RunWith(JUnit4::class)
//@HiltAndroidTest
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel
//    private val repository: RestaurantRepository = mockk()

    private lateinit var repository: RestaurantRepository
    private val dispatcher = StandardTestDispatcher()

//    @Inject
//    lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
//        hiltRule.inject()
        repository = mockk()
        viewModel = HomeViewModel(repository)
//        viewModel = HomeViewModel(repository).apply {
//            // Provide a test coroutine scope for viewModel
//            viewModelScope = CoroutineScope(dispatcher)
//        }
    }

    @Test
    fun testSearchRestaurants_Success() = runBlocking {

        val mockRestaurants = listOf(
            Restaurant(name = "Restaurant 1", rating = Rating(starRating = 4.5)),
            Restaurant(name = "Restaurant 2", rating = Rating(starRating = 4.0))
        )
        coEvery { repository.getRestaurantsByPostcode(any()) } returns mockRestaurants


        viewModel.fetchInitialRestaurants("SW1A 1AA")


        val state = viewModel.uiState.value
        assert(state.isLoading == false)
        assert(state.restaurants.size == 2)
    }

    @Test
    fun testSearchRestaurants_success() = runBlocking {
        // mock repository to return a successful response
        coEvery { repository.getRestaurantsByPostcode(any()) } returns listOf(Restaurant())

        viewModel.searchRestaurants("SW1A 1AA") // Example postcode

        // check if the state is updated correctly
        assertEquals(true, viewModel.uiState.value.isLoading)
        assertEquals("Restaurants loaded successfully 🎉", viewModel.uiState.value.snackbarMessage)
    }

    @Test
    fun testSearchRestaurants_Failure() = runBlocking {

        coEvery { repository.getRestaurantsByPostcode(any()) } throws Exception("Network error")


        viewModel.fetchInitialRestaurants("SW1A 1AA")

        // verify the state is updated correctly
        val state = viewModel.uiState.value
        assert(state.isLoading == false)
        assert(state.errorMessage == "Network error")
    }

    @Test
    fun testSearchRestaurants_error() = runBlocking {
        // throw an exception
        coEvery { repository.getRestaurantsByPostcode(any()) } throws Exception("Error fetching restaurants")

        viewModel.searchRestaurants("SW1A 1AA")


        assertEquals("Error: Error fetching restaurants", viewModel.uiState.value.snackbarMessage)
        assertEquals("Unknown error", viewModel.uiState.value.errorMessage)
    }

    @Test
    fun testApplyFilters() {

        val mockRestaurants = listOf(
            Restaurant(name = "Restaurant 1", isNew = true),
            Restaurant(name = "Restaurant 2", isNew = false)
        )
        viewModel.setRestaurants(mockRestaurants)


        val filterOptions = FilterOptions(isNew = true)
        viewModel.updateFilterOptions(filterOptions)
        viewModel.applyFilters()


        val filteredRestaurants = viewModel.uiState.value.filteredRestaurants
        assert(filteredRestaurants.size == 1)
        assert(filteredRestaurants[0].name == "Restaurant 1")
    }

    @After
    fun tearDown() {
//        dispatcher.cleanupTestCoroutines()
    }
}