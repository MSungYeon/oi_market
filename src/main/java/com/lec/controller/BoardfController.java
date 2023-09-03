package com.lec.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.lec.domain.Boardf;
import com.lec.domain.Member;
import com.lec.domain.PagingInfo;
import com.lec.domain.Reply;
import com.lec.service.BoardfService;
import com.lec.service.ReplyService;

@Controller
@SessionAttributes({"member", "pagingInfo"})
public class BoardfController {

	@Autowired
	private BoardfService boardfService;
	
    @Autowired
    private ReplyService replyService;
	
	@Autowired
	Environment environment;
	
	public PagingInfo pagingInfo = new PagingInfo();
	
	@ModelAttribute("member")
	public Member setMember() {
		return new Member();
	}

	@RequestMapping("/getfBoardList")
	public String getfBoardList(Model model,
			@RequestParam(defaultValue="0") int curPage,
			@RequestParam(defaultValue="9") int rowSizePerPage,
			@RequestParam(defaultValue="title_f") String searchType,
			@RequestParam(defaultValue="") String searchWord) {   			
		
		Pageable pageable = PageRequest.of(curPage, rowSizePerPage, Sort.by("seq_f").descending());
		Page<Boardf> pagedResult = boardfService.getfBoardList(pageable, searchType, searchWord);
	
		int totalRowCount  = pagedResult.getNumberOfElements();
		int totalPageCount = pagedResult.getTotalPages();
		int pageSize       = pagingInfo.getPageSize();
		int startPage      = curPage / pageSize * pageSize + 1;
		int endPage        = startPage + pageSize - 1;
		endPage = endPage > totalPageCount ? (totalPageCount > 0 ? totalPageCount : 1) : endPage;
	
		pagingInfo.setCurPage(curPage);
		pagingInfo.setTotalRowCount(totalRowCount);
		pagingInfo.setTotalPageCount(totalPageCount);
		pagingInfo.setStartPage(startPage);
		pagingInfo.setEndPage(endPage);
		pagingInfo.setSearchType(searchType);
		pagingInfo.setSearchWord(searchWord);
		pagingInfo.setRowSizePerPage(rowSizePerPage);
		model.addAttribute("pagingInfo", pagingInfo);

		model.addAttribute("pagedResult", pagedResult);
		model.addAttribute("pageable", pageable);
		model.addAttribute("cp", curPage);
		model.addAttribute("sp", startPage);
		model.addAttribute("ep", endPage);
		model.addAttribute("ps", pageSize);
		model.addAttribute("rp", rowSizePerPage);
		model.addAttribute("tp", totalPageCount);
		model.addAttribute("st", searchType);
		model.addAttribute("sw", searchWord);			
		return "fBoard/getfBoardList";
	}

	@GetMapping("/insertfBoard")
	public String insertfBoardView(@ModelAttribute("member") Member member) {
		if (member.getId() == null) {
			return "redirect:login";
		}
		return "fBoard/insertfBoard";
	}

	@PostMapping("/insertfBoard")
	public String insertfBoard(@ModelAttribute("member") Member member, Boardf boardf) throws IOException {
		if (member.getId() == null) {
			return "redirect:login";
		}	
		boardf.setMember(member);
		boardfService.insertfBoard(boardf);
		return "redirect:getfBoardList";
	}

	@GetMapping("/getfBoard")
	public String getfBoard(@ModelAttribute("member") Member member, Boardf boardf, Model model) {
		if (member.getId() == null) {
			return "redirect:login";
		}

		boardfService.updatefReadCount(boardf);
		model.addAttribute("boardf", boardfService.getfBoard(boardf));
		return "fBoard/getfBoard";
	}
	
	@GetMapping("/updatefBoard")
	public String updatefBoard(@ModelAttribute("member") Member member, Boardf boardf, Model model) {
	    if (member.getId() == null) {
	        return "redirect:login";
	    }

	    model.addAttribute("boardf", boardfService.getfBoard(boardf));
	    return "fBoard/updatefBoard";
	}

	@PostMapping("/updatefBoard")
	public String updatefBoard(@ModelAttribute("member") Member member, Boardf boardf) {
		if (member.getId() == null) {
			return "redirect:login";
		}

		boardfService.updatefBoard(boardf);
		return "redirect:getfBoard?seq_f=" + boardf.getSeq_f();
	}

	/*
	 * @GetMapping("/deletefBoard") public String
	 * deletefBoard(@ModelAttribute("member") Member member, Boardf boardf) { if
	 * (member.getId() == null) { return "redirect:login"; }
	 * 
	 * boardfService.deletefBoard(boardf); return "redirect:getfBoardList"; }
	 */
	@GetMapping("/deletefBoard")
	public String deletefBoard(@ModelAttribute("member") Member member, Boardf boardf) {
	    if (member.getId() == null) {
	        return "redirect:login";
	    }

	    List<Reply> replies = boardf.getReply();
	    if (replies != null) {
	        for (Reply reply : replies) {
	            replyService.replyDelete(reply);
	        }
	    }


	    // 게시물 삭제
	    boardfService.deletefBoard(boardf);

	    return "redirect:getfBoardList";
	}



	
    public int updateView(Boardf boardf) {
        return boardfService.updatefReadCount(boardf);
    }
}
