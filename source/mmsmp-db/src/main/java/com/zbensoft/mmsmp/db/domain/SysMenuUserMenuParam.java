package com.zbensoft.mmsmp.db.domain;

/**
 * 
 * @author xieqiang
 *
 */
public class SysMenuUserMenuParam {
	private String userId;

    private Integer menuType;

	public Integer getMenuType() {
		return menuType;
	}

	public void setMenuType(Integer menuType) {
		this.menuType = menuType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}