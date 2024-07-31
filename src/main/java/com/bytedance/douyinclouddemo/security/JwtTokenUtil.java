package com.bytedance.douyinclouddemo.security;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bytedance.douyinclouddemo.mapper.AnchorUserMapper;
import com.bytedance.douyinclouddemo.pojo.AnchorUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    @Autowired
    private AnchorUserMapper anchorUserMapper;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        String token = doGenerateToken(claims, userDetails.getUsername());

        // 更新数据库中的Token字段
        AnchorUser anchorUser = anchorUserMapper.selectOne(new QueryWrapper<AnchorUser>().eq("account", userDetails.getUsername()));
        anchorUser.setToken(token);
        anchorUserMapper.updateById(anchorUser);

        return token;
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        if (username.equals(userDetails.getUsername()) && !isTokenExpired(token)) {
            AnchorUser anchorUser = anchorUserMapper.selectOne(new QueryWrapper<AnchorUser>().eq("account", username));
            return token.equals(anchorUser.getToken()); // 比较Token是否匹配
        }
        return false;
    }

    public AnchorUser getUserFromToken(String token) {
        String username = getUsernameFromToken(token);
        return anchorUserMapper.selectOne(new QueryWrapper<AnchorUser>().eq("account", username));
    }
}
