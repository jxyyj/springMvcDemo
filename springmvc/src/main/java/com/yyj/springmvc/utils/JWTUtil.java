package com.yyj.springmvc.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 *
 * @author yan yinjie
 * @date 2026/03/11
 */
public class JWTUtil {

    // 密钥
    private static final String SECRET_KEY = "your_secret_key";

    // 过期时间（毫秒）
    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000; // 24小时

    /**
     * 生成token
     * @param username 用户名
     * @return token
     */
    public static String generateToken(String username) {
        // 创建声明
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("created", new Date());

        // 生成token
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /**
     * 解析token
     * @param token token
     * @return 声明
     */
    public static Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 从token中获取用户名
     * @param token token
     * @return 用户名
     */
    public static String getUsernameFromToken(String token) {
        Claims claims = parseToken(token);
        return claims != null ? claims.get("username", String.class) : null;
    }

    /**
     * 检查token是否过期
     * @param token token
     * @return 是否过期
     */
    public static boolean isTokenExpired(String token) {
        Claims claims = parseToken(token);
        if (claims == null) {
            return true;
        }
        Date expiration = claims.getExpiration();
        return expiration.before(new Date());
    }

    /**
     * 验证token
     * @param token token
     * @param username 用户名
     * @return 是否有效
     */
    public static boolean validateToken(String token, String username) {
        String tokenUsername = getUsernameFromToken(token);
        return username.equals(tokenUsername) && !isTokenExpired(token);
    }
}