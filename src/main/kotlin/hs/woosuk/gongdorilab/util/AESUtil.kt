package hs.woosuk.gongdorilab.util

import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AESUtil {
    private const val ALGO = "AES/CBC/PKCS5Padding"

    fun encrypt(text: String, key: SecretKeySpec, iv: IvParameterSpec): String = try {
        val cipher = Cipher.getInstance(ALGO).apply {
            init(Cipher.ENCRYPT_MODE, key, iv)
        }
        Base64.getEncoder().encodeToString(cipher.doFinal(text.toByteArray()))
    } catch (e: Exception) {
        e.printStackTrace()
        text
    }

    fun decrypt(text: String, key: SecretKeySpec, iv: IvParameterSpec): String = try {
        val cipher = Cipher.getInstance(ALGO).apply {
            init(Cipher.DECRYPT_MODE, key, iv)
        }
        String(cipher.doFinal(Base64.getDecoder().decode(text)))
    } catch (e: Exception) {
        e.printStackTrace()
        text
    }
}