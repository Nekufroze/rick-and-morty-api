package org.mathieu.data.remote.responses

import kotlinx.serialization.Serializable

/**
 * Represents detailed information about a location, typically received from an API response.
 *
 * @property id The unique identifier for the location.
 * @property name The name of the location.
 * @property type The type or category of the location.
 * @property dimension The specific dimension in which the location exists.
 */
@Serializable
internal data class LocationPreviewResponse(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
)