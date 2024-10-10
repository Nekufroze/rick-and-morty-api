package org.mathieu.data.repositories

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.mathieu.data.local.LocationLocal
import org.mathieu.data.local.objects.LocationObject
import org.mathieu.data.local.objects.toLocationPreview
import org.mathieu.data.local.objects.toModel
import org.mathieu.data.local.objects.toRealmObject
import org.mathieu.data.remote.CharacterApi
import org.mathieu.data.remote.LocationApi
import org.mathieu.data.remote.responses.LocationResponse
import org.mathieu.domain.models.character.Character
import org.mathieu.domain.models.location.Location
import org.mathieu.domain.models.location.LocationPreview
import org.mathieu.domain.repositories.LocationRepository

private const val LOCATION_PREFS = "location_repository_preferences"
private val nextPage = intPreferencesKey("next_location_page_to_load")

private val Context.dataStore by preferencesDataStore(
    name = LOCATION_PREFS
)

internal class LocationRepositoryImpl(
    private val context: Context,
    private val locationApi: LocationApi,
    private val characterApi: CharacterApi,
    private val locationLocal: LocationLocal
) : LocationRepository {
    override suspend fun getLocations(): Flow<List<Location>> =
        locationLocal
            .getLocations()
            .mapElement(transform = LocationObject::toModel)
            .also { if (it.first().isEmpty()) fetchNext() }


    /**
     * Fetches the next batch of locations and saves them to local storage.
     *
     * This function works as follows:
     * 1. Reads the next page number from the data store.
     * 2. If there's a valid next page (i.e., page is not -1), it fetches locations from the API for that page.
     * 3. Extracts the next page number from the API response and updates the data store with it.
     * 4. Transforms the fetched location data into their corresponding realm objects.
     * 5. Saves the transformed realm objects to the local database.
     *
     * Note: If the `next` attribute from the API response is null or missing, the page number is set to -1, indicating there's no more data to fetch.
     */
    private suspend fun fetchNext() {

        val page = context.dataStore.data.map { prefs -> prefs[nextPage] }.first()

        if (page != -1) {

            val response = locationApi.getLocations(page)

            val nextPageToLoad = response.info.next?.split("?page=")?.last()?.toInt() ?: -1

            context.dataStore.edit { prefs -> prefs[nextPage] = nextPageToLoad }

            val objects = response.results.map(transform = LocationResponse::toRealmObject)

            locationLocal.saveLocations(objects)
        }

    }

    override suspend fun loadMore() = fetchNext()


    /**
     * Retrieves the location with the specified ID.
     *
     * The function follows these steps:
     * 1. Tries to fetch the location from the local storage.
     * 2. If not found locally, it fetches the location from the API.
     * 3. Upon successful API retrieval, it saves the location to local storage.
     * 4. If the location is still not found, it throws an exception.
     *
     * @param id The unique identifier of the location to retrieve.
     * @return The [Location] object representing the location details.
     * @throws Exception If the location cannot be found both locally and via the API.
     */
    override suspend fun getLocations(id: Int): Location = coroutineScope {
        locationLocal.getLocation(id)?.toModel()
            ?: locationApi.getLocations(id)?.toRealmObject()?.let { response ->
                locationLocal.insert(response)
                response.toModel()
            } ?: throw Exception("Location not found")
    }

    override suspend fun getLocationPreview(id: Int): LocationPreview = coroutineScope {
        locationLocal.getLocation(id)?.toLocationPreview()
            ?: locationApi.getLocations(id)?.toRealmObject()?.toLocationPreview()
            ?: throw Exception("Location not found")
    }

    override suspend fun getCharactersByLocationId(locationId: Int): List<Character> = coroutineScope {
        val location = getLocations(locationId)
        val charactersID = location.residents.map { it.toString().split("/").last().toInt() }

        charactersID.map { id ->
            async {
                characterApi.getCharacter(id)?.toRealmObject()?.toModel()
            }
        }.map { it.await() }

        (location.residents)


    }
}



