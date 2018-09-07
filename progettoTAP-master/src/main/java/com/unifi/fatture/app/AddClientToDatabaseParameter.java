package com.unifi.fatture.app;

public class AddClientToDatabaseParameter extends AddToDatabaseParameter {
	private String residence;

	public AddClientToDatabaseParameter(String residence, String city, String zip, String country) {
		super(city, zip, country);
		this.setResidenceForDB(residence);
	}
	
	public String getResidenceForDB() {
		return this.residence;
	}
	
	public void setResidenceForDB(String residence) {
		this.residence = residence;
	}
}