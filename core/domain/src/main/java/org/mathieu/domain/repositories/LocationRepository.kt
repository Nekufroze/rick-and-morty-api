package org.mathieu.domain.repositories

import kotlinx.coroutines.flow.Flow
import org.mathieu.domain.models.character.Character
import org.mathieu.domain.models.location.Location
import org.mathieu.domain.models.location.LocationPreview

interface LocationRepository {
    /**
     * Fetches a list of Locations from the data source. The function streams the results
     * as a [Flow] of [List] of [Location] objects.
     *
     * @return A flow emitting a list of Locations.
     */
    suspend fun getLocations(): Flow<List<Location>>

    /**
     * Loads more Location from the data source, usually used for pagination purposes.
     * This function typically fetches the next set of characters and appends them to the existing list.
     */
    suspend fun loadMore()

    /**
     * Fetches the details of a specific Location based on the provided ID.
     *
     * @param id The unique identifier of the Location to be fetched.
     * @return Details of the specified Location.
     */
    suspend fun getLocations(id: Int): Location

    suspend fun getLocationPreview(id: Int): LocationPreview

    /**
     * Fetches a list of Characters that are associated with the specified Location.
     *
     * @param locationId The unique identifier of the Location.
     * @return A list of Characters associated with the specified Location.
     */
    suspend fun getCharactersByLocationId(locationId: Int): List<Character>

}