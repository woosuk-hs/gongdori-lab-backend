package hs.woosuk.gongdorilab.api.neis.service

import hs.woosuk.gongdorilab.api.neis.properties.NeisProperties
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import org.springframework.web.util.UriComponentsBuilder

@Service
class NeisService(
    private val webClient: WebClient,
    private val neisProperties: NeisProperties
) {

    private fun getUri(path: String): String =
        UriComponentsBuilder.fromUriString(neisProperties.baseUri)
            .pathSegment(path.trimStart('/'))
            .queryParam("KEY", neisProperties.key)
            .queryParam("Type", "json")
            .queryParam("ATPT_OFCDC_SC_CODE", "P10")
            .queryParam("SD_SCHUL_CODE", "8321094")
            .build()
            .toUriString()


    fun getTimetable(): String =
        webClient.get()
            .uri(getUri("/hisTimetable"))
            .retrieve()
            .bodyToMono<String>()
            .block() ?: ""
}