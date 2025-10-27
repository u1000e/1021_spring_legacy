package com.kh.spring.ajax.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.kh.spring.board.model.dto.BoardDTO;
import com.kh.spring.board.model.dto.ReplyDTO;
import com.kh.spring.board.model.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class AjaxController {
	
	/*
	 * 응답할 데이터를 문자열로 반환
	 * ModelAndView의 viewName필드에 return한 문자열값이 대입\
	 * => DispatcherServlet
	 * => ViewResolver
	 * 
	 * 반환하는 String타입의 값이 View의 정보가 아닌 응답데이터라는 것을 명시해서
	 * => MessageConverter라는 빈으로 이동하게끔
	 * 
	 * @ResponseBody 
	 */
	@ResponseBody
	@GetMapping(value="test", produces="text/html; charset=UTF-8")
	public String ajaxReturn(@RequestParam(name="input") String value) {
		log.info("잘넘어옴? {}", value);
		// DB에 잘 다녀왔다고 가정
		// 오늘 점심은 짬뽕이다! ==> 조회해옴
		String lunchMenu = "오늘 점심은 짬뽕이다!";
		return lunchMenu;
	}
	
	private final BoardService boardService;
	
	@Autowired
	public AjaxController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	@ResponseBody
	@PostMapping(value = "replies", produces="text/html; charset=UTF-8")
	public String insertReply(ReplyDTO reply, HttpSession session) {
		// log.info("{}", reply);
		int result = boardService.insertReply(reply, session);
		return result > 0 ? "success" : "fail";
	}
	
	@ResponseBody
	@GetMapping(value="board/{num}", produces="application/json; charset=UTF-8")
	public BoardDTO detail(@PathVariable(value="num") Long boardNo) {
		log.info("게시글 번호 잘 오나용 ㅎ : {}", boardNo);
		BoardDTO board = boardService.findByBoardNo(boardNo);
		log.info("혹시 모르니 찍어봄 : {}", board);
		/*	아 편하다~
		 * {
		 * 	"boardNo" : 19,
		 *  "boardTitle" : "첨부파일있이",
		 *  ...
		 *  "replies" : [
		 *  	{
		 *  		"replyNo" : 5,
		 *  		"replyContent" : "ㅎㅎ"
		 *  		...
		 *  	},
		 *  	{
		 *  	}
		 *  ]
		 * }
		 */
		//return new Gson().toJson(board);
		return board;
	}
	
	
	
	
	
	
	
	

}
