package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;	

public class WarehouseList {
	
	public static List<Warehouse> Wlist;
	
	public WarehouseList() {
		Wlist = new ArrayList<Warehouse>();
	}
	
	/**
	 * Add a Warehouse object to the list's collection
	 * @param w Warehouse instance to add to the collection
	 */
	public static void addWarehouseToList(Warehouse w) {
		Wlist.add(w);
	}

	/**
	 * Remove a Warehouse from the list
	 * @return Warehouse w if found in list, otherwise null
	 */
	public static Warehouse removeWarehouseFromList(Warehouse w) {
		//System.err.println(Wlist);
		if(Wlist.contains(w)) {
			Wlist.remove(w);	
			return w;
		}
		
		return null;
	}
	
	public List<Warehouse> getList() {
		return Wlist;
	}

	public void setList(List<Warehouse> WList) {
		this.Wlist = WList;
	}
	
}
