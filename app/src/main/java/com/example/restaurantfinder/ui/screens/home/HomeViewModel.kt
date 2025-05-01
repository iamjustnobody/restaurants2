package com.example.restaurantfinder.ui.screens.home



import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantfinder.data.model.FilterOptions
import kotlinx.coroutines.launch
import com.example.restaurantfinder.data.repository.RestaurantRepository
import com.example.restaurantfinder.data.model.Restaurant
import com.example.restaurantfinder.data.network.JustEatApi
import com.example.restaurantfinder.data.repository.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
//class HomeViewModel(private val restaurantRepository: RestaurantRepository) : ViewModel() {
//class HomeViewModel : ViewModel() {
//class HomeViewModel (application: Application) : AndroidViewModel(application) {
class HomeViewModel @Inject constructor(
    private val repository: RestaurantRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

//    private val _isFilterDialogVisible = MutableStateFlow(false)
//    val isFilterDialogVisible: StateFlow<HomeUiState> = _uiState.asStateFlow()
//
//    private val _filterOptions = MutableStateFlow(FilterOptions())
//    val filterOptions: StateFlow<FilterOptions> = _filterOptions
//
//    private val _filteredRestaurants = MutableStateFlow<List<Restaurant>>(emptyList())
//    val filteredRestaurants: StateFlow<List<Restaurant>> = _filteredRestaurants

    val updatedRestaurants: StateFlow<List<Restaurant>> =
        repository.getUpdatedRestaurants()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

//    private val repository = RestaurantRepository(application)//RestaurantRepository(JustEatApi.service)
    fun searchRestaurants(postcode: String, initial: Boolean = true, limit: Int = 10, offset: Int = 0) {
//        init
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    snackbarMessage = if (initial) "Searching for restaurants at: $postcode" else null,
                    errorMessage = null,
                    isLoading = initial,
                    isLoadingMore = !initial,
                    showSuccessDialog = false
                )
            }

//            try {
//                val restaurants = repository.getRestaurantsByPostcode(postcode)
//
//                _uiState.update {
//                    it.copy(
//                        restaurants = if (initial) restaurants else it.restaurants + restaurants,
//                        filteredRestaurants = restaurants, // or apply filters if needed
//                        snackbarMessage = when {
//                            initial && restaurants.isEmpty() -> "No restaurants found."
//                            !initial && restaurants.isEmpty() -> "No more restaurants found."
//                            else -> "Restaurants loaded successfully 🎉"
//                        },
//                        isLoading = false,
//                        isLoadingMore = false,
//                        showSuccessDialog = initial,
//                        noMoreItems = restaurants.isEmpty()
//                    )
//                }
//
//            } catch (e: Exception) {
//                _uiState.update {
//                    it.copy(
//                        isLoading = false,
//                        isLoadingMore = false,
//                        errorMessage = e.localizedMessage ?: "Unknown error",
//                        snackbarMessage = "Error: ${e.localizedMessage ?: "Unknown error"}"
//                    )
//                }
//            }

            when (val result = repository.getRestaurantsByPostcode(postcode)) {
                is ApiResult.Success -> {
                    val cached: List<Restaurant> = repository.getCachedRestaurants(limit, offset)
                    val restaurantList: List<Restaurant> = result.data
                    _uiState.update {
                        it.copy(
                            restaurants = if (initial) restaurantList else it.restaurants + restaurantList,
                            filteredRestaurants = restaurantList, // or apply filters if needed
                            snackbarMessage = when {
                                initial && restaurantList.isEmpty() -> "No restaurants found."
                                !initial && restaurantList.isEmpty() -> "No more restaurants found."
                                else -> "Restaurants loaded successfully 🎉"
                            },
                            isLoading = false,
                            isLoadingMore = false,
                            showSuccessDialog = initial,
                            noMoreItems = restaurantList.isEmpty()
                        )
                    }
                }

                is ApiResult.Error -> {
                    val errorMessage = result.message
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isLoadingMore = false,
                            errorMessage = errorMessage,
                            snackbarMessage = "Error: ${result.throwable?.localizedMessage ?: "Unknown error"}"

                        )
                    }
                }
            }

        }
    }

    fun fetchInitialRestaurants(postcode: String) {
        searchRestaurants(postcode, initial = true)
    }

    fun loadMoreRestaurants(postcode: String) {
        searchRestaurants(postcode, initial = false)
    }

    fun setSnackbarMessage(message: String?) {
        _uiState.update { it.copy(snackbarMessage = message) }
    }

    fun dismissSuccessDialog() {
        _uiState.update { it.copy(showSuccessDialog = false) }
    }


    fun applyFilters(restaurants: List<Restaurant>) {
        val filters = _uiState.value.filterOptions
        val filtered = restaurants.filter { restaurant ->
            (!filters.isNew || restaurant.isNew == true) &&
                    (!filters.isCollection || restaurant.isCollection == true) &&
                    (!filters.isDelivery || restaurant.isDelivery == true) &&
                    (!filters.isOpenNowForCollection || restaurant.isOpenNowForCollection == true) &&
                    (!filters.isOpenNowForDelivery || restaurant.isOpenNowForDelivery == true) &&
                    (!filters.isOpenNowForPreorder || restaurant.isOpenNowForPreorder == true) &&
                    (!filters.deliveryIsOpen || restaurant.availability?.delivery?.isOpen == true) &&
                    (!filters.deliveryCanPreOrder || restaurant.availability?.delivery?.canPreOrder == true)
        }

        _uiState.update { it.copy(filteredRestaurants = filtered) }
    }

    fun updateFilterDialogVisible(visible: Boolean) {
        _uiState.update { it.copy(isFilterDialogVisible = visible) }
    }

    fun updateFilterOptions(options: FilterOptions) {
        _uiState.update { it.copy(filterOptions = options) }
    }

    fun setRestaurants(restaurants: List<Restaurant>) {
        _uiState.update {
            it.copy(
                restaurants = restaurants,
                filteredRestaurants = restaurants
            )
        }
    }

    fun sortRestaurants(option: SortingOption) {
        val sorted = when (option) {
            SortingOption.NAME -> _uiState.value.filteredRestaurants.sortedBy { it.name }
            SortingOption.RATING -> _uiState.value.filteredRestaurants.sortedByDescending { it.rating?.starRating }
            SortingOption.CUISINE -> _uiState.value.filteredRestaurants.sortedBy {
                it.cuisines.joinToString(", ") { cuisine -> cuisine.name }
            }
            SortingOption.DEFAULT -> _uiState.value.filteredRestaurants
        }

        _uiState.update {
            it.copy(filteredRestaurants = sorted, sortingOption = option)
        }
    }
}

