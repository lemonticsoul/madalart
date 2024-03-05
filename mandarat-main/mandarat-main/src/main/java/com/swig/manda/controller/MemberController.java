package com.swig.manda.controller;

import com.swig.manda.dto.MemberDto;
import com.swig.manda.model.Member;
import com.swig.manda.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/member")
public class MemberController {


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    MemberService memberService;


    //회원가입 get
    @GetMapping("/join")
    public ResponseEntity<?> joinForm() {
        Map<String, Object> response = new HashMap<>();
        response.put("member", new MemberDto());
        response.put("passwordPolicy", "비밀번호는 8~20자리, 영문, 숫자, 특수문자 포함");

        return ResponseEntity.ok(response);
    }


    //회원가입 해야함! post
    @PostMapping("/join")
    public ResponseEntity<?> joinSave(@Valid @RequestBody MemberDto memberDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){

            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());

        }
        if (!memberDto.getPassword().equals(memberDto.getRepassword())) {

            return ResponseEntity.badRequest().body("패스워드가 맞지 않습니다!");
        }

        memberService.join(memberDto);
        return ResponseEntity.ok().body("회원가입을 축하드립니다.");



    }

}