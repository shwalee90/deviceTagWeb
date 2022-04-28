package com.auxil.pump.controller;

import com.auxil.pump.domain.ApiResponse;
import com.auxil.pump.dto.AuthDTO;
import com.auxil.pump.repository.SpringDataJpaMemberRepository;
import com.auxil.pump.security.JwtTokenProvider;
import com.auxil.pump.service.AuthService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final SpringDataJpaMemberRepository userRepository;

    private final AuthService authService;


    @PostMapping("/login")
    @ApiOperation(value = "로그인")
    public ApiResponse login(@RequestBody HashMap<String, String> map , HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes ra){

        String id = map.get("username");
        String pw = map.get("password");

        System.out.println(map);
        AuthDTO.LOGINDTO logindto = new AuthDTO.LOGINDTO();

        logindto.setName(id);
        logindto.setPassword(pw);



        return authService.login(logindto);
//        ApiResponse ar =authService.login(logindto);

//        HashMap<String,String> hm = (HashMap<String, String>) ar.getResult();
//
//        HttpHeaders headers = new HttpHeaders();
//
//        //model.addAttribute("token" , hm.get("accessToken"));
//
//        headers.set("token" , hm.get("accessToken"));
//
//        //System.out.println( hm.get("accessToken"));
//
//        request.setAttribute("token" ,hm.get("accessToken"));
//
//        response.setHeader("token" ,hm.get("accessToken"));
//
//
//        ra.addAttribute("token" , hm.get("accessToken"));

        //    Request builder = new Request.Builder().url(url).get().addHeader("Authorization", "Bearer "+access_token).build();

        //  Request builder = new Request.Builder().url(url).get().addHeader("Authorization", "Bearer "+access_token).build();

//        System.out.println( hm.get("accessToken"));
//        System.out.println( ar.getCode());
//        System.out.println( ar.getResult());

        //     return "home";


    }

    @PostMapping("/get-newToken")
    public ApiResponse newAccessToken(@RequestBody @Valid AuthDTO.GetNewAccessTokenDTO getNewAccessToken, HttpServletRequest request) {
        return authService.newAccessToken(getNewAccessToken, request);
    }




}
