package com.lec.service;

import com.lec.domain.Boardf;
import com.lec.domain.Member;
import com.lec.domain.Reply;

public interface ReplyService {

	void replyWrite(Reply reply, Member member, Long seq_f);
	void replyDelete(Reply reply);
	Boardf findBoardfById(Long seq_f);
	void saveReply(Reply reply);
}
