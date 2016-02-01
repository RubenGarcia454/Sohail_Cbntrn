package models;

import java.util.Observable;

public class Warehouse extends Observable {
	
	private static long nextId = 1;
	private long id;
	private String name, address, state, city,zipcode;
	private int capacity;

	public Warehouse(){
		id = WarehouseList.Wlist.size() + nextId++;
		name=address=state=city=zipcode= "";
		capacity = 0;
	}
	
	public Warehouse(String n, String a, String s,String c, String z,int cap) {
		this();
		//validate parameters
		if(!validName(n))
			throw new IllegalArgumentException("Invalid name!");
		if(!validAddress(a))
			throw new IllegalArgumentException("Invalid Address!");
		if(!validZip(z))
			throw new IllegalArgumentException("Invalid Zipcode!");
		if(!validStorage(cap))
			throw new IllegalArgumentException("Invalid Capacity!");
		if(!validState(s))
			throw new IllegalArgumentException("Invalid State!");
		if(!validCity(c))
			throw new IllegalArgumentException("Invalid City!");
		
		name= n;
		address= a;
		state= s;
		city= c;
		zipcode= z;
		capacity = cap;
		
	}
	
	
	//TODO check string for alphanumerics and symbols	
	public boolean validCity(String c) {
		if (c == null)
				return false;
		if (c.length() > 100)
				return false;	
		return true;
	}
	
	//TODO check string for alphanumerics and symbols
	public boolean validState(String s) {
		if (s == null)
			return false;
		if (s.length() > 50)
			return false;	
		return true;
	}

	public boolean validStorage(int cap) {
		if (cap < 0)
			return false;
		return true;
	}

	public boolean validZip(String z) {
		try
	    {
	      int i = Integer.parseInt(z);
	    }
	    catch(NumberFormatException nfe)
	    {
	      return false;
	    }
		if(z.length() > 5)
			return false; 
		return true;
	}

	public boolean validAddress(String a) {
		if (a == null)
			return false;	
		return true;
	}
	//TODO check string for Uniqueness
	public boolean validName(String n) {
		if (n == null)
			return false;
		if (n.length() > 250)
			return false;	
		return true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		//get ready to notify observers (notify is called in finishUpdate())
				setChanged();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
		//get ready to notify observers (notify is called in finishUpdate())
				setChanged();
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
		//get ready to notify observers (notify is called in finishUpdate())
				setChanged();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
		//get ready to notify observers (notify is called in finishUpdate())
				setChanged();
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
		//get ready to notify observers (notify is called in finishUpdate())
				setChanged();
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
		//get ready to notify observers (notify is called in finishUpdate())
				setChanged();
	}

	/**
	 * Returns the Person's hopefully unique id
	 * @return
	 */
	public long getId() {
		return id;
	}
	
	
	/**
	 * Tells the model that update has finished so it can finish the update
	 * E.g., notify observers
	 */
	public void finishUpdate() {
		notifyObservers();
	}
	
}
