package hs.woosuk.gongdorilab.api.neis.controller

import hs.woosuk.gongdorilab.api.neis.service.NeisService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/neis")
class NeisController(
    private val neisService: NeisService
) {

    @GetMapping
    fun index(): String {
        return "Hello World!"
    }

    @GetMapping("/timetable")
    fun timetable(): String {
        return neisService.getTimetable()
    }

}