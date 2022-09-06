package com.myapp.boardsite.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.myapp.boardsite.dto.User;

@Mapper
@Repository
public interface UserRepository {
	public User selectUser(String username);
	public List<User> selectAllUser();
	public int insertUser(User user);
	public int deleteUser(String username);
}
