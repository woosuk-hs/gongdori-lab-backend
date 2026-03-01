package hs.woosuk.gongdorilab.test.controller

import hs.woosuk.gongdorilab.common.service.CryptoService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MyController(val cryptoService: CryptoService) {

    @GetMapping("/test1")
    fun test(): String {
        val original = "Hello Spring!"
        val encrypted = cryptoService.encrypt(original)
        val decrypted = cryptoService.decrypt(encrypted)

        return "원본: $original \n암호화: $encrypted \n복호화: $decrypted"
    }
}