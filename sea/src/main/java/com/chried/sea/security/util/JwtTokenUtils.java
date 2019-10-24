package com.chried.sea.security.util;

import com.alibaba.fastjson.JSON;
import com.chried.core.param.Parameter;
import com.chried.sea.redis.cache.UserCache;
import com.chried.sea.security.model.SelfUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

/**
 * jwt token工具类.
 *
 * @author chried
 */
@Slf4j
public class JwtTokenUtils {

    /**
     * 私钥.
     */
    private static PrivateKey privateKey = null;
    /**
     * 公钥.
     */
    public static PublicKey publicKey = null;

    /**
     * 权限参数头.
     */
    public static final String AUTH_HEADER = "auth";

    /**
     * 设置过期时间(s).
     * 有效期30分钟.
     */
    public static final long EXPIRATION_SECONDS = 1800L;

    static {
        try {
            // 寻找证书地址.
            InputStream inputStream = new PathMatchingResourcePatternResolver().getResource("jwt.jks").getInputStream();
            // java key store 固定常量
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(inputStream, "123456".toCharArray());
            // jwt 为 命令生成整数文件时的别名
            privateKey = (PrivateKey) keyStore.getKey("jwt", "123456".toCharArray());
            publicKey = keyStore.getCertificate("jwt").getPublicKey();
        } catch (Exception e) {
            log.error("JwtTokenUtil初始化公钥私钥出现异常:{}", e.getMessage());
        }
    }

    /**
     * 创建token.
     *
     * @param selfUserDetails 扩展用户.
     * @return token.
     */
    public static String generateToken(SelfUserDetails selfUserDetails) {

        return generateToken(selfUserDetails.toUserCache());
    }

    /**
     * 创建token.
     *
     * @param userCache 缓存用户.
     * @return token.
     */
    public static String generateToken(UserCache userCache) {
        return Jwts.builder()
                .setClaims(null)
                .setSubject(userCache.getAccount())
                .setId(String.valueOf(userCache.getUserId()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_SECONDS * 1000))
                .claim(AUTH_HEADER, JSON.toJSONString(userCache.getAuthorities()))
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
    }

    /**
     * 利用公钥解析token.
     *
     * @param token token.
     * @return token.
     */
    public static Claims parseToken(String token) {
        // 如果带有头，那么去掉头.
        if (token.startsWith(Parameter.JWT_TOKEN_HEADER_PREFIX)) {
            token = token.substring(Parameter.JWT_TOKEN_HEADER_PREFIX.length());
        }
        try {
            return Jwts.parser()
                    .setSigningKey(publicKey)
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            log.error("JwtTokenUtil验证token出现异常:{}", e.getMessage());
            if (e instanceof ExpiredJwtException) {
                throw e;
            }
        }
        return null;
    }

}