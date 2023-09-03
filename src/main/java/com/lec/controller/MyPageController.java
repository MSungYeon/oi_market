package com.lec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.lec.domain.Member;
import com.lec.service.MemberService;

@Controller
@SessionAttributes("member")
public class MyPageController {

	@Autowired
	private MemberService memberService;
	
	@GetMapping("/myPage")
	public String myPage(@ModelAttribute("member") Member member) {
		if(member.getId() == null) {
			return "redirect:login";
		}
		return "mypage/myPage";
	}
	
	@GetMapping("/buyBoardList")
	public String buyBoardList(@ModelAttribute("member") Member member) {
		if(member.getId() == null) {
			return "redirect:login";
		}
		return "mypage/buyBoardList";
	}
	
	@GetMapping("/sellBoardList")
	public String sellBoardList(@ModelAttribute("member") Member member) {
		if(member.getId() == null) {
			return "redirect:login";
		}
		return "mypage/sellBoardList";
	}
	
	@GetMapping("/myfBoardList")
	public String myfBoardList(@ModelAttribute("member") Member member) {
		if(member.getId() == null) {
			return "redirect:login";
		}
		return "mypage/myfBoardList";
	}
}
