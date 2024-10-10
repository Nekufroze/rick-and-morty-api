package org.mathieu.location.details

import android.app.Application
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import org.mathieu.domain.models.character.Character
import org.mathieu.domain.models.location.Location
import org.mathieu.domain.repositories.LocationRepository
import org.mathieu.ui.ViewModel


class LocationDetailsViewModel(
    application: Application) : ViewModel<LocationDetailsViewModel.LocationDetailsState>(
    LocationDetailsState(), application) {
    private val locationRepository: LocationRepository by inject()


    fun init(locationId: Int) {
        viewModelScope.launch {
            val location = locationRepository.getLocations(locationId)
            val characters = locationRepository.getCharactersByLocationId(locationId)
            updateState {
                copy(
                    location = location,
                    characters = characters
                )
        }
    }
}

    data class LocationDetailsState(
        val location: Location? = null,
        val characters: List<Character> = emptyList()
    )
}