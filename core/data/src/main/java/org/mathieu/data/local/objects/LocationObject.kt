package org.mathieu.data.local.objects

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Ignore
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mathieu.data.remote.responses.LocationResponse
import org.mathieu.data.repositories.tryOrNull
import org.mathieu.domain.models.location.Location
import org.mathieu.domain.models.location.LocationPreview

/**
 * Represents a location entity stored in the SQLite database via Realm.
 *
 * @property id Unique identifier of the location.
 * @property name Name of the location.
 * @property type The type or category of the location.
 * @property dimension The specific dimension or universe where this location exists.
 * @property residents List of character IDs who reside in this location.
 */
internal class LocationObject : RealmObject {
    @PrimaryKey
    var id: Int = -1
    var name: String = ""
    var type: String = ""
    var dimension: String = ""
    @Ignore
    var residents: List<Int> = emptyList()
}

internal fun LocationResponse.toRealmObject(): LocationObject = LocationObject().also { obj ->
    obj.id = id
    obj.name = name
    obj.type = type
    obj.dimension = dimension
    obj.residents = residents.mapNotNull { url ->
        tryOrNull { url.split("/").last().toInt() }
    }
}

internal fun LocationObject.toLocationPreview(): LocationPreview {
    return LocationPreview(
        id = id,
        name = name,
        type = type,
        dimension = dimension
    )
}

internal fun LocationObject.toModel(): Location {
    return Location(
        id = id,
        name = name,
        type = type,
        dimension = dimension,
        residents = emptyList()
    )
}
