package com.lec.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lec.domain.Member;
import com.lec.persistence.MemberRepository;
import com.lec.service.MemberService;

@Service
public class memberServiceimpl implements MemberService{

	@Autowired
	private MemberRepository memberRepository;
	
	@Override
	public Member getMember(Member member) {
		Optional<Member> findMember = memberRepository.findById(member.getId());
		if(findMember.isPresent()) 
			return findMember.get();
		else return null;
	}
	
	@Override
	public Page<Member> getMemberList(Pageable pageable, String searchType, String searchWord) {
		
		if(searchType.equalsIgnoreCase("id")){
			return memberRepository.findByIdContaining(searchWord, pageable);
		} else {
			return memberRepository.findByNameContaining(searchWord, pageable);
		}
	}
	
	@Override
	public long getTotalRowCount(Member member) {
		return memberRepository.count();
	}
	
	@Override
	public void insertMember(Member member) {
	    memberRepository.save(member);
	}


	@Override
	public void updateMember(Member member) {
		Member findMember = memberRepository.findById(member.getId()).get();
		findMember.setNickname(member.getNickname());
		findMember.setProfile(member.getProfile());
		memberRepository.save(findMember);
	}
	@Override
	public void deleteMember(Member member) {
		// 경고메시지 추가하기
		memberRepository.deleteById(member.getId());
	}

	@Override
	public Member findByName(String name) {
		return memberRepository.findByName(name);
	}

	@Override
	public void updatePoint(Member member) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Page<Member> selectMember(Pageable pageable, String searchType, String searchWord) {
	    if (searchType.equalsIgnoreCase("id")) {
	        return memberRepository.findByIdContainingOrNickname(searchWord, searchWord, pageable);
	    } else if (searchType.equalsIgnoreCase("nickname")) {
	        return memberRepository.findByIdContainingOrNickname(searchWord, searchWord, pageable);
	    } else {
	        return memberRepository.findByIdContainingOrNicknameIgnoreCaseContainingOrName(searchWord, searchWord, searchWord, pageable);
	    }
	}

}
