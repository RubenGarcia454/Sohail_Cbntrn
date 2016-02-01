package controller;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import models.Warehouse;
import models.WarehouseList;
import views.WarehouseDetailView;
import views.WarehouseListView;

/**
 * Controller class for 
 * @author marcos
 *
 */
public class Launcher {

	/**
	 * Configures and Launches initial view(s) of the application on the Event Dispatch Thread
	 */
	public static void createAndShowGUI() {
		//init model(s)
		
		//init view(s)
		WarehouseList warehouseList = new WarehouseList();
		//add a few people
		WarehouseList.addWarehouseToList(new Warehouse("Bob's Bed", "15011 Jones Maltsberger Rd", "New Mexico","Alaster","54161", 2500));
		WarehouseList.addWarehouseToList(new Warehouse("Sue's Sofa", "11482 Perrin Beitel Rd", "California","San Andreas","6136",4000));
		WarehouseList.addWarehouseToList(new Warehouse("Tom's TV", "13919 Nacogdoches Rd", "Texas","San Antonio","35042",200));
		//students have no money
		MDIParent appFrame = new MDIParent("Assignment 1", warehouseList);
		
		//use exit on close if you only want windowClosing to be called (can abort closing here also)
		//appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//use dispose on close if you want windowClosed to also be called
		appFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//need to set initial size of MDI frame
		appFrame.setSize(800, 640);
		
		appFrame.setVisible(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

}
