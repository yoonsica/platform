package com.ceit.vic.platform.models;

/**
 * ztree节点对象模型
 * @author VIC
 *
 */
public class ZTreeNode {
	String name;
	int id;
	int pId;
	boolean open=false;
	String isParent="false";
	String href=null;
	String icon;//树节点图标
	String iconCls;//标签页图标css名称
	public ZTreeNode(String name, int id, int pId) {
		super();
		this.name = name;
		this.id = id;
		this.pId = pId;
	}
	public ZTreeNode() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	//String iconOpen="static/ztree/css/zTreeStyle/img/diy/1_open.png";
	//String iconClose="static/ztree/css/zTreeStyle/img/diy/1_close.png";
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public String getIsParent() {
		return isParent;
	}
	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getpId() {
		return pId;
	}
	public void setpId(int pId) {
		this.pId = pId;
	}
	
}
