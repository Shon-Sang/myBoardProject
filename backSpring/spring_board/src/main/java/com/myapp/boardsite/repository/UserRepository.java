package com.myapp.boardsite.repository;

import java.util.List;
import java.util.Map;

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
	public int updateUserRefreshToken(Map<String, String> data);
}
