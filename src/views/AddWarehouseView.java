package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.MDIChild;
import controller.MDIParent;

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
	private Warehouse myWarehouse;
	private WarehouseList warehouseList = new WarehouseList();
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
		this.setTitle("New");
	}

	/**
	 * saves changes to the view's Person model 
	 */
	//if any of them fail then no fields should be changed
	//and previous values reloaded
	//this is called rollback
	public void addWarehouse() {
		//display any error message if field data are invalid
		String testN = fldName.getText().trim();
//		if(!myWarehouse.validName(testN)) {
//			parent.displayChildMessage("Invalid name!");
//			refreshFields();
//			return;
//		}
		String testA = fldAddress.getText().trim();
//		if(!myWarehouse.validAddress(testA)) {
//			parent.displayChildMessage("Invalid addres!");
//			refreshFields();
//			return;
//		}
		String testS = fldState.getText().trim();
//		if(!myWarehouse.validState(testS)) {
//			parent.displayChildMessage("Invalid State!");
//			refreshFields();
//			return;
//		}
		String testC = fldCity.getText().trim();
//		if(!myWarehouse.validCity(testC)) {
//			parent.displayChildMessage("Invalid city!");
//			refreshFields();
//			return;
//		}
		String testZ = fldZip.getText().trim();
//		if(!myWarehouse.validZip(testZ)) {
//			parent.displayChildMessage("Invalid Zip!");
//			refreshFields();
//			return;
//		}
		
		
		int testCap = 0;
		testCap = Integer.parseInt(fldCap.getText());
//		try {
//			testCap = Integer.parseInt(fldCap.getText());
//		} catch(Exception e) {
//			parent.displayChildMessage("Invalid capacity!");
//			refreshFields();
//			return;
//		}
//		if(!myWarehouse.validStorage(testCap)) {
//			parent.displayChildMessage("Invalid capacity!");
//			refreshFields();
//			return;
//		}
		
		//fields are valid so save to model
		try {
			warehouseList.addWarehouseToList(new Warehouse(testN, testA, testS,testC,testZ, testCap));
		} catch(Exception e) {
			parent.displayChildMessage(e.getMessage());
			refreshFields();
			return;
		}
		
		parent.displayChildMessage("Added new warehouse");
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
