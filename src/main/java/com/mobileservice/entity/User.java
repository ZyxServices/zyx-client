package com.mobileservice.entity;

import java.util.Date;

/**
 * @描述: 用户 .
 * @作者: zhanghr .
 */
public class User {
	
	 /**serialVersionUID TODO*/ 
	
	private static final long serialVersionUID = 1L;
	private String name;
	private String nick;
	private String  passWord;
	private String phone;
	private String url;
	private String address;
	private Date birthday;
	private Date registerTime;
	private Date lastLoginTime;
	private String idcard;
	private Integer type;
	private Integer sex;
	private String receiiptAddress;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Date getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getReceiiptAddress() {
		return receiiptAddress;
	}
	public void setReceiiptAddress(String receiiptAddress) {
		this.receiiptAddress = receiiptAddress;
	}


	@Override
	public String toString() {
		return "User{" +
				"name='" + name + '\'' +
				", nick='" + nick + '\'' +
				", passWord='" + passWord + '\'' +
				", phone='" + phone + '\'' +
				", url='" + url + '\'' +
				", address='" + address + '\'' +
				", birthday=" + birthday +
				", registerTime=" + registerTime +
				", lastLoginTime=" + lastLoginTime +
				", idcard='" + idcard + '\'' +
				", type=" + type +
				", sex=" + sex +
				", receiiptAddress='" + receiiptAddress + '\'' +
				'}';
	}
}