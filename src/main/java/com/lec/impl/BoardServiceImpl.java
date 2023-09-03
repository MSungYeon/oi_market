package com.lec.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lec.domain.Board;
import com.lec.persistence.BoardRepository;
import com.lec.service.BoardService;

@Service
public class BoardServiceImpl implements BoardService{

	@Autowired
	private BoardRepository boardRepository;
	
	@Override
	public long getTotalRowCount(Board board) {
		return boardRepository.count();
	}

	@Override
	public Board getBoard(Board board) {
		Optional<Board> findBoard = boardRepository.findById(board.getSeq());
		if(findBoard.isPresent())
			return findBoard.get();
		else return null;
	}

	@Override
	public Page<Board> getBoardList(Pageable pageable, String searchType, String searchWord) {
		if(searchType.equalsIgnoreCase("title")) {
			return boardRepository.findByTitleContaining(searchWord, pageable);
		} else if(searchType.equalsIgnoreCase("cate")) {
			return boardRepository.findByCateContaining(searchWord, pageable);
		} else {
			return boardRepository.findByContentContaining(searchWord, pageable);
		}
	}

	@Override
	public void insertBoard(Board board) {
		boardRepository.save(board);
	}

	@Override
	public void updateBoard(Board board) {
        if (board.getSeq() == null) {
            throw new IllegalArgumentException("The ID must not be null.");
        }
        
        Optional<Board> optionalBoard = boardRepository.findById(board.getSeq());
        if (optionalBoard.isPresent()) {
        	Board existingBoard = optionalBoard.get();
            existingBoard.setTitle(board.getTitle());
            existingBoard.setContent(board.getContent());
            existingBoard.setPrice(board.getPrice());
            existingBoard.setFileName(board.getFileName());
            existingBoard.setCate(board.getCate());
    		boardRepository.save(existingBoard);
        } else {
            throw new IllegalArgumentException("News not found with ID: " + board.getSeq());
        }
		
	}

	@Override
	public void deleteBoard(Board board) {
		boardRepository.deleteById(board.getSeq());				
	}
	
	@Override
	public int updateReadCount(Board board) {
		return boardRepository.updateReadCount(board.getSeq());
	}

	@Override
	public void updateLikedCount(Board board) {
		boardRepository.updateLikedCount(board.getSeq());
	}
}
