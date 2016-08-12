package com.ku.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
		if(authentication.getPrincipal() instanceof String) {
			return String.valueOf(authentication.getPrincipal());
		} else {
			User user = (User)authentication.getPrincipal();
			return String.valueOf(user.getId());
		}
		
	}
	
	public static String getLoggedInUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication.getPrincipal() instanceof String) {
			return String.valueOf(authentication.getPrincipal());
		} else {
			User user = (User)authentication.getPrincipal();
			return user.getUsername();
		}
	}
	
	public static String getUserIP() {
		ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = sra.getRequest();
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if(StringUtil.isEmptyString(ipAddress)){
			return request.getRemoteAddr();
		} else {
			return ipAddress;
		}
	}
}
