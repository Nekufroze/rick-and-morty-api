package org.mathieu.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode
import org.mathieu.data.remote.responses.CharacterResponse
import org.mathieu.data.remote.responses.LocationResponse
import org.mathieu.data.remote.responses.PaginatedResponse

internal class LocationApi(private val client: HttpClient) {

    /**
     * Fetches a list of Location from the API.
     *
     * If the page parameter is not provided, it defaults to fetching the first page.
     *
     * @param page The page number to fetch. If null, the first page is fetched by default.
     * @return A paginated response containing a list of [LocationResponse] for the specified page.
     * @throws HttpException if the request fails or if the status code is not [HttpStatusCode.OK].
     */
    suspend fun getLocations(page: Int?): PaginatedResponse<LocationResponse> = client
        .get("location/") {
            if (page != null)
                url {
                    parameter("page", page)
                }
        }
        .accept(HttpStatusCode.OK)
        .body()

    /**
     * Fetches the details of a character with the given ID from the service.
     *
     * @param id The unique identifier of the Location to retrieve.
     * @return The [LocationResponse] representing the details of the Location.
     * @throws HttpException if the request fails or if the status code is not [HttpStatusCode.OK].
     */
    suspend fun getLocations(id: Int): LocationResponse? = client
        .get("location/$id")
        .accept(HttpStatusCode.OK)
        .body()

    /**
     * Fetches a character by its URL.
     * @param url The URL of the character to fetch.
     * @return The [CharacterResponse] representing the character details.
     * @throws HttpException if the request fails or if the status code is not [HttpStatusCode.OK].
     * @throws IllegalArgumentException if the URL is empty or invalid.
     */
    suspend fun getCharacterByUrl(url: String): CharacterResponse = client
        .get(url)
        .accept(HttpStatusCode.OK)
        .body()
}
