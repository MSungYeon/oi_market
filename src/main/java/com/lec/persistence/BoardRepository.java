package com.lec.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.lec.domain.Board;

public interface BoardRepository extends CrudRepository<Board, Long> {

	@Modifying
	@Transactional
	@Query("update Board b set b.cnt = b.cnt + 1 where b.seq = :seq")
	int updateReadCount(@Param("seq") Long seq);
	
	@Modifying
	@Transactional
	@Query("update Board b set b.liked = b.liked + 1 where b.seq = :seq")
	int updateLikedCount(@Param("seq") Long seq);
	
	Page<Board> findByWriterContaining(String writer, Pageable pageable);
	
	@Query("SELECT b FROM Board b WHERE b.title LIKE %:title%")
	Page<Board> findByTitleContaining(String title, Pageable pageable);
	
	@Query("SELECT b FROM Board b WHERE b.cate LIKE %:cate%")
	Page<Board> findByCateContaining(String cate, Pageable pageable);
	
	Page<Board> findByPriceContaining(int price, Pageable pageable);
	Page<Board> findByContentContaining(String content, Pageable pageable);
	Page<Board> findByAddrContaining(String addr, Pageable pageable);
	Page<Board> findByLikedContaining(int liked, Pageable pageable);
	List<Board> findAll(Sort by);
}
