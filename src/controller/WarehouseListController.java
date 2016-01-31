package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractListModel;
import javax.swing.event.ListDataListener;

import models.Warehouse;
import models.WarehouseList;

/**
 * Provides the PersonList data to be used by PersonListView JLists
 * 
 * ListModels are very handy in that they come with functionality to automatically associate
 * a clicked item in the JList with the data behind the list item (i.e., the model)
 *  
 * Declaring our own custom ListModel gives us better control of the list data and abstracts it out of the View
 * Remember, we don't want any one class to have too many Responsibilities (i.e., the SRP principle)
 * 
 * I consider this a Controller class in that it doesn't have any real data of its own. It coordinates communication
 * between the underlying Person objects and the JList. 
 * 
 * Moreover, I would NOT use this as a pure model (i.e., store the Person objects in an instance variable Collection)
 * because it is Swing-specific.
 * 
 * @author marcos
 *
 */
public class WarehouseListController extends AbstractListModel<Warehouse> implements Observer {
	/**
	 * Collection for storing Person object refs
	 * Leave it abstract so we can possibly use different concrete list subclasses
	 */
	private static List<Warehouse> wList;
	
	/**
	 * GUI container housing this object's list controller's JList
	 * Allows this controller to tell the view to repaint() if models in list change
	 */
	private MDIChild myListView;
	
	public WarehouseListController(WarehouseList wl) {
		super();
		wList = wl.getList();
		
		//register as observer with all Person models in list
		registerAsObserver();
	}
	
	@Override
	public int getSize() {
		return wList.size();
	}

	@Override
	public Warehouse getElementAt(int index) {
		if(index > getSize())
			throw new IndexOutOfBoundsException("Index " + index + " is out of list bounds!");
		return wList.get(index);
	}

	public MDIChild getMyListView() {
		return myListView;
	}
	public static void removeWarehouse(Warehouse w) {
		System.err.println(wList);
		if(wList.contains(w)) {
			wList.remove(w);			
		}
		System.err.println(wList);
	}
	public void setMyListView(MDIChild myListView) {
		this.myListView = myListView;
	}

	/**
	 * iterate through model list and register as observer with all observables
	 */
	public void registerAsObserver() {
		//register with the observable models
		for(Warehouse w: wList)
			w.addObserver(this);
	}

	/**
	 * iterate through model list and UNregister as observer with all observables
	 */
	public void unregisterAsObserver() {
		//unregister with the observable models
		for(Warehouse w: wList)
			w.deleteObserver(this);
	}

	//model tells this observer that it has changed
	//so tell JList's view to repaint itself now
	@Override
	public void update(Observable o, Object arg) {
		myListView.repaint();
	}
}
