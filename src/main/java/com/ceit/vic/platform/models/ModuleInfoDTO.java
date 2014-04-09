package com.ceit.vic.platform.models;

public class ModuleInfoDTO {
String name;
int id;
String parent;
String parentName;
String link;
String state;
String icon;
String expand;
public String getParentName() {
	return parentName;
}
public void setParentName(String parentName) {
	this.parentName = parentName;
}
public String getIcon() {
	return icon;
}
public void setIcon(String icon) {
	this.icon = icon;
}
public String getExpand() {
	return expand;
}
public void setExpand(String expand) {
	this.expand = expand;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getParent() {
	return parent;
}
public void setParent(String parent) {
	this.parent = parent;
}
public String getLink() {
	return link;
}
public void setLink(String link) {
	this.link = link;
}
public String getState() {
	return state;
}
public void setState(String state) {
	this.state = state;
}

}
