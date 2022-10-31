package com.myapp.boardsite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myapp.boardsite.dto.Board;
import com.myapp.boardsite.repository.BoardRepository;

@Service
public class BoardServiceImp implements BoardService{

	@Autowired
	BoardRepository boardRepository;
	
	public BoardServiceImp() {
	}
	
	@Override
	public List<Board> searchAllBoardData() {
		return boardRepository.selectAllBoard();
	}
	
	@Override
	public int insertBoardData(Board board) {
		return boardRepository.insertBoard(board);
	}
	
	@Override
	public int updateBoardData(Board board) {
		return boardRepository.updateBoard(board);
	}
	
	@Override
	public int deleteBoardData(int id) {
		return boardRepository.deleteBoard(id);
	}
	
	@Override
	public List<Board> serarchBoardData(Board board) {
		return boardRepository.selectBoard(board);
	}
	
	@Override
	public Board serarchIdBoardData(int id) {
		return boardRepository.selectIdBoard(id);
	}
}
