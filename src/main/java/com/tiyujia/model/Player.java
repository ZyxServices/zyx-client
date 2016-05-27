package com.tiyujia.model;

import java.util.Date;

public class Player {
	
	String id;//id
	String name;//中文名字
	String enname;//英文名称
	String nationality;//国籍
	String birthday;//出生日期
	int weight;//体重
	int height;//身高
    String url;//头像
    String club;//俱乐部
    String position;//位置
    String clubNumber;//俱乐部号码
    String nationalTeamNumber;//国家队号码
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEnname() {
		return enname;
	}
	public void setEnname(String enname) {
		this.enname = enname;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getClub() {
		return club;
	}
	public void setClub(String club) {
		this.club = club;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getClubNumber() {
		return clubNumber;
	}
	public void setClubNumber(String clubNumber) {
		this.clubNumber = clubNumber;
	}
	public String getNationalTeamNumber() {
		return nationalTeamNumber;
	}
	public void setNationalTeamNumber(String nationalTeamNumber) {
		this.nationalTeamNumber = nationalTeamNumber;
	}
	@Override
	public String toString() {
		return "name=" + name + ",   nationality=" + nationality + "";
	}
	
	
    
    
    
    
	

}
