package com.myapp.boardsite.service;

import java.util.List;

import com.myapp.boardsite.dto.Board;

public interface BoardService {
	public List<Board> searchAllBoardData();
	public int insertBoardData(Board board);
	public int updateBoardData(Board board);
	public int deleteBoardData(int id);
	public List<Board> serarchBoardData(Board board);
	public Board serarchIdBoardData(int id);
}
