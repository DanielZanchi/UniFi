package pizzeriadiddieffe.core;

public interface Item {
	public double getPrice();
	public String getInfo();
	public void add(Item obj) throws Exception;
	public void remove(Item obj) throws Exception;
}