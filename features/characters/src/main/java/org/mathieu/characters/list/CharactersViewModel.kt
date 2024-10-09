package org.mathieu.characters.list

import android.app.Application
import org.koin.core.component.inject
import org.mathieu.domain.models.character.Character
import org.mathieu.domain.repositories.CharacterRepository
import org.mathieu.domain.repositories.LocationRepository
import org.mathieu.ui.Destination
import org.mathieu.ui.ViewModel

sealed interface CharactersAction {
    data class SelectedCharacter(val character: Character):
        CharactersAction
}

class CharactersViewModel(application: Application) : ViewModel<CharactersState>(
    CharactersState(), application) {

    private val characterRepository: CharacterRepository by inject()
    private val locationRepository: LocationRepository by inject()

    init {

        collectData(
            source = { characterRepository.getCharacters()}
        ) {

            onSuccess {
                updateState { copy(characters = it, error = null) }
            }

            onFailure {
                updateState { copy(characters = emptyList(), error = it.toString()) }
            }

            updateState { copy(isLoading = false) }
        }

//        setLocationPreview(characters = state.value.characters)

    }

//    private fun setLocationPreview(characters: List<Character>) {
//        viewModelScope.launch {
//            characters.forEach { character ->
//                val locationPreview = locationRepository.getLocationPreview(character.location.second)
//                character.locationPreview = locationPreview
//            }
//            updateState { copy(characters = characters) }
//        }
//    }

    fun handleAction(action: CharactersAction) {
        when(action) {
            is CharactersAction.SelectedCharacter -> selectedCharacter(action.character)
        }
    }


    private fun selectedCharacter(character: Character) =
        sendEvent(Destination.CharacterDetails(character.id.toString()))

}


data class CharactersState(
    val isLoading: Boolean = true,
    val characters: List<Character> = emptyList(),
    val error: String? = null
)