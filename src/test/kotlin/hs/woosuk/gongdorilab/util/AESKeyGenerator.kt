package hs.woosuk.gongdorilab.util

import org.junit.jupiter.api.Test
import java.security.SecureRandom
import java.util.Base64

class AESKeyGenerator {

    @Test
    fun generateAndDecodeAESKey() {
        val key = generateAESKey()
        println("AESKey: $key") // Base64
    }

    fun generateAESKey(length: Int = 16): String =
        ByteArray(length).also { SecureRandom().nextBytes(it) }
            .let { Base64.getEncoder().withoutPadding().encodeToString(it) }
}