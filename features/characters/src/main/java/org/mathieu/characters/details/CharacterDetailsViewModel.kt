package org.mathieu.characters.details

import android.app.Application
import org.koin.core.component.inject
import org.mathieu.domain.models.location.LocationPreview
import org.mathieu.domain.repositories.CharacterRepository
import org.mathieu.domain.repositories.LocationRepository
import org.mathieu.ui.ViewModel


class CharacterDetailsViewModel(application: Application) : ViewModel<CharacterDetailsState>(
    CharacterDetailsState(), application) {

    private val characterRepository: CharacterRepository by inject()
    private val locationRepository: LocationRepository by inject()


    fun init(characterId: Int) {
        fetchData(
            source = { characterRepository.getCharacter(id = characterId) }
        ) {

            onSuccess {
                updateState { copy(avatarUrl = it.avatarUrl, name = it.name, error = null, locationID = it.location.second) }
            }

            onFailure {
                updateState { copy(error = it.toString()) }
            }

            updateState { copy(isLoading = false) }
        }
    }


    fun setLocationPreview(locationId: Int) {
        fetchData(
            source = { locationRepository.getLocationPreview(locationId) }
        ) {

            onSuccess {
                updateState { copy(locationPreview = it) }
            }

            onFailure {
                updateState { copy(error = it.toString()) }
            }
        }
    }
}


data class CharacterDetailsState(
    val isLoading: Boolean = true,
    val avatarUrl: String = "",
    val name: String = "",
    val error: String? = null,
    val locationPreview: LocationPreview? = null,
    val locationID: Int = 0
)