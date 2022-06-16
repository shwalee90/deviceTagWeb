package com.auxil.pump.controller;

import com.auxil.pump.aop.RequestWrapper;
import com.auxil.pump.domain.TbEquipInfo;
import com.auxil.pump.domain.TbTagBase;
import com.auxil.pump.service.AuthService;
import com.auxil.pump.service.TbService;
import com.auxil.pump.service.TestMod;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@RestController
public class ApiController {


    @Lazy
    private final TestMod testMod;



    @GetMapping ("/auth/pump")
    public void authHomeP(HttpServletRequest request){

        System.out.println("token2 : " +request.getHeader("token"));


    }





    @GetMapping("/modTest")
    public void modTest(){

        testMod.quickStart();


    }



    @PostMapping("/pump")
    public String pump(HttpServletRequest request, HttpServletResponse response ,Model model){

        String token = request.getParameter("token");

        System.out.println("token : " + token);

        System.out.println("token2 : " +request.getHeader("token"));

      //  request.setAttribute("token",token);

//        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("token" , token);
//
//        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params,headers);
//
//
//        RestTemplate rt = new RestTemplate();
//        ResponseEntity <String> res = rt.exchange("http://localhost:7000/auth/home" , HttpMethod.POST
//        , entity , String.class);
//
//        System.out.println(res);

        return "redirect:/auth/home";


    }





    @PostMapping("/auth/token")
    @ResponseBody
    public Map<String, String> apiTokenPost() {
        final Map<String, String> map = new HashMap<>();


        map.put("message", "Welcome");
        return map;
    }




}
