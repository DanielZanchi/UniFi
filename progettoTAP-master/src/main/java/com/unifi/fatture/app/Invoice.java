package com.unifi.fatture.app;

public class Invoice extends User {
	private static final long serialVersionUID = 6436363366345956837L;
	private String price;
	private String description;

	public Invoice() {
	}

	public Invoice(String id, String name, String price, String description) {
		super(id, name);
		this.setPrice(price);
		this.setDescription(description);
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode(){
		primeNumber = 7;
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
}