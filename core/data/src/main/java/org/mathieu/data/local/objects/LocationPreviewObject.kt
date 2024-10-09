package org.mathieu.data.local.objects

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mathieu.data.remote.responses.LocationPreviewResponse
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
internal class LocationPreviewObject : RealmObject {
    @PrimaryKey
    var id: Int = -1
    var name: String = ""
    var type: String = ""
    var dimension: String = ""
//    var residents: List<Int> = emptyList()
}

internal fun LocationPreviewResponse.toRealmObject(): LocationObject = LocationObject().also { obj ->
    obj.id = id
    obj.name = name
    obj.type = type
    obj.dimension = dimension
}

internal fun LocationPreviewObject.toLocationPreview(): LocationPreview {
    return LocationPreview(
        id = id,
        name = name,
        type = type,
        dimension = dimension
    )
}