package com.signalripple.fitnessbike.bean;

public class SuperBiker {
String imageUrl;
String name;

public SuperBiker(String name,String imgURL)
{
	this.name = name;
	this.imageUrl = imgURL;
}

public String getImageUrl() {
	return imageUrl;
}
public void setImageUrl(String imageUrl) {
	this.imageUrl = imageUrl;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}

}
