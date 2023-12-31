package com.lec.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.lec.domain.Member;

public interface MemberService {
	
	long getTotalRowCount(Member member);
	Member getMember(Member member);
	Page<Member> getMemberList(Pageable pageable, String searchType, String searchWord);
	void insertMember(Member member);
	void updateMember(Member member);
	void updatePoint(Member member);
	void deleteMember(Member member);
	
	// Add this method declaration
	Member findByName(String name);
	Page<Member> selectMember(Pageable pageable, String searchType, String searchWord);
	
}
