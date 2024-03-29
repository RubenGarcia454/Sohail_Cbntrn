package views;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import models.Warehouse;

/**
 * Customizes row info in JList
 * Don't want to use Person's toString() in JLists. Only want to see each person's full name.
 * @author marcos
 *
 */
public class WarehouseListCellRenderer implements ListCellRenderer<Warehouse> {
	/**
	 * Can use default rendered to keep the visual parts I like (e.g., row height, highlight color, etc.)
	 */
	private final DefaultListCellRenderer DEFAULT_RENDERER = new DefaultListCellRenderer();
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Warehouse> list, Warehouse value, int index,
			boolean isSelected, boolean cellHasFocus) {
		JLabel renderer = (JLabel) DEFAULT_RENDERER.getListCellRendererComponent(list, value.getName(), index, isSelected, cellHasFocus);
		return renderer;
	}

}
