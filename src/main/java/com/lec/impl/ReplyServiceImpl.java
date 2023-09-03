package com.lec.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lec.domain.Boardf;
import com.lec.domain.Member;
import com.lec.domain.Reply;
import com.lec.persistence.BoardfRepository;
import com.lec.persistence.MemberRepository;
import com.lec.persistence.ReplyRepository;
import com.lec.service.ReplyService;

@Service
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;
    private final BoardfRepository boardfRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public ReplyServiceImpl(ReplyRepository replyRepository, BoardfRepository boardfRepository, MemberRepository memberRepository) {
        this.replyRepository = replyRepository;
        this.boardfRepository = boardfRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public void replyWrite(Reply reply, Member member, Long seq_f) {
        Optional<Boardf> findBoardf = boardfRepository.findById(seq_f);
        if (findBoardf.isPresent()) {
            Boardf boardf = findBoardf.get();
            reply.setBoardf(boardf);
        }
            reply.setMember(member);

        replyRepository.save(reply);
}

    @Override
    public void replyDelete(Reply reply) {
        replyRepository.delete(reply);
    }

	@Override
	public Boardf findBoardfById(Long seq_f) {
		return boardfRepository.findById(seq_f).orElse(null);
	}

	@Override
	public void saveReply(Reply reply) {
	    replyRepository.save(reply);
	}



}
