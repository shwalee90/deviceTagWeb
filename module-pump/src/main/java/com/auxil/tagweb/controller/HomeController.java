package com.auxil.tagweb.controller;

import com.auxil.tagweb.repository.SpringDataJpaMemberRepository;
import com.auxil.tagweb.security.JwtTokenProvider;
import com.auxil.tagweb.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@RequiredArgsConstructor
@RestController
@RequestMapping("/tagWeb")
public class HomeController implements ErrorController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final SpringDataJpaMemberRepository userRepository;

    private final AuthService authService;


    @RequestMapping({"/", "/error"})
    public String index() {
        return "index.html";
    }

    @GetMapping("/test")
    public String test(){
        return "tttt";
    }



//    @GetMapping("/")
//    public String home(HttpServletRequest request, Model model){
//
///       HttpSession session = request.getSession(false);
//       // System.out.println(token);
//
//        if (session != null) {
//            System.out.println("session (O)");
//
//            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//            SecurityMember user = (SecurityMember)principal;
//
//            String userName = ((SecurityMember) principal).getUsername();
//
//
//            model.addAttribute("userName" , userName);
//            model.addAttribute("role" , ((SecurityMember) principal).getAuthorities());
//
//
//        }else{
//            System.out.println("session (X)");
//        }
//
//
//
//        return "home";

//
//        return "안녕하세요. 현재 서버시간은 "+new Date() +"입니다. \n";
//    }





    @GetMapping("/pump/home")
    public String pumpHome(){

        return "pump/home";
    }


    @GetMapping("/login")
    public void login(HttpServletRequest request){

        String token = request.getParameter("token");

        System.out.println("token : " + token);

        System.out.println("token2 : " +request.getHeader("token"));

    }



    // 서블릿 HTTP 세션 사용
    @PostMapping("/logout")
    public String logoutV3(HttpServletRequest request) {
        // getSession(false) 를 사용해야 함 (세션이 없더라도 새로 생성하면 안되기 때문)
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }






}
