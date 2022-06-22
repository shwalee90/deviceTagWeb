package com.auxil.middle.controller;

import com.auxil.middle.domain.Member;
import com.auxil.middle.domain.MemberRole;
import com.auxil.middle.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


@RestController
public class MemberController {

    private final MemberService memberService ;

    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }


    @PostMapping("/members/new")
    public String create(@RequestBody HashMap<String, String> map ){

        MemberRole role = new MemberRole();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Member member = new Member();
        member.setName(map.get("username"));
        member.setPassword(passwordEncoder.encode(map.get("password")));
        role.setRoleName("USER");
        member.setRoles(Arrays.asList(role));

        memberService.join(member);

        return "redirect:/";
    }


    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }


}
