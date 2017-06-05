package com.rainbow.commons

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import jodd.datetime.JDateTime
import jodd.util.Base64
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

/**
 * Created by rainbow on 2017/6/5.
 *一事专注，便是动人；一生坚守，便是深邃！
 */
open class JwtUtils {

    fun createKey(): SecretKey {
        val key = Base64.decode("")

        return SecretKeySpec(key, 0, key.size, "AES")
    }

    fun createJWT(audience: String, claims: MutableMap<String, Any?>, subject: String = "auth", ttl: Int = -1): String {
        val time = JDateTime()

        //有效期
        val export = time.clone().addMinute(if (ttl > 0) ttl else 5).convertToDate()

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("")
                .setIssuedAt(time.convertToDate())
                .setSubject(subject)
                .setExpiration(export)
                .setAudience(audience)
                .signWith(SignatureAlgorithm.ES512, createKey())
                .compact()
    }

    fun parseJWT(jwt: String) = Jwts.parser().setSigningKey(createKey()).parseClaimsJwt(jwt).body

}