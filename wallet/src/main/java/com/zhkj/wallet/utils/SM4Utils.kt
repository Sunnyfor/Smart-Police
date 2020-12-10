package com.zhkj.wallet.utils

import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.util.encoders.Base64
import java.security.Security
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec


/**
 * Desc
 * Author ZhangYe
 * Mail zhangye98@foxmail.com
 * Date 2020/12/9 16:32
 */
class SM4Utils {
    private val algorithm = "SM4"
    private val password = "bjzhkj2020ytzhjb"

    // PKCS5Padding  NoPadding 补位规则，PKCS5Padding缺位补0，NoPadding不补
    private val transformation = "SM4/ECB/PKCS5Padding"

    init {
        Security.addProvider(BouncyCastleProvider())
    }

    private fun generateEcbCipher(mode: Int, keyData: ByteArray): Cipher {
        val cipher = Cipher.getInstance(transformation, BouncyCastleProvider())
        val sm4Key = SecretKeySpec(keyData, algorithm)
        cipher.init(mode, sm4Key)
        return cipher
    }

    fun encrypt(content: String): String {
        var result = ""
        generateEcbCipher(Cipher.ENCRYPT_MODE, password.toByteArray()).let {
            result = String(Base64.encode(it.doFinal(content.toByteArray())))
        }
        return result
    }

    fun decrypt(content: String): String {
        var result = ""
        generateEcbCipher(Cipher.DECRYPT_MODE, password.toByteArray()).let {
            result = String(it.doFinal(Base64.decode(content.toByteArray())))
        }
        return result
    }
}