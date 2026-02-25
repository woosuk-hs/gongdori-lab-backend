package hs.woosuk.gongdorilab.service

import io.github.cdimascio.dotenv.Dotenv
import org.springframework.stereotype.Service
import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

@Service
class CryptoService(
    dotenv: Dotenv,
) {

    private val ALGO = "AES/CBC/PKCS5Padding"
    private val keySpec: SecretKeySpec
    private val secureRandom = SecureRandom()

    init {
        // key.padEnd(16, '0').substring(0, 16).toByteArray()
        val keyBytes = (dotenv["AES_KEY"] ?: error("AES_KEY is Null"))
            .padEnd(16, '0')
            .substring(0, 16)
            .toByteArray()
        keySpec = SecretKeySpec(keyBytes, "AES")
    }

    fun encrypt(text: String): String = runCatching {
        val iv = ByteArray(16).apply { secureRandom.nextBytes(this) }
        val cipher = Cipher.getInstance(ALGO).apply {
            init(Cipher.ENCRYPT_MODE, keySpec, IvParameterSpec(iv))
        }
        (iv + cipher.doFinal(text.toByteArray())).toBase64()
    }.getOrElse { throw RuntimeException("Unable to encrypt data", it) }

    fun decrypt(text: String): String = runCatching {
        val combined = text.fromBase64()
        val iv = combined.copyOfRange(0, 16)
        val encrypted = combined.copyOfRange(16, combined.size)
        val cipher = Cipher.getInstance(ALGO).apply {
            init(Cipher.DECRYPT_MODE, keySpec, IvParameterSpec(iv))
        }
        String(cipher.doFinal(encrypted))
    }.getOrElse { throw RuntimeException("Unable to decrypt data", it) }

    private fun ByteArray.toBase64(): String = Base64.getEncoder().encodeToString(this)
    private fun String.fromBase64(): ByteArray = Base64.getDecoder().decode(this)
}