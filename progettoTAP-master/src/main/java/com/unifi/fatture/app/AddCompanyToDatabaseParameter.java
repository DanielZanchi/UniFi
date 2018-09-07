package com.unifi.fatture.app;

public class AddCompanyToDatabaseParameter extends AddToDatabaseParameter {
	private String address;

	public AddCompanyToDatabaseParameter(String address, String city, String zip, String country) {
		super(city, zip, country);
		this.setAddressForDB(address);
	}

	public String getAddressForDB() {
		return address;
	}
	
	public void setAddressForDB(String address) {
		this.address = address;
	}
}