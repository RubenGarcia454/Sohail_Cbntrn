package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.MDIChild;
import controller.MDIParent;
import controller.WarehouseListController;
import models.Warehouse;
import models.WarehouseList;

/**
 * Editable view of Person model
 * Displays all field info for a single Person 
 * User can save info by clicking "Save" button
 * 
 * NOTE: this view is now coupled with MDI controller classes
 * In order for it to possibly communicate with the MDI master/parent frame
 * @author marcos
 *
 */
public class AddWarehouseView extends MDIChild implements Observer {
	/**
	 * Warehouse object shown in view instance
	 */
	public static Warehouse myWarehouse;
	
	/**
	 * Fields for Warehouse data access
	 */
	private JLabel fldId;
	private JTextField fldName, fldAddress, fldState, fldCity, fldZip;
	private JTextField fldCap;
	
	/**
	 * Constructor
	 * @param title
	 */
	public AddWarehouseView(String title,  MDIParent m) {
		super(title, m);
		
		//prep layout and fields
		JPanel panel = new JPanel(); 
		panel.setLayout(new GridLayout(7, 2));
		
		//init fields to record data
		panel.add(new JLabel("Id"));
		fldId = new JLabel("X");
		panel.add(fldId);

		panel.add(new JLabel("Warehouse Name"));
		fldName = new JTextField("");
		panel.add(fldName);
		
		panel.add(new JLabel("Address"));
		fldAddress = new JTextField("");
		panel.add(fldAddress);
		
		panel.add(new JLabel("State"));
		fldState = new JTextField("");
		panel.add(fldState);
		
		panel.add(new JLabel("City"));
		fldCity = new JTextField("");
		panel.add(fldCity);

		panel.add(new JLabel("Zip"));
		fldZip = new JTextField("");
		panel.add(fldZip);
		
		panel.add(new JLabel("Capacity"));
		fldCap = new JTextField("");
		panel.add(fldCap);
		
		this.add(panel);
		
		//add a Save button to write field changes back to model data
		panel = new JPanel();
		panel.setLayout(new FlowLayout());
		JButton button = new JButton("Add Record");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addWarehouse();
			}
		});
		panel.add(button);
		
		this.add(panel, BorderLayout.SOUTH);

		//load fields with model data
		refreshFields();
		
		//can't call this on JPanel
		//this.pack();
		this.setPreferredSize(new Dimension(500, 300));
	}
	
	/**
	 * Reload fields with model data
	 * Used when model notifies view of change
	 */
	
	public void refreshFields() {

		fldId.setText("");
		fldName.setText("");
		fldAddress.setText("");
		fldState.setText("");
		fldCity.setText("");
		fldZip.setText("");
		fldCap.setText("");
		//update window title
		this.setTitle("New Warehouse");
	}

	/**
	 * saves changes to the view's Person model 
	 */
	//if any of them fail then no fields should be changed
	//and previous values reloaded
	//this is called rollback
	public void addWarehouse() {
		//display any error message if field data are invalid
		
		if (fldName.getText().equals("")){
			parent.displayChildMessage("Empty Name!");
		}
		else if (fldAddress.getText().equals("")){
			parent.displayChildMessage("Empty Address!");
		}
		else if (fldState.getText().equals("")){
			parent.displayChildMessage("Empty State!");
		}
		else if (fldCity.getText().equals("")){
			parent.displayChildMessage("Empty City!");
		}
		else if (fldZip.getText().equals("")){
			parent.displayChildMessage("Empty Zip!");
		}
		else if (fldCap.getText().equals("")){
			parent.displayChildMessage("Empty Capacity!");
		}
		
		String testN = fldName.getText().trim();
		if(!validName(testN)) {
			parent.displayChildMessage("Invalid name!");
			refreshFields();
			return;
		}
		String testA = fldAddress.getText().trim();
		 if(!validAddress(testA)) {
			parent.displayChildMessage("Invalid addres!");
			refreshFields();
			return;
		}
		String testS = fldState.getText().trim();
		if(!validState(testS)) {
			parent.displayChildMessage("Invalid State!");
			refreshFields();
			return;
		}
		String testC = fldCity.getText().trim();
		if(!validCity(testC)) {
			parent.displayChildMessage("Invalid city!");
			refreshFields();
			return;
		}
		String testZ = fldZip.getText().trim();
		if(!validZip(testZ)) {
			parent.displayChildMessage("Invalid Zip!");
			refreshFields();
			return;
		}
	
		
		int testCap = 0;
		
		testCap = Integer.parseInt(fldCap.getText());
		
//		try {
//			testCap = Integer.parseInt(fldCap.getText());
//		} catch(Exception e) {
//			parent.displayChildMessage("Invalid capacity!");
//			refreshFields();
//			return;
//		}
		if(validStorage(testCap)) {
			parent.displayChildMessage("Invalid capacity!");
			refreshFields();
			return;
		}
		
		Warehouse newWarehouse = new Warehouse(testN, testA, testS,testC,testZ, testCap); 
		//fields are valid so save to model
		
		try {
			//WarehouseListController.addWarehouseToList(newWarehouse);
			WarehouseList.addWarehouseToList(newWarehouse);
//			WarehouseList.Wlist.add(WarehouseListController.wList.size()+1,newWarehouse);
		} catch(Exception e) {
			parent.displayChildMessage(e.getMessage());
			refreshFields();
			return;
		}
		
		parent.displayChildMessage("Added new warehouse");
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
		
		
	/**
	 * Subclass-specific cleanup
	 */
	@Override
	protected void childClosing() {
		//let superclass do its thing
		super.childClosing();
	
	}

	/**
	 * Called by Observable
	 */
	@Override
	public void update(Observable o, Object arg) {
		refreshFields();
	}

}
