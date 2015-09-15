package com.signalripple.fitnessbike.bean;

public class ActionBean {

	String name;
	String time;
	String[] rule;
	String[] image;
	int wantJoinPeople;
	int aleadyJoinPeople;
	public ActionBean(String name, String time, String[] rule, String[] image,
			int wantJoinPeople, int aleadyJoinPeople) {
		super();
		this.name = name;
		this.time = time;
		this.rule = rule;
		this.image = image;
		this.wantJoinPeople = wantJoinPeople;
		this.aleadyJoinPeople = aleadyJoinPeople;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String[] getRule() {
		return rule;
	}
	public void setRule(String[] rule) {
		this.rule = rule;
	}
	public String[] getImage() {
		return image;
	}
	public void setImage(String[] image) {
		this.image = image;
	}
	public int getWantJoinPeople() {
		return wantJoinPeople;
	}
	public void setWantJoinPeople(int wantJoinPeople) {
		this.wantJoinPeople = wantJoinPeople;
	}
	public int getAleadyJoinPeople() {
		return aleadyJoinPeople;
	}
	public void setAleadyJoinPeople(int aleadyJoinPeople) {
		this.aleadyJoinPeople = aleadyJoinPeople;
	}
	
	
	

	
}
