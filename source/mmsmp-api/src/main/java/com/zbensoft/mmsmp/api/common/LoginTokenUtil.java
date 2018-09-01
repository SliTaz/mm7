package com.zbensoft.mmsmp.api.common;

import java.util.concurrent.ConcurrentHashMap;

import com.zbensoft.mmsmp.api.config.spring.securityjwt.JwtAuthenticationResponse;

public class LoginTokenUtil {

	private static ConcurrentHashMap<String, JwtAuthenticationResponse> map = new ConcurrentHashMap<>();

	public static void create_TOKEN(String userName, JwtAuthenticationResponse token) {
		map.put(userName, token);
	}

	public static String key_TOKEN(String username) {
		return username;
	}

	public static boolean hasKey(String redisKey) {
		return map.containsKey(redisKey);
	}

	public static JwtAuthenticationResponse get_TOKEN(String redisKey) {
		return map.get(redisKey);
	}

	public static void delete_TOKEN(String username) {
		map.remove(username);
	}

	public static JwtAuthenticationResponse get_TOKEN_NO_UPDATE_TIME(String redisKey) {
		return map.get(redisKey);
	}

}
