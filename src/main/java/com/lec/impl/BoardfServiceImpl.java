package com.lec.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lec.domain.Boardf;
import com.lec.domain.Reply;
import com.lec.persistence.BoardfRepository;
import com.lec.persistence.ReplyRepository;
import com.lec.service.BoardfService;

@Service
public class BoardfServiceImpl implements BoardfService {

	@Autowired
	private BoardfRepository boardRepo;
	
	@Autowired
	private ReplyRepository replyRepository;

	public Boardf getfBoard(Boardf boardf) {
		Optional<Boardf> findBoard = boardRepo.findById(boardf.getSeq_f());
		if(findBoard.isPresent())
			return findBoard.get();
		else return null;
	}	

	public Page<Boardf> getfBoardList(Pageable pageable, String searchType, String searchWord) {		
		if(searchType.equalsIgnoreCase("title_f")) {
			return boardRepo.findByTitleContaining(searchWord, pageable);
		} else if(searchType.equalsIgnoreCase("writer_f")) {
			return boardRepo.findByWriterContaining(searchWord, pageable);
		} else {
			return boardRepo.findByContentContaining(searchWord, pageable);
		}
	}
    

	public void insertfBoard(Boardf boardf) {
		boardRepo.save(boardf);
		boardRepo.updateLastSeq(0L, 0L, boardf.getSeq_f());
	}


	public void updatefBoard(Boardf boardf) {
		Boardf findBoard = boardRepo.findById(boardf.getSeq_f()).get();

		findBoard.setTitle_f(boardf.getTitle_f());
		findBoard.setContent_f(boardf.getContent_f());
		findBoard.setCate_f(boardf.getCate_f());
		boardRepo.save(findBoard);
	}

//	public void deletefBoard(Boardf boardf) {
//		boardRepo.deleteById(boardf.getSeq_f());
//	}
	
	public void deletefBoard(Boardf boardf) {
	    List<Reply> replies = boardf.getReply();
	    if (replies != null) {
	        for (Reply reply : replies) {
	            reply.setBoardf(null); // 부모 엔티티와의 연관관계를 제거
	            reply.setMember(null); // 다른 연관관계가 있다면 해당 연관관계도 제거
	            replyRepository.delete(reply);
	        }
	    }
	    boardf.setReply(null); // 자식 엔티티와의 연관관계를 제거
	    boardf.setMember(null); // 다른 연관관계가 있다면 해당 연관관계도 제거
	    boardRepo.deleteById(boardf.getSeq_f());
	}


	@Override
	public long getTotalRowCount(Boardf boardf) {
		return boardRepo.count();
	}

	@Override
	public int updatefReadCount(Boardf boardf) {
		return boardRepo.updatefReadCount(boardf.getSeq_f());
	}
}
