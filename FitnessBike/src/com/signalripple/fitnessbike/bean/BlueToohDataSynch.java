package com.signalripple.fitnessbike.bean;

public class BlueToohDataSynch {

	int bikeState;
	int recodeCount;
	int year;
	int month;
	int day;
	int gear;
	int timeInterval;
	double RPM;
	
	
	
	public BlueToohDataSynch(int bikeState, int recodeCount, int year,
			int month, int day, int gear, int timeInterval, double RPM) {
		super();
		this.bikeState = bikeState;
		this.recodeCount = recodeCount;
		this.year = year;
		this.month = month;
		this.day = day;
		this.gear = gear;
		this.timeInterval = timeInterval;
		this.RPM = RPM;
	}
	
	public int getBikeState() {
		return bikeState;
	}
	public void setBikeState(int bikeState) {
		this.bikeState = bikeState;
	}
	public int getRecodeCount() {
		return recodeCount;
	}
	public void setRecodeCount(int recodeCount) {
		this.recodeCount = recodeCount;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getGear() {
		return gear;
	}
	public void setGear(int gear) {
		this.gear = gear;
	}
	public int getTimeInterval() {
		return timeInterval;
	}
	public void setTimeInterval(int timeInterval) {
		this.timeInterval = timeInterval;
	}
	public double getRPM() {
		return RPM;
	}
	public void setRPM(double rPM) {
		RPM = rPM;
	}
	
	
	
}
