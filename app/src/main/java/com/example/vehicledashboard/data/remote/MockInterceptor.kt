package com.example.vehicledashboard.data.remote

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.util.UUID

class MockInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val path = request.url.encodedPath

        val body = when {
            path.endsWith("/vehicles") -> buildVehicleListJson()
            path.contains("/vehicles/") -> {
                val id = path.substringAfterLast("/")
                buildVehicleDetailJson(id)
            }
            else -> """{"error":"not found"}"""
        }

        return Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("OK")
            .body(body.toResponseBody("application/json".toMediaType()))
            .addHeader("content-type", "application/json")
            .build()
    }

    private fun buildVehicleListJson(): String {
        val ids = listOf("v1", "v2", "v3", "v4")
        val names = listOf(
            "Falcon" to "Model S",
            "Ranger" to "Model X",
            "Bolt" to "Model 3",
            "Nomad" to "Model Y"
        )
        return buildString {
            append("[")
            ids.forEachIndexed { index, id ->
                val (name, model) = names[index]
                append(
                    """
                    {
                      "id":"$id",
                      "name":"$name",
                      "model":"$model",
                      "battery":${20 + index * 15},
                      "range_km":${120.0 + index * 40},
                      "speed_kmh":${30.0 + index * 5},
                      "odometer_km":${1500.0 + index * 320},
                      "online":${index % 2 == 0},
                      "last_updated":${System.currentTimeMillis() - index * 60000}
                    }
                    """.trimIndent()
                )
                if (index < ids.size - 1) append(",")
            }
            append("]")
        }
    }

    private fun buildVehicleDetailJson(id: String): String {
        val known = setOf("v1", "v2", "v3", "v4")
        if (id !in known) {
            return """{"error":"vehicle not found"}"""
        }
        val index = id.removePrefix("v").toInt() - 1
        val names = listOf(
            "Falcon" to "Model S",
            "Ranger" to "Model X",
            "Bolt" to "Model 3",
            "Nomad" to "Model Y"
        )
        val (name, model) = names[index]
        return """
        {
          "id":"$id",
          "name":"$name",
          "model":"$model",
          "battery":${20 + index * 15},
          "range_km":${120.0 + index * 40},
          "speed_kmh":${30.0 + index * 5},
          "odometer_km":${1500.0 + index * 320},
          "online":${index % 2 == 0},
          "last_updated":${System.currentTimeMillis() - index * 60000}
        }
        """.trimIndent()
    }

    @Suppress("unused")
    private fun randomId() = UUID.randomUUID().toString()
}