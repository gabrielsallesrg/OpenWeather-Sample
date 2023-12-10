package com.gsrg.data.common.remote.adapter

import android.content.Context
import android.util.Log
import co.infinum.retromock.Retromock
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.gsrg.data.common.MockApiProvider
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.lang.reflect.Type
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class RetrofitAdapterImpl @Inject constructor(
    private val mockApiProvider: MockApiProvider,
    private val context: Context,
) : RetrofitAdapter {

    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(OffsetDateTime::class.java, OffsetDateTimeDeserializer())
        .create()

    override fun <T> getRetrofitInterface(api: Class<T>): T {
        val retrofitClient = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return if (mockApiProvider.useMockApi()) {
            Retromock.Builder()
                .retrofit(retrofitClient)
                .defaultBodyFactory(context.assets::open)
                .build()
                .create(api)
        } else {
            retrofitClient.create(api)
        }
    }
}

private class OffsetDateTimeDeserializer: JsonDeserializer<OffsetDateTime>, JsonSerializer<OffsetDateTime> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): OffsetDateTime {
        return try {
            val isoDateTime = json?.asString?.convertToIso() ?: ""
            val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
            OffsetDateTime.parse(isoDateTime, formatter)
        } catch (e: Exception) {
            Log.e(this.javaClass.simpleName, e.stackTraceToString())
            OffsetDateTime.now()
        }
    }

    override fun serialize(
        src: OffsetDateTime?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(src))
    }

    private fun String.convertToIso(): String {
        val basicFormatPattern = Regex(".{10} .{8}")

        return if (basicFormatPattern.matches(this)) {
            val datePart = this.substring(0, 10)
            val timePart = this.substring(11)
            "${datePart}T${timePart}Z"
        } else {
            this
        }
    }
}
