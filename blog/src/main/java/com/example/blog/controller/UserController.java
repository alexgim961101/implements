package com.example.blog.controller;

import com.example.blog.dto.LoginDto;
import com.example.blog.entity.member.Member;
import com.example.blog.entity.member.MemberRepository;
import com.example.blog.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final MemberRepository memberRepository;

    @GetMapping("/")
    public String homePage(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {
        if(memberId == null) return "home";

        Member member = memberRepository.findById(memberId).orElse(null);
        if(member == null) return "home";

        model.addAttribute("member", member);
        return "member/loginHome";
    }

    @GetMapping("/login")
    public String loginPage(@ModelAttribute LoginDto loginDto) {
        return "member/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginDto loginDto, BindingResult bindingResult, HttpServletResponse response) {
        if(bindingResult.hasErrors()) return "member/loginForm";

        Member loginMember = userService.login(loginDto.getLoginId(), loginDto.getPassword());

        if(loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "member/loginForm";
        }

        Cookie cookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
        response.addCookie(cookie);

        return "redirect:/";
    }

    @PostMapping("logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("memberId", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/";
    }

    @GetMapping("/add")
    public String addForm(@ModelAttribute Member member) {
        return "member/addMemberForm";
    }

    @PostMapping("/add")
    public String save(@Valid @ModelAttribute Member member, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "member/addMemberForm";
        }

        memberRepository.save(member);
        return "redirect:/";
    }
}
