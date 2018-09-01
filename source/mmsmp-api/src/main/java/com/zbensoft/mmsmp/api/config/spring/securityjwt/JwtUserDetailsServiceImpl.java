package com.zbensoft.mmsmp.api.config.spring.securityjwt;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.zbensoft.mmsmp.api.common.MessageDef;
import com.zbensoft.mmsmp.api.service.api.SysMenuService;
import com.zbensoft.mmsmp.api.service.api.SysRoleService;
import com.zbensoft.mmsmp.api.service.api.SysRoleUserService;
import com.zbensoft.mmsmp.api.service.api.SysUserService;
import com.zbensoft.mmsmp.db.domain.SysMenuUserMenuParam;
import com.zbensoft.mmsmp.db.domain.SysMenuUserMenuResponse;
import com.zbensoft.mmsmp.db.domain.SysUser;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(JwtUserDetailsServiceImpl.class);

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysRoleUserService sysRoleUserService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysMenuService sysMenuService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String userType = username.substring(0, 2);
		username = username.substring(3);

		if (MessageDef.USER_TYPE.SYS_STRING.equals(userType)) {
			return loadSysUserByUsername(username);
		}
		log.info(String.format("No user found with username '%s'.", username));
		throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
	}

	private UserDetails loadSysUserByUsername(String username) {
		SysUser user = sysUserService.selectByUserName(username);
		if (user == null) {
			log.info(String.format("No user found with username '%s'.", username));
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		}
		boolean isEable = true;
		if (user.getStatus() == null || user.getStatus() == MessageDef.STATUS.DISABLE_INT) {
			isEable = false;
		}
		Integer hasMainOffice = 0;
		return new JwtUser(user.getUserId(), username, user.getUserName(), user.getPassword(), null, null, null,
				getGrantedAuthorities(user), null, isEable, true, MessageDef.DEFAULT_LOGIN_PASSWORD.NOT_DEFUALT,
				MessageDef.DEFAULT_PAY_PASSWORD.NOT_DEFUALT, MessageDef.EMAIL_BIND_STATUS.UN_BIND,
				user.getIsFirstLogin(), MessageDef.MERCHANT_LOGIN_TYPE.PROVIDE, MessageDef.CLAP.NO_CLAP, hasMainOffice,
				MessageDef.JWTUSER_TYPE_NO.PROVIDERT_TYPE_NO, MessageDef.JWTUSER_TYPE_NO.STORE_TYPE_NO,
				MessageDef.JWTUSER_TYPE_NO.EMPLOYEE_TYPE_NO, null, null, null, null, MessageDef.IS_GAS_STATION.NO);
	}

	private List<GrantedAuthority> getGrantedAuthorities(SysUser user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		SysMenuUserMenuParam sysMenuUserMenuParam = new SysMenuUserMenuParam();
		sysMenuUserMenuParam.setUserId(user.getUserId());
		sysMenuUserMenuParam.setMenuType(MessageDef.MENU_TYPE.FUNCTION);
		PageHelper.startPage(1, 100000);
		List<SysMenuUserMenuResponse> sysMenuUserMenuResponseList = sysMenuService.getUserMenus(sysMenuUserMenuParam,
				null);

		if (sysMenuUserMenuResponseList != null && sysMenuUserMenuResponseList.size() > 0) {
			for (SysMenuUserMenuResponse sysMenuUserMenuResponse : sysMenuUserMenuResponseList) {
				authorities.add(new ZBGrantedAuthority("ROLE_" + sysMenuUserMenuResponse.getMenuKeyWord()));
			}
		}
		return authorities;
	}
}
