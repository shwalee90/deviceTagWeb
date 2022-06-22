package com.auxil.middle.security;

import com.auxil.middle.dto.AuthDTO;
import com.auxil.middle.service.SecurityMemberService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;


@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Lazy
    private final SecurityMemberService securityMemberService;

    private  String secretKey = "secretkey";

    // 토큰 유효시간 30분
    @Lazy
    private final long accessExpiredTime =  60 * 60 * 1 * 60 * 1000L;

    // 3분 refresh
    @Lazy
    private final long refreshExpireTime =   60 * 60 * 3 * 60 * 2000L ;



    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT 토큰 생성
    public String createAccessToken( AuthDTO.LOGINDTO logindto) {

        Map<String,Object> header = new HashMap<>();
        header.put("type" , "token");
        Map<String,Object> payloads = new HashMap<>();
        payloads.put("userName" ,  logindto.getName());

        Date expiration = new Date();
        expiration.setTime(expiration.getTime() + accessExpiredTime);


        return Jwts.builder()
                .setHeader(header)
                .setClaims(payloads) // 정보 저장
                .setSubject("user")
                .setExpiration(expiration) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();
    }


    public Map<String,String> createRefreshToken (AuthDTO.LOGINDTO logindto){

        Map<String,Object> header = new HashMap<>();
        header.put("type" , "token");
        Map<String,Object> payloads = new HashMap<>();
        payloads.put("userName" ,  logindto.getName());

        Date expiration = new Date();
        expiration.setTime(expiration.getTime() + refreshExpireTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.ENGLISH);
        String refreshTokenExpAt = sdf.format(expiration);


        String jwt = Jwts.builder()
                .setHeader(header)
                .setClaims(payloads) // 정보 저장
                .setSubject("user")
                .setExpiration(expiration) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();

        Map<String,String> result = new HashMap<>();
        result.put("refreshToken" ,  jwt);
        result.put("refreshTokenExpAt" , refreshTokenExpAt);
        return result;

    }


    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = securityMemberService.loadUserByUsername(this.getUserInfo(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserInfo(String token) {

         String a = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().toString();

         System.out.println(a);

        return (String)Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("userName");
    }

    // Request의 Header에서 token 값을 가져옵니다. "X-AUTH-TOKEN" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {


        return request.getHeader("token");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(ServletRequest request , String authToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (MalformedJwtException e) {
            request.setAttribute("exeption" , "MalformedJwtException");
        }catch (ExpiredJwtException e) {
            request.setAttribute("exeption" , "ExpiredJwtException");
        }
        catch (UnsupportedJwtException e) {
            request.setAttribute("exeption" , "UnsupportedJwtException");
        }
        catch (IllegalArgumentException e) {
            request.setAttribute("exeption" , "IllegalArgumentException");
        }
        return false;
    }
}