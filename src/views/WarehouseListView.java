package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import controller.MDIChild;
import controller.MDIParent;
import controller.MenuCommands;
import controller.WarehouseListController;
import models.Warehouse;
import models.WarehouseList;

/**
 * Displays a list of Warehouse objects
 * Double click Person creates and shows detail view of that Person object 

 * NOTE: this view is now coupled with MDI controller classes
 * In order for it to possibly communicate with the MDI master/parent frame
 * @author marcos
 *
 */
public class WarehouseListView extends MDIChild   {	
	/**
	 * GUI instance variables
	 */
	public static int index;
	private JList<Warehouse> listWarehouse;
	private WarehouseListController myList;
	//saves reference to last selected model in JList
	//parent asks for this when opening a detail view
	private Warehouse selectedModel;
	
	/**
	 * Constructor
	 * @param title Window title
	 * @param list PersonListController contains collection of Person objects
	 * @param mdiParent MasterFrame MDI parent window reference
	 */
	public WarehouseListView(String title, WarehouseListController list, MDIParent m) {
		super(title, m);
		
		//set self to list's view (allows ListModel to tell this view to repaint when models change)
		//PersonListController is an observer of the models
		list.setMyListView(this);
		
		//prep list view
		myList = list;
		listWarehouse = new JList<Warehouse>(myList);
		//use our custom cell renderer instead of default (don't want to use Person.toString())
		listWarehouse.setCellRenderer(new WarehouseListCellRenderer());
		listWarehouse.setPreferredSize(new Dimension(200, 150));
		
		//add a Save button to write field changes back to model data
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		JButton button = new JButton("Remove Warehouse");
		button.setVisible(false);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RemoveWarehouse();
			}
		});
		panel.add(button);
		
		this.add(panel, BorderLayout.NORTH);
		//add event handler for double click
		listWarehouse.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				//if double-click then get index and open new detail view with record at that index
		        if(evt.getClickCount() == 2) {
		        	index = listWarehouse.locationToIndex(evt.getPoint());
		        	//get the Person at that index
		        	selectedModel = myList.getElementAt(index);
		        	
		        	//open a new detail view
		        	openDetailView();
		        }
		      //if double-click then get index 
		        if(evt.getClickCount() == 1) {
		        	index = listWarehouse.locationToIndex(evt.getPoint());
		        	//get the Person at that index
		        	selectedModel = myList.getElementAt(index);
		        	button.setVisible(true);
		        } 
		    }


		});
		
		//add to content pane
		this.add(new JScrollPane(listWarehouse));
		
		this.setPreferredSize(new Dimension(300, 200));
	}

	public void RemoveWarehouse() {

		selectedModel = getSelectedWarehouse();
		WarehouseList.removeWarehouseFromList(selectedModel);
		
	}

	/**
	 * Opens a PersonDetailView with the given Person object
	 * @param p Person object to load into the detail view
	 */
	public void openDetailView() {
		parent.doCommand(MenuCommands.SHOW_DETAIL_WAREHOUSE, this);
	}
	
	/**
	 * returns selected person in list
	 * @return
	 */
	public Warehouse getSelectedWarehouse() {
		return selectedModel;
	}

	/**
	 * Subclass-specific cleanup
	 */
	@Override
	protected void childClosing() {
		//let superclass do its thing
		super.childClosing();
				
		//unregister from observables
		myList.unregisterAsObserver();
	}
	
}
