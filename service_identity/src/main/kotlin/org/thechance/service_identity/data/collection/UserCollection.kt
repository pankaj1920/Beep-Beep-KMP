package org.thechance.service_identity.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class UserCollection(
    @SerialName("_id")
    @BsonId
    @Contextual
    val id: ObjectId = ObjectId(),
    @SerialName("full_name")
    val fullName: String?,
    @SerialName("user_name")
    val username: String?,
    @SerialName("password")
    val password: String?,
    @SerialName("is_deleted")
    val isDeleted: Boolean = false,
)

