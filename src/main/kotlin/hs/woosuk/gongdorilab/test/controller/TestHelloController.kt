package hs.woosuk.gongdorilab.test.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.Date

@RestController
@RequestMapping("/api/test")
class TestHelloController {

    @GetMapping("/hello")
    fun hello(): String = "Hello World!"

    @GetMapping("/hello/{name}")
    fun hello(
        @PathVariable name: String,
        @RequestParam age: Int // ? 단위
    ): String = "Hello $name! I'm $age"

    @GetMapping("/time")
    fun time(): Date {
        return Date()
    }

    @GetMapping("ahello")
    fun ahello(): ResponseEntity<String> = ResponseEntity("Hello World!", HttpStatus.OK)
}