package com.lec.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.lec.domain.Board;
import com.lec.domain.Member;
import com.lec.domain.PagingInfo;
import com.lec.service.BoardService;

@Controller
@SessionAttributes({"member", "pagingInfo"})
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@Autowired
	Environment environment;
	
	@Value("${path.upload}")
	public String uploadFolder;
	
	@Value("${path.download}")
	public String downFolder;
	
	public PagingInfo pagingInfo = new PagingInfo();
	
	@ModelAttribute("member")
	public Member setMember() {
		return new Member();
	}
	
	
	@RequestMapping("/getBoardList")
	public String getBoardList(Model model,
			@RequestParam(defaultValue="0") int curPage,
			@RequestParam(defaultValue="10") int rowSizePerPage,
			@RequestParam(defaultValue="title") String searchType,
			@RequestParam(defaultValue="") String searchWord) {   			
		
		Pageable pageable = PageRequest.of(curPage, rowSizePerPage, Sort.by("seq").descending());
		Page<Board> pagedResult = boardService.getBoardList(pageable, searchType, searchWord);
	
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
		return "board/getBoardList";
	}
	
	@GetMapping("/insertBoard")
	public String insertBoardForm(@ModelAttribute("member") Member member) {
		if(member.getId() == null) {
			return "redirect:login";
		}
		return "board/insertBoard";
	}
	
	@PostMapping("/insertBoard")
	public String insertBoard(@ModelAttribute("member") Member member, Board board, HttpServletRequest request, @RequestParam("upload") MultipartFile[] files) throws IOException {
		if(member.getId() == null) {
			return "redirect:login";
		}
		
		// 파일업로드
	    List<String> uploadFiles = new ArrayList<>();
	    for (MultipartFile uploadFile : files) {
	        if (!uploadFile.isEmpty()) {
	            String upload = uploadFile.getOriginalFilename();
	            uploadFile.transferTo(new File(uploadFolder + upload));
	            uploadFiles.add(upload);
	        }
	    }
	    board.setUploadFiles(uploadFiles);

		board.setMember(member);
		boardService.insertBoard(board);
		return "redirect:getBoard?seq=" + board.getSeq();
	}
	
	@GetMapping("/getBoard")
	public String getBoard(@ModelAttribute("member") Member member, Board board, Model model) {
		if(member.getId() == null) {
			return "redirect:login";
		}
		// 조회수 여기서 하기
		boardService.updateReadCount(board);
		model.addAttribute("board", boardService.getBoard(board));
		return "board/getBoard";
	}
	
	@GetMapping("/updateBoard")
	public String updateBoard(@ModelAttribute("member") Member member, Board board, Model model) {
	    if (member.getId() == null) {
	        return "redirect:login";
	    }

	    model.addAttribute("board", boardService.getBoard(board));
	    return "board/updateBoard";
	}

	
	@PostMapping("/updateBoard")
	public String updateBoard(@ModelAttribute("member") Member member, Board board, @RequestParam("upload") MultipartFile[] files) throws IOException {
		if(member.getId() == null) {
			return "redirect:login";
		}
		
	    // 파일 업로드
	    List<String> uploadFiles = new ArrayList<>();
	    for (MultipartFile uploadFile : files) {
	        if (!uploadFile.isEmpty()) {
	            String upload = uploadFile.getOriginalFilename();
	            uploadFile.transferTo(new File(uploadFolder + upload));
	            uploadFiles.add(upload);
	        }
	    }
	    board.setUploadFiles(uploadFiles);
		
		boardService.updateBoard(board);
		return "redirect:getBoard?seq=" + board.getSeq();
	}
	
	@GetMapping("/deleteBoard")
	public String deleteBoard(@ModelAttribute("member") Member member, Board board) {
		if(member.getId() == null) {
			return "redirect:login";
		}
		boardService.deleteBoard(board);
		return "redirect:getBoardList";
	}
	
    public int updateView(Board board) {
        return boardService.updateReadCount(board);
    }
	
	
	@PostMapping("/updateLikeCount")
	@ResponseBody
	public String updateLikeCount(@RequestBody Board board) {
	    // 서비스에서 좋아요 수를 업데이트함
	    boardService.updateLikedCount(board);
	    return "Success";
	}
	/*
	 * @PostMapping("/updateLikeCount")
	 * 
	 * @ResponseBody public String updateLikeCount(@RequestBody Board board) { Long
	 * boardSeq = board.getSeq(); // 게시글의 일련번호를 가져옴
	 * 
	 * // 리포지토리에서 게시글을 조회하고 좋아요 수를 업데이트함 Board existingBoard =
	 * boardRepository.findById(boardSeq).orElse(null); if (existingBoard != null) {
	 * int likeCount = existingBoard.getLiked() + 1; // 좋아요 수를 1 증가시킴
	 * existingBoard.setLiked(likeCount); boardRepository.save(existingBoard);
	 * return "Success"; } else { return "Error: Board not found"; } }
	 */

	/*
	 * @Autowired private ChatService chatService;
	 * 
	 * @PostMapping("/createChatRoom") public String
	 * createChatRoom(@ModelAttribute("member") Member
	 * member, @RequestParam("room_name") String roomName) { if (member.getId() ==
	 * null) { return "redirect:login"; }
	 * 
	 * // 채팅방 생성 로직을 추가 ChatRoom chatRoom = new ChatRoom();
	 * chatRoom.setRoomName(roomName); // 채팅방 생성 시 필요한 다른 정보들도 설정 가능
	 * 
	 * chatRoom = chatService.createRoom(chatRoom); // ChatService의 createRoom 메소드를
	 * 호출하여 채팅방 생성
	 * 
	 * return "redirect:/chat/room/enter/" + chatRoom.getId(); // 생성된 채팅방으로 이동 }
	 */





	
}










