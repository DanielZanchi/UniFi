package com.unifi.fatture.app;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.apache.log4j.Logger;

public class User implements Serializable {
	private static final long serialVersionUID = 5897984558305166044L;
	private static final Logger LOGGER = Logger.getLogger(DatabaseUiComunication.class);
	private String id;
	private String name;

	protected int primeNumber = 3;

	public User() {	
	}

	public User(String id, String name) {
		this.setId(id);
		this.setName(name);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		Field[] fields = this.getClass().getDeclaredFields();
		int result = 1;
		for(Field field : fields) {
			field.setAccessible(true);
			int k = 0;
			try {
				k = field.get(this).hashCode();
			} catch (Exception e) {
				LOGGER.error(e);
			}
			result = primeNumber * result + k;
		}
		result = primeNumber*result+((getId()==null) ? 0 : getId().hashCode());
		result = primeNumber*result+((getName()==null) ? 0 : getName().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Field[] fields = this.getClass().getDeclaredFields();
		Field[] fields2 = (this.getClass().cast(obj)).getClass().getDeclaredFields();
		User user = (User)obj;

		if(user.getName()!=getName()||user.getId()!=getId()) {
			return false;
		}else {				
			for(int i = 0; i<fields.length; i++) {
				fields[i].setAccessible(true);
				fields2[i].setAccessible(true);
				try {
					if(!fields[i].get(this).equals(fields2[i].get(obj))) {
						return false;
					}
				} catch (Exception e) {
					LOGGER.error(e);
				}
			}
			return true;
		}
	}
}