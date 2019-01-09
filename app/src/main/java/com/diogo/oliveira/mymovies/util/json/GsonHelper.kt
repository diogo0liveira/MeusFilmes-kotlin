package com.diogo.oliveira.mymovies.util.json

import com.diogo.oliveira.mymovies.util.network.Status
import com.google.gson.*
import java.lang.reflect.Type

/**
 * Created in 06/08/18 09:46.
 *
 * @author Diogo Oliveira.
 */
class GsonHelper
{
    companion object
    {
        fun build(): Gson
        {
            return GsonBuilder().registerTypeAdapter(Status::class.java, EnumResultStatusAdapter()).create()
        }
    }

    private class EnumResultStatusAdapter : JsonSerializer<Status>, JsonDeserializer<Status>
    {
        override fun serialize(status: Status, type: Type, context: JsonSerializationContext): JsonElement
        {
            return context.serialize(status.value)
        }

        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, type: Type, context: JsonDeserializationContext): Status
        {
            return Status[json.asInt]
        }
    }
}