package com.myapp.boardsite.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.myapp.boardsite.dto.User;

public interface LoginService {
	public Map<String, String> SignIn(User user);
	public Map<String, String> refresh(Map<String, String> map, HttpServletResponse response);
}
