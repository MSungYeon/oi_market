package com.lec.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.lec.domain.Boardf;

public interface BoardfService {
	
	long getTotalRowCount(Boardf boardf);
	Boardf getfBoard(Boardf boardf);
	Page<Boardf> getfBoardList(Pageable pageable, String searchType, String searchWord);
	void insertfBoard(Boardf boardf);
	void updatefBoard(Boardf boardf);
	void deletefBoard(Boardf boardf);
	int updatefReadCount(Boardf boardf);
}
