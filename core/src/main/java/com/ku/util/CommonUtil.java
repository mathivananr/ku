package com.ku.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ku.model.User;

public final class CommonUtil {

	public CommonUtil() {
		
	}
	
	public static User getLoggedInUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		return user;
	}
	
	public static String getLoggedInUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		return String.valueOf(user.getId());
	}
	
	public static String getLoggedInUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		return user.getUsername();
	}
}
