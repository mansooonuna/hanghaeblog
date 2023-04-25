package com.sparta.hanhaeblog.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

// Simple Logging Facade for Java
// 프로그램 개발 중이나 완료 후 발생할 수 있는 오류에 대해 디버깅하거나 운영 중인 프로그램 상태를 모니터링 하기 위해 필요한 정보(로그)를 기록하기 위해 적용
@Slf4j
@Component                  // Bean 객체로 등록하겠다.
@RequiredArgsConstructor    // 필수값만 인자로 받는 생성자 만들겠다.
public class JwtUtil {

    // 토큰 생성에 필요한 값

    // Header Key 값
    public static final String AUTHORIZATION_HEADER = "Authorization";

    //Token 식별자
    private static final String BEARER_PREFIX = "Bearer ";

    // 토큰 만료시간 60분 * 60초 * 1000밀리세컨드
    private static final long TOKEN_TIME = 60 * 60 * 1000L;

    // application.properties에 있는 secret.key 값을 가져옴
    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;

    // 암호화 알고리즘 HS256을 사용한다.
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;



    // 의존성 주입 후 초기화를 수행한다.
    @PostConstruct
    public void init() {
        // jwt key 값을 Base64를 이용해 문자열을 변환한다.
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // header 토큰을 가져오기
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 토큰 생성
    public String createToken(String username) {
        Date date = new Date();

        return BEARER_PREFIX +
                // jwt 토큰 생성시 호출하는 builder
                Jwts.builder()
                        // 해당 유저의 정보(여기서는 이름)
                        .setSubject(username)
                        // 토큰 만료시간
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                        // 토큰 발행일자
                        .setIssuedAt(date)
                        // 암호화방식
                        .signWith(key, signatureAlgorithm)
                        // 마지막으로 압축하고 서명하기 위해 호출 후 생성
                        .compact();
    }

    // 토큰 검증
    // 토큰을 읽을 때 가장 중요한 것은 암호화된 서명을 검증하기 위해 사용하는 지정된 키
    // 서명 검증에 실패? No 신뢰, 폐기 ㄱ ㄱ
    public boolean validateToken(String token) {
        try {
            // JWT 파싱하는 코드
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            // parserBuilder() : parserBuilder 인스턴스 생성
            // setSingingKey(key) : parserBuiler 인스턴스에 key를 설정, 이 key 는 JWT 생성 때 사용된 키랑 같아야함
            // build() : JwtParser 인스턴스 생성
            // parseClaimsJws(token) : JWT 토큰 파싱함 파싱한 사용자 정보를 Claims 객체에 담아서 리턴
            return true; // 예외가 발생하지 않았으면 토큰은 유효함.


            // 그 밖에 유요하지 않은 토큰 예외 처리
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }


    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
    // 여기서 반환타입 Claims 인데, JWT 가 Claim 기반 토큰이라서 사용하는 것
    // Claim : 사용자에 대한 프로퍼티 / 속성 , 토큰 자체가 정보임
    // parseClaimsJws(token) 에서 Jws<Claims> 객체가 반환이되고 getBody()에 의해 Jws 객체의 본문인 Claims 가 반환
    //

}