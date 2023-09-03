package com.lec.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lec.domain.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
};
