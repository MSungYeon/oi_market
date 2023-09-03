package com.lec.persistence;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lec.domain.Member;

public interface MemberRepository extends JpaRepository<Member, String> {

	Page<Member> findByIdContaining(String searchWord, Pageable pageable);
	Page<Member> findByNameContaining(String searchWord, Pageable pageable);
	
	Optional<Member> findById(String id);
	Member findByName(String name);
//	Optional<Member> findById(String receiverId);
	Page<Member> findByNicknameIgnoreCaseContaining(String searchWord, Pageable pageable);
	Page<Member> findByIdContainingOrNicknameIgnoreCaseContaining(String searchWord, String searchWord2,
			Pageable pageable);
	Page<Member> findByIdContainingOrNicknameIgnoreCaseContainingOrNameContaining(String searchWord, String searchWord2,
			String searchWord3, Pageable pageable);
	
    Page<Member> findByIdContainingOrNickname(String searchWord1, String searchWord2, Pageable pageable);
    Page<Member> findByIdContainingOrNicknameIgnoreCaseContainingOrName(String searchWord1, String searchWord2, String searchWord3, Pageable pageable);


}
