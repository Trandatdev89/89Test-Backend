package com.project01.quiz.utils;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.util.Base64;
import com.nimbusds.jwt.SignedJWT;
import com.project01.quiz.entity.UserEntity;
import com.project01.quiz.exception.CustomException.AppException;
import com.project01.quiz.exception.CustomException.ErrorCode;
import com.project01.quiz.repository.InvalidateTokenRepository;
import com.project01.quiz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class SecurityUtil {

    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Autowired
    private InvalidateTokenRepository invalidateTokenRepository;

    @Autowired
    @Lazy
    private JwtEncoder jwtEncoder;

    public static final MacAlgorithm MAC_ALGORITHM=MacAlgorithm.HS256;

    public String generateToken(UserEntity user){
        JwsHeader jwsHeader=JwsHeader.with(MAC_ALGORITHM).build();

        Instant now=Instant.now();
        JwtClaimsSet jwtClaimsSet=JwtClaimsSet.builder()
                .subject((user.getId()).toString())
                .issuedAt(now)
                .expiresAt(now.plus(expiration, ChronoUnit.SECONDS))
                .claim("scope",getAuthority((List<GrantedAuthority>)user.getAuthorities()))
                .id(UUID.randomUUID().toString())
                .build();
        String token=jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader,jwtClaimsSet)).getTokenValue();
        return token;
    }

    public SecretKey getSecretKey(){
        byte[] bytes = Base64.from(secretKey).decode();
        return new SecretKeySpec(bytes,0,bytes.length,SecurityUtil.MAC_ALGORITHM.getName());
    }

    public String getAuthority(List<GrantedAuthority> roles){
        String authority=roles.stream().map(it->it.getAuthority().toString()).collect(Collectors.joining(" "));
        return authority;
    }


    public SignedJWT verifyToken(String token) throws ParseException, JOSEException {
        SignedJWT signedJWT= SignedJWT.parse(token);
        JWSVerifier jwsVerifier= new MACVerifier(getSecretKey());
        boolean verifyed=signedJWT.verify(jwsVerifier);
        if(!(verifyed && signedJWT.getJWTClaimsSet().getExpirationTime().after(new Date()))){
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
        if(invalidateTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())){
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
        return signedJWT;
    }

}
