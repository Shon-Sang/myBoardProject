package com.myapp.boardsite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.boardsite.dto.Board;
import com.myapp.boardsite.security.CustomUserDetails;
import com.myapp.boardsite.service.BoardService;

@RestController
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	public BoardController() {
	}
	
	@GetMapping("/all")
	public List<Board> getAllBoard(){
		return boardService.searchAllBoardData();
	}
	
	@PostMapping("/add")
	public ResponseEntity<Board> addBoard(@RequestBody Board board, Authentication auth){
		System.out.println("title : " + board.getTitle());
		System.out.println("content : " + board.getContent());
		System.out.println("download : " + board.getDownload());
		board.setWriter(getUserName(auth));
		boardService.insertBoardData(board);
		return ResponseEntity.ok(board);
	}
	
	// react에서 자기거만 바꿀수 있도록 할것? 아니면 여기서 조건을 달아서? (react에서 선택한 글의 writer와 username 비교해서 아예 버튼 자체를 안보여주는식으로)
	// 스프링에서 할 경우, 서비스 단에서 id로 select한 해당 레코드의 writer와 현재 로그인한 username과 같은지 확인
	// id로 구분해서 업데이트
	@PutMapping("/update")
	public ResponseEntity<Board> updateBoard(@RequestBody Board board){
		boardService.updateBoardData(board);
		return ResponseEntity.ok(board);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Board> deleteBoard(@PathVariable("id")int id){
		Board deleteBoard = boardService.serarchIdBoardData(id);
		boardService.deleteBoardData(id);
		return ResponseEntity.ok(deleteBoard);
	}
	
	public String getUserName(Authentication auth) {
		CustomUserDetails customUserDetails = (CustomUserDetails)auth.getPrincipal();
		return customUserDetails.getUsername();
	}
}
