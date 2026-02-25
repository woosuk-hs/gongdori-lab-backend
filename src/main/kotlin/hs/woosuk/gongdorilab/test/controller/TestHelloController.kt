package hs.woosuk.gongdorilab.test.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestHelloController {

    @GetMapping("/hello")
    fun hello(): String = "Hello World!"

    @GetMapping("/hello/{name}")
    fun hello(
        @PathVariable name: String,
        @RequestParam age: Int // ? 단위
    ): String = "Hello $name! I'm $age"
}