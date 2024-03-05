package com.swig.manda.service;

import com.swig.manda.dto.MemberDto;
import com.swig.manda.model.Member;
import com.swig.manda.repository.MemberRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
public class MemberService {

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void join(@Valid MemberDto memberDto){

        String encodedPassword=passwordEncoder.encode(memberDto.getPassword());

        Member member=new Member();
        member.setUsername(memberDto.getUsername());
        member.setPassword(encodedPassword);
        member.setRole(memberDto.getRole());
        member.setEmail(memberDto.getEmail());
        member.setName(memberDto.getName());
        memberRepository.save(member);

    }

    public Member registerNewOAuth2User(String provider, String providerId, String nickname,String email) {

        String encodedPassword = bCryptPasswordEncoder.encode("temporary-password");

        Member user = Member.builder()
                    .username(nickname)
                    .password(encodedPassword)
                    .role("USER")
                .email(email)
                    .provider(provider)
                    .providerId(providerId)
                    .build();

        return memberRepository.save(user);
    }



    public Boolean duplicateUsername(String username){

        return memberRepository.existsByUsername(username);

    }
    public boolean userEmailCheck(String email, String username) {
       Member member=memberRepository.findByUsername(username);
       return member!= null&&member.getUsername().equals(username);
    }

    public void updatePassword(String username, String newPassword) {
        Member member = memberRepository.findByUsername(username);
        if (member != null) {

            String encodedNewPassword = bCryptPasswordEncoder.encode(newPassword);

            member.setPassword(encodedNewPassword);

            memberRepository.save(member);
        }
    }

    public String findUsernameByEmailAndName(String email, String name) {

        Optional<Member> member = memberRepository.findByEmailAndName(email, name);
        return member.map(Member::getUsername).orElse(null);
    }




}
