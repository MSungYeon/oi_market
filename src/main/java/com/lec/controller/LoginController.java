package com.lec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.lec.domain.Member;
import com.lec.service.MemberService;

@Controller
@SessionAttributes("member")
public class LoginController {

	@Autowired
	private MemberService memberService;
	
	@GetMapping("/login")
	public String loginView() {
		return "/login/login";
	}
	
//	@PostMapping("/login")
//	public String login(Member member, Model model) {
//		Member findMember = memberService.getMember(member);
//		if(findMember != null && findMember.getPassword().equals(member.getPassword())) {
//			model.addAttribute("member", findMember);  // session과 request영역에서 동시에 저장
//			return "redirect:/getBoardList";
//		} else {
//			return "redirect:login";
//		}
//	}
	@PostMapping("/login")
	public String login(Member member, Model model) {
	    Member findMember = memberService.getMember(member);
	    if (findMember != null && findMember.getPassword().equals(member.getPassword())) {
	        model.addAttribute("member", findMember);
	        return "redirect:/getBoardList";
	    } else {
	        model.addAttribute("loginError", "아이디 또는 비밀번호가 올바르지 않습니다."); // 로그인 에러 메시지 추가
	        return "redirect:login";
	    }
	}

	@GetMapping("/logout") 
	public String logout(SessionStatus status) {
		status.setComplete();
		return "redirect:login";
	}
}
