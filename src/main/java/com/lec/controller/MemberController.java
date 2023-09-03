package com.lec.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.lec.domain.Member;
import com.lec.domain.PagingInfo;
import com.lec.service.MemberService;

@Controller
@SessionAttributes("pagingInfo")
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	@Value("${path.upload}")
	public String uploadFolder;
	
	@Autowired
	Environment environment;
	
	public PagingInfo pagingInfo = new PagingInfo();
	
	@ModelAttribute("member")
	public Member setMember() {
		return new Member();
	}
	
	
	@RequestMapping("getMemberList")
	public String getMemberList(Model model,
			@RequestParam(defaultValue="0") int curPage,
			@RequestParam(defaultValue="6") int rowSizePerPage,
			@RequestParam(defaultValue="nickname") String searchType,
			@RequestParam(defaultValue="") String searchWord) {   			
		
		Pageable pageable = PageRequest.of(curPage, rowSizePerPage, Sort.by("id").ascending());
		Page<Member> pagedResult = memberService.selectMember(pageable, searchType, searchWord);
	
		int totalRowCount  = pagedResult.getNumberOfElements();
		int totalPageCount = pagedResult.getTotalPages();
		int pageSize       = pagingInfo.getPageSize();
		int startPage      = curPage / pageSize * pageSize + 1;
		int endPage        = startPage + pageSize - 1;
		endPage = endPage > totalPageCount ? (totalPageCount > 0 ? totalPageCount : 1) : endPage;
	
		pagingInfo.setCurPage(curPage);
		pagingInfo.setTotalRowCount(totalRowCount);
		pagingInfo.setTotalPageCount(totalPageCount);
		pagingInfo.setStartPage(startPage);
		pagingInfo.setEndPage(endPage);
		pagingInfo.setSearchType(searchType);
		pagingInfo.setSearchWord(searchWord);
		pagingInfo.setRowSizePerPage(rowSizePerPage);
		model.addAttribute("pagingInfo", pagingInfo);

		model.addAttribute("pagedResult", pagedResult);
		model.addAttribute("pageable", pageable);
		model.addAttribute("cp", curPage);
		model.addAttribute("sp", startPage);
		model.addAttribute("ep", endPage);
		model.addAttribute("ps", pageSize);
		model.addAttribute("rp", rowSizePerPage);
		model.addAttribute("tp", totalPageCount);
		model.addAttribute("st", searchType);
		model.addAttribute("sw", searchWord);			
		return "member/getMemberList";
	}
	
	@GetMapping("/insertMember")
	public String insertMemberForm(Member member) {

		return "member/insertMember";
	}
	
	@PostMapping("/insertMember")
	public String insertMember(Member member) throws IOException {
	    if (member.getId() == null) {
	        return "redirect:login";
	    }
	    
	    // 파일 업로드
	    MultipartFile uploadFile = member.getUploadFile();
	    if (!uploadFile.isEmpty()) {
	        String fileName = uploadFile.getOriginalFilename();
	        uploadFile.transferTo(new File(uploadFolder + fileName));
	        member.setProfile(fileName);
	    } else {
	        // 파일이 업로드되지 않았을 경우 기본 이미지 파일을 설정
	    	member.setProfile("default_profile.png");
	    }
	    
	    memberService.insertMember(member);
	    return "redirect:login";
	}

	
	@GetMapping("/getMember")
	public String getMember(Member member, Model model) {
		if(member.getId() == null) {
			return "redirect:login";
		}
		model.addAttribute("member", memberService.getMember(member));
		return "member/getMember";
	}	
	
	@GetMapping("/updateMember")
	public String updateMemberForm(Member member, Model model) {
		if(member.getId() == null) {
			return "redirect:login";
		}
		model.addAttribute("member", memberService.getMember(member));
		return "member/updateMember";
	}
	
	@PostMapping("/updateMember")
	public String updateMember(Member member, @RequestParam("uploadFile") MultipartFile file, HttpSession session) {
	    if (member.getId() == null) {
	        return "redirect:login";
	    }
	    // 프로필 이미지 업로드 처리
	    if (!file.isEmpty()) {
	        try {
	            String fileName = file.getOriginalFilename();
	            String savePath = uploadFolder + fileName;
	            file.transferTo(new File(savePath));
	            member.setProfile(fileName); // DB에 저장된 파일명을 업데이트
	        } catch (IOException e) {
	            // 파일 업로드 실패시 처리할 로직
	            e.printStackTrace();
	        }
	    }

	    // 닉네임 업데이트 처리
	    memberService.updateMember(member);

	    // 세션에 저장된 member 객체 업데이트
	    Member updatedMember = (Member) session.getAttribute("member");
	    updatedMember.setProfile(member.getProfile()); // 업데이트된 프로필 사진을 세션에 반영
	    updatedMember.setNickname(member.getNickname()); // 업데이트된 닉네임을 세션에 반영
	    session.setAttribute("member", updatedMember);

	    return "redirect:updateMember?id=" + member.getId();
	}


	
	@GetMapping("/deleteMember")
	public String deleteMemberForm(Member member) {
		if(member.getId() == null) {
			return "redirect:login";
		}
		memberService.deleteMember(member);
		
		return "redirect:login";
	}
}










