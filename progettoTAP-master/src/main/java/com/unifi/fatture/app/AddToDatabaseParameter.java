package com.unifi.fatture.app;

public class AddToDatabaseParameter {
	private String city;
	private String zip;
	private String country;

	public AddToDatabaseParameter(String city, String zip, String country) {
		this.setCityForDB(city);
		this.setCountryForDB(country);
		this.setZipForDB(zip);
	}
	
	public String getCityForDB() {
		return city;
	}
	
	public void setCityForDB(String city) {
		this.city = city;
	}
	
	public String getZipForDB() {
		return zip;
	}
	
	public void setZipForDB(String zip) {
		this.zip = zip;
	}
	
	public String getCountryForDB() {
		return country;
	}
	
	public void setCountryForDB(String country) {
		this.country = country;
	}
}
