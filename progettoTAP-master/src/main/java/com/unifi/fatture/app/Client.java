package com.unifi.fatture.app;

public class Client extends User {	
	private static final long serialVersionUID = 392147957593130325L;
	private String fiscalCode;
	private String cityResidence;
	private String city;
	private String zip;
	private String country;
	private String phone;
	private String email;

	public Client() {
	}

	public Client(String id, String name, String fiscalCode, String cityResidence, String city, String zip, String country) {
		super(id, name);
		this.setFiscalCode(fiscalCode);
		this.setCityResidence(cityResidence);
		this.setCity(city);
		this.setZip(zip);
		this.setCountry(country);
	}

	public void setExtraParameters(String phone, String email) {
		this.setPhone(phone);
		this.setEmail(email);
	}

	public String getFiscalCode() {
		return fiscalCode;
	}

	public void setFiscalCode(String fiscalCode) {
		this.fiscalCode = fiscalCode;
	}

	public String getCityResidence() {
		return cityResidence;
	}

	public void setCityResidence(String cityResidence) {
		this.cityResidence = cityResidence;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}


	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public int hashCode(){
		primeNumber = 5;
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
}