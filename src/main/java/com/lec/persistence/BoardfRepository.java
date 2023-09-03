package com.lec.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.lec.domain.Boardf;

public interface BoardfRepository extends CrudRepository<Boardf, Long> {

    @Modifying
    @Transactional
    @Query("update Boardf b set b.cnt_f = b.cnt_f + 1 where b.seq_f = :seq_f")
    int updatefReadCount(@Param("seq_f") Long seq_f);

    @Modifying
    @Transactional
    @Query("update Boardf b set b.boardf_ref = b.seq_f, b.boardf_lev = :lev_f, b.boardf_seq = :_seq_f where b.seq_f = :seq_f")
    void updateLastSeq(@Param("lev_f") Long i, @Param("_seq_f") Long j, @Param("seq_f") Long seq_f);

    @Query("SELECT b FROM Boardf b WHERE b.title_f LIKE %:title_f%")
    Page<Boardf> findByTitleContaining(@Param("title_f") String title_f, Pageable pageable);

    @Query("SELECT b FROM Boardf b WHERE b.writer_f LIKE %:writer_f%")
    Page<Boardf> findByWriterContaining(@Param("writer_f") String writer_f, Pageable pageable);

    @Query("SELECT b FROM Boardf b WHERE b.content_f LIKE %:content_f%")
    Page<Boardf> findByContentContaining(@Param("content_f") String content_f, Pageable pageable);

}
