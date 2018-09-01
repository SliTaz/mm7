package com.zbensoft.mmsmp.db.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author xieqiang
 *
 */
public class SysMenuUserMenuResponse extends SysMenu {

	private List<SysMenuUserMenuResponse> nodes = new ArrayList<SysMenuUserMenuResponse>();
	private String activeClass;

	public SysMenuUserMenuResponse(SysMenu sysMenu) {
		setMenuId(sysMenu.getMenuId());
		setMenuKeyWord(sysMenu.getMenuKeyWord());
		setMenuSortno(sysMenu.getMenuSortno());
		setMenuNameCn(sysMenu.getMenuNameCn());
		setMenuNameEn(sysMenu.getMenuNameEn());
		setMenuNameEs(sysMenu.getMenuNameEs());
		setMenuPic(sysMenu.getMenuPic());
		setMenuType(sysMenu.getMenuType());
		setPreMenuId(sysMenu.getPreMenuId());
		setMenuProces(sysMenu.getMenuProces());
		setRemark(sysMenu.getRemark());
	}

	public List<SysMenuUserMenuResponse> getNodes() {
		return nodes;
	}

	public void setNodes(List<SysMenuUserMenuResponse> nodes) {
		this.nodes = nodes;
	}

	public String getActiveClass() {
		return activeClass;
	}

	public void setActiveClass(String activeClass) {
		this.activeClass = activeClass;
	}

}