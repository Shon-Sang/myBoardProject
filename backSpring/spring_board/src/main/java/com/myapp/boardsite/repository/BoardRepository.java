package com.myapp.boardsite.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.myapp.boardsite.dto.Board;

@Repository
@Mapper
public interface BoardRepository { // 기본적으로 읽기는 모든 유저가능
	
	public List<Board> selectAllBoard();
	public int insertBoard(Board board);
	public int updateBoard(Board board);
	public int deleteBoard(int id);
	public Board selectIdBoard(int id);
}
