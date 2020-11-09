package com.lti.dto;

public class HomeDto {
	String city;
	int cityId;
	int numberOfRooms;
	int delete;
	
	public int getDelete() {
		return delete;
	}
	public void setDelete(int delete) {
		this.delete = delete;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public int getNumberOfRooms() {
		return numberOfRooms;
	}
	public void setNumberOfRooms(int numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}
	
}
