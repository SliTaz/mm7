package com.zbensoft.mmsmp.api.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.zbensoft.mmsmp.api.common.LoginTokenUtil;
import com.zbensoft.mmsmp.api.common.MessageDef;
import com.zbensoft.mmsmp.api.config.spring.securityjwt.JwtAuthenticationResponse;
import com.zbensoft.mmsmp.api.config.spring.securityjwt.JwtTokenUtil;
import com.zbensoft.mmsmp.api.config.spring.securityjwt.JwtUser;
import com.zbensoft.mmsmp.api.service.api.SysRoleUserService;
import com.zbensoft.mmsmp.api.service.api.SysUserService;
import com.zbensoft.mmsmp.db.domain.SysRoleUserKey;
import com.zbensoft.mmsmp.db.domain.SysUser;
import com.zbensoft.mmsmp.db.mapper.SysUserMapper;

@Service
public class SysUserServiceImpl implements SysUserService {
	private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

	@Autowired
	SysUserMapper sysUserMapper;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	UserDetailsService userDetailsService;
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	@Autowired
	SysRoleUserService sysRoleUserService;

	private String tokenHead = MessageDef.LOGIN.TOKEN_HEAD;

	@Override
	public int deleteByPrimaryKey(String userId) {
		return sysUserMapper.deleteByPrimaryKey(userId);
	}

	@Override
	public int insert(SysUser record) {
		return sysUserMapper.insert(record);
	}

	@Override
	public int insertSelective(SysUser record) {
		return sysUserMapper.insertSelective(record);
	}

	@Override
	public int count(SysUser user) {
		return sysUserMapper.count(user);
	}

	@Override
	public SysUser selectByPrimaryKey(String userId) {
		return sysUserMapper.selectByPrimaryKey(userId);
	}

	@Override
	public int updateByPrimaryKeySelective(SysUser record) {
		return sysUserMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SysUser record) {
		return sysUserMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<SysUser> selectPage(SysUser record) {
		return sysUserMapper.selectPage(record);
	}

	@Override
	public boolean isUserExist(SysUser user) {
		return selectByUserName(user.getUserName()) != null;
	}

	@Override
	public void deleteAll() {
		sysUserMapper.deleteAll();
	}

	@Override
	public SysUser selectByUserName(String userName) {
		return sysUserMapper.selectByUserName(userName);
	}

	@Override
	public JwtAuthenticationResponse login(String username, String password) throws Exception {
		try {
			UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
			// Perform the security
			final Authentication authentication = authenticationManager.authenticate(upToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			// Reload password post-security so we can generate token
			final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			final String token = jwtTokenUtil.generateToken(userDetails, username);
			return new JwtAuthenticationResponse(tokenHead + token, ((JwtUser) userDetails).getId(), ((JwtUser) userDetails).getUsername(), username, ((JwtUser) userDetails).getIsDefaultPassword(),
					((JwtUser) userDetails).getIsDefaultPayPassword(), ((JwtUser) userDetails).getEmailBindStatus(),((JwtUser) userDetails).getIsFirstLogin(),((JwtUser) userDetails).getUserLoginType(),((JwtUser) userDetails).getIsClap(),((JwtUser) userDetails).getHasMainOffice(),((JwtUser) userDetails).getAuthorities(), (JwtUser) userDetails);
		} catch (BadCredentialsException be) {
			throw be;
		} catch (UsernameNotFoundException ue) {
			log.warn("login exception"+ue.getMessage());
			throw ue;
		} catch (Exception e) {
			log.error("login exception", e);
			throw e;
		}
	}

	@Override
	public JwtAuthenticationResponse refresh(String oldToken) {
		final String token = oldToken.substring(tokenHead.length());
		String username = jwtTokenUtil.getUsernameFromToken(token);

		if (username != null && !username.isEmpty()) {
			String userType = username.substring(0, 2);
			// String loginType = RedisDef.LOGIN_TYPE.WEB;
			// if (MessageDef.USER_TYPE.CONSUMER_STRING_APP_LOGIN.equals(userType) || MessageDef.USER_TYPE.MERCHANT_STRING_APP_LOGIN.equals(userType)) {
			// loginType = RedisDef.LOGIN_TYPE.APP;
			// }
			String redisKey = LoginTokenUtil.key_TOKEN(username);
			if (LoginTokenUtil.hasKey(redisKey)) {
				if (SecurityContextHolder.getContext().getAuthentication() != null) {
					JwtAuthenticationResponse tokenRedis = LoginTokenUtil.get_TOKEN(redisKey);
					if (tokenRedis != null) {
						JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
						if (user != null && jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
							return new JwtAuthenticationResponse(tokenHead + jwtTokenUtil.refreshToken(token), user.getId(), user.getUsername(), username, user.getIsDefaultPassword(), user.getIsDefaultPayPassword(),
									user.getEmailBindStatus(), user.getIsFirstLogin(), user.getUserLoginType(),user.getIsClap(),user.getHasMainOffice(),user.getAuthorities(), (JwtUser) user);
						}
					}
				}
			}
		}
		return null;
	}

	@Override
	public void logout(String oldToken) {
		if (oldToken != null && oldToken.length() > tokenHead.length()) {
			final String token = oldToken.substring(tokenHead.length());
			String username = jwtTokenUtil.getUsernameFromToken(token);

			if (username != null && !username.isEmpty()) {
				String userType = username.substring(0, 2);
				// String loginType = RedisDef.LOGIN_TYPE.WEB;
				// if (MessageDef.USER_TYPE.CONSUMER_STRING_APP_LOGIN.equals(userType) || MessageDef.USER_TYPE.MERCHANT_STRING_APP_LOGIN.equals(userType)) {
				// loginType = RedisDef.LOGIN_TYPE.APP;
				// }
				LoginTokenUtil.delete_TOKEN(username);
			}
		}
	}

	@Override
	public SysUser register(SysUser userToAdd) {
		final String username = userToAdd.getUserName();
		if (sysUserMapper.selectByUserName(username) != null) {
			return null;
		}
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		final String rawPassword = userToAdd.getPassword();
		userToAdd.setUserId(System.currentTimeMillis() + "");
		userToAdd.setPassword(encoder.encode(rawPassword));
		// TODO: 创建时间
		// userToAdd..setLastPasswordResetDate(new Date());
		sysUserMapper.insert(userToAdd);

		SysRoleUserKey sysRoleUserKey = new SysRoleUserKey();

		sysRoleUserKey.setUserId(userToAdd.getUserId());
		sysRoleUserKey.setRoleId("201");
		sysRoleUserService.insert(sysRoleUserKey);

		return userToAdd;
	}

	public static void main(String[] args) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.matches("111111", encoder.encode("111111")));
	}
}
