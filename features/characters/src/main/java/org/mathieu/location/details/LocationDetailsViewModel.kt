package org.mathieu.location.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.mathieu.domain.models.character.Character
import org.mathieu.domain.models.location.Location
import org.mathieu.domain.repositories.LocationRepository

data class LocationDetailsState(
    val location: Location? = null,
    val characters: List<Character> = emptyList()
)

class LocationDetailsViewModel(
    private val locationRepository: LocationRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LocationDetailsState())
    val state: StateFlow<LocationDetailsState> = _state

    fun init(locationId: Int) {
        viewModelScope.launch {
            val location = locationRepository.getLocations(locationId)
            val characters = locationRepository.getCharactersByLocationId(locationId)
            _state.value = LocationDetailsState(location = location, characters = characters)
        }
    }
}