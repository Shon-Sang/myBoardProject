package com.myapp.boardsite.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.myapp.boardsite.dto.User;
import com.myapp.boardsite.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// 1. username으로 정보를 찾음
		// 2. 찾은 정보가 null이 아니라면 해당 정보를 리턴
		// 결과적으로 UserDetails를 리턴해야함
		User getUser = userRepository.selectUser(username);
		if(getUser == null) {
			System.out.println("loadUserByUsername메소드에서 DB로부터 일치하는 유저를 못찾음");
			return null;
		}
		return new CustomUserDetails(getUser);
	}
}
