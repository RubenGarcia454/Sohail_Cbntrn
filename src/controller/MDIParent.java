package controller;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import models.Warehouse;
import models.WarehouseList;
import views.AddWarehouseView;
import views.WarehouseDetailView;
import views.WarehouseListView;

/**
 * MasterFrame : a little MDI skeleton that has communication from child to JInternalFrame 
 * 					and from child to the top-level JFrame (MasterFrame)  
 * @author marcos
 *
 */
public class MDIParent extends JFrame {
	private static final long serialVersionUID = 1L;
	private JDesktopPane desktop;
	private int newFrameX = 0, newFrameY = 0; //used to cascade or stagger starting x,y of JInternalFrames
	
	//models and model-controllers
	private WarehouseList WarehouseList;

	//keep a list of currently open views
	//useful if the MDIParent needs to act on the open views or see if an instance is already open
	private List<MDIChild> openViews;
	
	public MDIParent(String title, WarehouseList wList) {
		super(title);
		
		WarehouseList = wList;
		
		//init the view list
		openViews = new LinkedList<MDIChild>();
		
		//create menu for adding inner frames
		MDIMenu menuBar = new MDIMenu(this);
		setJMenuBar(menuBar);
		   
		//create the MDI desktop
		desktop = new JDesktopPane();
		add(desktop);
		
		//add shutdown hook to clean up properly even when VM quits (e.g., Command-Q)
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				closeProperly();
			}
		});

	}
	
	/**
	 * responds to menu events or action calls from child windows (e.g., opening a detail view)
	 * @param cmd Command to perform (e.g., show detail of warehouse object)
	 * @param caller Calling child window reference in case Command requires more info from caller (e.g., selected Warehouse)
	 */
	public void doCommand(MenuCommands cmd, Container caller) {
		switch(cmd) {
			case APP_QUIT :
				//close all child windows first
				closeChildren();
				this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
				break;
			case SHOW_LIST_WAREHOUSE :
				WarehouseListView v1 = new WarehouseListView("Warehouse List", new WarehouseListController(WarehouseList), this);
				//v1.setSingleOpenOnly(true);
				openMDIChild(v1);
				
				break;
			case SHOW_DETAIL_WAREHOUSE :
				Warehouse w = ((WarehouseListView) caller).getSelectedWarehouse();
				WarehouseDetailView v = new WarehouseDetailView(w.getName(), w, this);
				openMDIChild(v);
				break;
			case SHOW_ADD_WAREHOUSE :
				AddWarehouseView v2 = new AddWarehouseView("New", this);
				openMDIChild(v2);
				break;
		}
	}
		


	/**
	 * Close all open children by telling them to close normally (this will fire their own cleanup events)
	 * This is called when Quit is selected on the menu
	 */
	public void closeChildren() {
		JInternalFrame [] children = desktop.getAllFrames();
		for(int i = children.length - 1; i >= 0; i--) {
			children[i].dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
			//as each child window closes, it will call its closeChild() method to clean itself up
		}
	}

	/**
	 * Child windows are already closing so we just need to tell the child panels to clean up.
	 * This can happen when the JVM closes
	 */
	public void cleanupChildPanels() {
		JInternalFrame [] children = desktop.getAllFrames();
		for(int i = children.length - 1; i >= 0; i--) {
			if(children[i] instanceof MDIChildFrame)
				((MDIChildFrame) children[i]).closeChild();
		}
	}
	
	/**
	 * this method will always be called when the app quits since it hooks into the VM
	 */
	public void closeProperly() {
		cleanupChildPanels();
	}

	/**
	 * create the child panel, insert it into a JInternalFrame and show it
	 * @param child
	 * @return
	 */
	public JInternalFrame openMDIChild(MDIChild child) {
		//first, if child's class is single open only and already open,
		//then restore and show that child
		//System.out.println(openViewNames.contains(child));
		if(child.isSingleOpenOnly()) {
			for(MDIChild testChild : openViews) {
				if(testChild.getClass().getSimpleName().equals(child.getClass().getSimpleName())) {
					try {
						testChild.restoreWindowState();
					} catch(PropertyVetoException e) {
						e.printStackTrace();
					}
					JInternalFrame c = (JInternalFrame) testChild.getMDIChildFrame();
					return c;
				}
			}
		}
		
		//create then new frame
		MDIChildFrame frame = new MDIChildFrame(child.getTitle(), true, true, true, true, child);
		
		//pack works but the child panels need to use setPreferredSize to tell pack how much space they need
		//otherwise, MDI children default to a standard size that I find too small
		frame.pack();
		frame.setLocation(newFrameX, newFrameY);
		
		//tile its position
		newFrameX = (newFrameX + 10) % desktop.getWidth(); 
		newFrameY = (newFrameY + 10) % desktop.getHeight(); 
		desktop.add(frame);
		//show it
		frame.setVisible(true);
		
		//add child to openViews
		openViews.add(child);
		
		return frame;
	}
	
	//display a child's message in a dialog centered on MDI frame
	public void displayChildMessage(String msg) {
		JOptionPane.showMessageDialog(this, msg);
	}
	
	/**
	 * When MDIChild closes, we need to unregister it from the list of open views
	 * @param child
	 */
	public void removeFromOpenViews(MDIChild child) {
		openViews.remove(child);
	}
	
}
