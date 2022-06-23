package com.auxil.tagweb.controller;

import com.auxil.tagweb.domain.ApiResponse;
import com.auxil.tagweb.dto.AuthDTO;
import com.auxil.tagweb.repository.SpringDataJpaMemberRepository;
import com.auxil.tagweb.security.JwtTokenProvider;
import com.auxil.tagweb.service.AuthService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;


@RequiredArgsConstructor
@RestController
public class AuthController {

    @Lazy
    private final PasswordEncoder passwordEncoder;
    @Lazy
    private final JwtTokenProvider jwtTokenProvider;
    @Lazy
    private final SpringDataJpaMemberRepository userRepository;
    @Lazy
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

    }

    @PostMapping("/get-newToken")
    public ApiResponse newAccessToken(@RequestBody @Valid AuthDTO.GetNewAccessTokenDTO getNewAccessToken, HttpServletRequest request) {
        return authService.newAccessToken(getNewAccessToken, request);
    }



}
