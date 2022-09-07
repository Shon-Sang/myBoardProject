package com.myapp.boardsite.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.myapp.boardsite.dto.User;

// 생성자, 인자있는 생성자(UserDetilService에서 리턴해야하니까)
public class CustomUserDetails implements UserDetails{
	
	private User user;
	
	public CustomUserDetails() {
	}
	
	public CustomUserDetails(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}
	// 유저의 권한 목록을 리턴
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<GrantedAuthority>();
		
		collect.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				return user.getAuthRole();
			}
		});
		
		return collect;
	}
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}
	
	@Override
	public String getUsername() {
		return user.getUsername();
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}

}
