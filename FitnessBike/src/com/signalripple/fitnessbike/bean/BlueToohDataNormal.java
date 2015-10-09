package com.signalripple.fitnessbike.bean;

public class BlueToohDataNormal {

	float version;
	/**阻力*/
	float gear;
	int bikeState;
	float proID;
	boolean currentTimeRequest;
	float girth;
	double RPM;
	int timeInterval;
	double distance;
	float calorie;
	
	
	

	
	public BlueToohDataNormal(float version, float gear, int bikeState, float proID,
			boolean currentTimeRequest, float girth, double rPM,
			int timeInterval, double distance, float calorie) {
		super();
		this.version = version;
		this.gear = gear;
		this.bikeState = bikeState;
		this.proID = proID;
		this.currentTimeRequest = currentTimeRequest;
		this.girth = girth;
		RPM = rPM;
		this.timeInterval = timeInterval;
		this.distance = distance;
		this.calorie = calorie;
	}



	@Override
	public String toString() {
		return "BlueToohData [version=" + version + ", gear=" + gear
				+ ", bikeState=" + bikeState + ", proID=" + proID
				+ ", currentTimeRequest=" + currentTimeRequest + ", girth="
				+ girth + ", RPM=" + RPM + ", timeInterval=" + timeInterval
				+ ", distance=" + distance + ", calorie=" + calorie + "]";
	}


	public boolean isCurrentTimeRequest() {
		return currentTimeRequest;
	}
	public void setCurrentTimeRequest(boolean currentTimeRequest) {
		this.currentTimeRequest = currentTimeRequest;
	}


	public int getBikeState() {
		return bikeState;
	}
	public void setBikeState(int bikeState) {
		this.bikeState = bikeState;
	}
	public float getGirth() {
		return girth;
	}
	public void setGirth(float girth) {
		this.girth = girth;
	}

	public double getRPM() {
		return RPM;
	}
	public void setRPM(double rPM) {
		RPM = rPM;
	}
	public int getTimeInterval() {
		return timeInterval;
	}
	public void setTimeInterval(int timeInterval) {
		this.timeInterval = timeInterval;
	}

	public float getVersion() {
		return version;
	}


	public void setVersion(float version) {
		this.version = version;
	}


	public float getGear() {
		return gear;
	}


	public void setGear(float gear) {
		this.gear = gear;
	}


	public float getProID() {
		return proID;
	}


	public void setProID(float proID) {
		this.proID = proID;
	}


	public double getDistance() {
		return distance;
	}


	public void setDistance(double distance) {
		this.distance = distance;
	}


	public float getCalorie() {
		return calorie;
	}
	public void setCalorie(float calorie) {
		this.calorie = calorie;
	}
	
	
	
}
