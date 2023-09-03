package com.lec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.lec.domain.Boardf;
import com.lec.domain.Member;
import com.lec.domain.Reply;
import com.lec.service.ReplyService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@SessionAttributes({"loggedInMember"})

public class ReplyController {

    @Autowired
    private final ReplyService replyService;

    @PostMapping("/reply_write")
    public String replyWrite(@ModelAttribute Reply reply, @RequestParam Long seq_f,
            @SessionAttribute(name = "loggedInMember", required = false) Member member) {

        Boardf boardf = replyService.findBoardfById(seq_f);
        if (boardf != null) {
            reply.setBoardf(boardf);
        }
        
        reply.setMember(member); // 수정: member 객체를 reply에 설정
        replyService.replyWrite(reply, member, seq_f);
        return "redirect:/getfBoard?seq_f=" + seq_f;
    }




    @GetMapping("/reply_delete")
    public String replyDelete(@ModelAttribute Reply reply,
                              @SessionAttribute(name = "loggedInMember", required = false) Member member,
                              @RequestHeader("Referer") String referer) {

        Long seq_f = null;
        if (reply.getBoardf() != null) {
            seq_f = reply.getBoardf().getSeq_f();
        }

        replyService.replyDelete(reply);
        
        if (seq_f != null) {
            return "redirect:getfBoard?seq_f=" + seq_f;
        } else {
            return "redirect:" + referer;
        }
    }


};
