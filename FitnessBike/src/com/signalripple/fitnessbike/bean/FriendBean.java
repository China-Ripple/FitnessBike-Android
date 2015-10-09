package com.signalripple.fitnessbike.bean;

public class FriendBean {

	/**用户id*/
	String id ;
	/**名次  排名*/
	int position;
	/**赞的数量*/
	int zanNumber;
	/**头像URL*/
	String pictureUrl;
	/**名字*/
	String name;
	/**公里*/
	String kilometre;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public int getZanNumber() {
		return zanNumber;
	}
	public void setZanNumber(int zanNumber) {
		this.zanNumber = zanNumber;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKilometre() {
		return kilometre;
	}
	public void setKilometre(String kilometre) {
		this.kilometre = kilometre;
	}
	
	
}
