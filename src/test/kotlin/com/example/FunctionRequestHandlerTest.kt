package com.example

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.example.request.Addition
import io.micronaut.json.JsonMapper
import io.micronaut.serde.annotation.SerdeImport
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

@SerdeImport(Addition::class)
class FunctionRequestHandlerTest {

    @Test
    fun testHandler() {
        val handler = FunctionRequestHandler()
        val request = APIGatewayProxyRequestEvent()
        val addition = Addition(1, 3)
        val objectMapper: JsonMapper = JsonMapper.createDefault()
        request.body = objectMapper.writeValueAsString(addition)
        request.httpMethod = "GET"
        request.path = "/"
        println(request)
        val response = handler.execute(request)
        assertEquals(200, response.statusCode.toInt())
        assertEquals("{\"message\":\"Result: 4\"}", response.body)
        println(response.body)
        handler.close()
    }
}
