package com.project01.quiz.services.impl;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import com.project01.quiz.dto.request.AuthRequest;
import com.project01.quiz.dto.request.RegisterRequest;
import com.project01.quiz.dto.request.TokenRequest;
import com.project01.quiz.dto.response.AuthResponse;
import com.project01.quiz.dto.response.IntrospecResponse;
import com.project01.quiz.entity.InValidTokenEntity;
import com.project01.quiz.entity.UserEntity;
import com.project01.quiz.exception.CustomException.AppException;
import com.project01.quiz.exception.CustomException.ErrorCode;
import com.project01.quiz.repository.InvalidateTokenRepository;
import com.project01.quiz.repository.RoleRepository;
import com.project01.quiz.repository.UserRepository;
import com.project01.quiz.services.AuthServices;
import com.project01.quiz.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class AuthServicesImpl implements AuthServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private InvalidateTokenRepository invalidateTokenRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {
        UserEntity user=userRepository.
                findByUsername(authRequest.getUsername()).orElseThrow(()->new AppException(ErrorCode.USER_NOTFOUND));
        if(!passwordEncoder.matches(authRequest.getPassword(),user.getPassword())){
            throw new AppException(ErrorCode.PASSWORD_WRONG);
        }
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword());
        Authentication authentication=authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        String token= securityUtil.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public IntrospecResponse introspecToken(TokenRequest tokenRequest) {
         boolean isvalid=false;
        try {
            securityUtil.verifyToken(tokenRequest.getToken());
            isvalid = true;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
        return IntrospecResponse.builder()
                .valid(isvalid)
                .build();
    }

    @Override
    public AuthResponse refreshToken(TokenRequest tokenRequest) {
        try {
            SignedJWT signedJWT =  securityUtil.verifyToken(tokenRequest.getToken());
            String id = signedJWT.getJWTClaimsSet().getJWTID();
            Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();
            String idUser = signedJWT.getJWTClaimsSet().getSubject();
            UserEntity user=userRepository.findById(Long.parseLong(idUser)).get();

            InValidTokenEntity inValidTokenEntity=InValidTokenEntity.builder()
                    .id(id)
                    .expirytime(expiration)
                    .user(user)
                    .build();

            invalidateTokenRepository.save(inValidTokenEntity);

            String token= securityUtil.generateToken(user);

            return AuthResponse.builder()
                    .token(token)
                    .build();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Void logout(TokenRequest tokenRequest){

        try {
            SignedJWT signedJWT =  securityUtil.verifyToken(tokenRequest.getToken());
            String id = signedJWT.getJWTClaimsSet().getJWTID();
            Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();
            String idUser = signedJWT.getJWTClaimsSet().getSubject();
            UserEntity user=userRepository.findById(Long.parseLong(idUser)).get();

            InValidTokenEntity inValidTokenEntity=InValidTokenEntity.builder()
                    .id(id)
                    .expirytime(expiration)
                    .user(user)
                    .build();

            invalidateTokenRepository.save(inValidTokenEntity);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
       return null;
    }

    @Override
    public void register(RegisterRequest registerRequest) {
        if(userRepository.existsByUsername(registerRequest.getUsername())){
            throw new AppException(ErrorCode.USER_EXITED);
        }
        List<Long> roleId = new ArrayList<>();
        roleId.add(2L);
        UserEntity userEntity=UserEntity.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .birth(registerRequest.getBirth())
                .fullname(registerRequest.getFullname())
                .roles(roleRepository.findAllById(roleId))
                .build();

        userRepository.save(userEntity);
    }

}
