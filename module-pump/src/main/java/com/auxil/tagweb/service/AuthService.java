package com.auxil.tagweb.service;

import com.auxil.tagweb.domain.ApiResponse;
import com.auxil.tagweb.domain.RefreshToken;
import com.auxil.tagweb.domain.ResponseMap;
import com.auxil.tagweb.dto.AuthDTO;
import com.auxil.tagweb.repository.AuthRepository;
import com.auxil.tagweb.security.ErrorCode;
import com.auxil.tagweb.security.JwtTokenProvider;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AuthService {

    private JwtTokenProvider jwtTokenProvider;

    private AuthenticationManager authenticationManager;

    private AuthRepository authRepository;


    AuthService( @Lazy JwtTokenProvider jwtTokenProvider , @Lazy AuthenticationManager authenticationManager, @Lazy AuthRepository authRepository ){
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.authRepository = authRepository;
    }




    public ApiResponse login(AuthDTO.LOGINDTO logindto) {
        ResponseMap result = new ResponseMap();

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(logindto.getName() ,logindto.getPassword())
            );

            Map createToken = createTokenReturn(logindto);
            result.setResponseData("accessToken" , createToken.get("accessToken"));
            result.setResponseData("refreshIdx" , createToken.get("refreshIdx"));

        }catch (Exception e){
            e.printStackTrace();
            try {
                throw new AuthenticationException(ErrorCode.UsernameOrPasswordNotFoundException.getMessage());
            } catch (AuthenticationException authenticationException) {
                authenticationException.printStackTrace();
            }
        }

        return result;

    }

    private Map createTokenReturn(AuthDTO.LOGINDTO logindto) {
        Map result = new HashMap();

        String accessToken = jwtTokenProvider.createAccessToken(logindto);
        String refreshToken = jwtTokenProvider.createRefreshToken(logindto).get("refreshToken");
        String refreshTokenExpirationAt = jwtTokenProvider.createRefreshToken(logindto).get("refreshTokenExpAt");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

       // System.out.println(refreshTokenExpirationAt);
        Date rtea = null;
        try {
            rtea = sdf.parse(refreshTokenExpirationAt);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        RefreshToken insertRefreshToken = RefreshToken.builder().userName(logindto.getName())
                .accessToken(accessToken).refreshToken(refreshToken).refreshTokenExpirationAt(rtea)
                .build();

        RefreshToken prior = authRepository.findByUser_name(logindto.getName());

        if(prior == null){
            authRepository.save(insertRefreshToken);
        }
        else{
            long pIdx = prior.getIdx();
            System.out.println(pIdx);

            insertRefreshToken = RefreshToken.builder().idx(pIdx).userName(logindto.getName())
                    .accessToken(accessToken).refreshToken(refreshToken).refreshTokenExpirationAt(rtea).build();



            authRepository.save(insertRefreshToken);
        }

       // authRepository.saveRefreshToken(insertRefreshToken);

        result.put("accessToken" , accessToken);
        result.put("refreshIdx" , insertRefreshToken.getIdx());

         return result;
    }

    public ApiResponse newAccessToken(AuthDTO.GetNewAccessTokenDTO getNewAccessToken, HttpServletRequest request) {
        ResponseMap result = new ResponseMap();
        Optional<RefreshToken> refreshToken = authRepository.findRefreshTokenByIdx(getNewAccessToken.getRefreshIdx());

        String sRefreshToken = refreshToken.get().getRefreshToken();

        //accessToken은 만료되었지만 refreshtoken 만료 안됐을 때
        if(jwtTokenProvider.validateToken(request,sRefreshToken)) {
            String name = jwtTokenProvider.getUserInfo(sRefreshToken);
            AuthDTO.LOGINDTO logindto = new AuthDTO.LOGINDTO();
            logindto.setName(name);

            Map createToken = createTokenReturn(logindto);
            result.setResponseData("accessToken", createToken.get("accessToken"));
            result.setResponseData("refreshIdx", createToken.get("refreshIdx"));
        }else{
            //refreshtoken 또한 만료된 경우 다시 로그인
            result.setResponseData("code" ,  ErrorCode.ReLogin.getCode());
            result.setResponseData("message" ,  ErrorCode.ReLogin.getMessage());
            result.setResponseData("HttpStatus" ,  ErrorCode.ReLogin.getStatus());
        }
        return result;
    }
}
