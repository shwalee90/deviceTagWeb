package com.auxil.middle.controller;

import com.auxil.middle.domain.ApiResponse;
import com.auxil.middle.dto.AuthDTO;
import com.auxil.middle.repository.SpringDataJpaMemberRepository;
import com.auxil.middle.security.JwtTokenProvider;
import com.auxil.middle.service.AuthService;
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
import java.util.Date;
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
