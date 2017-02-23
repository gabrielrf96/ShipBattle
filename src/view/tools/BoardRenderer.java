package view.tools;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class BoardRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 8681247668223461898L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, 
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if (value != null && value.equals(1) && table.getName() != "machineBoard") {
			cell.setBackground(Color.GREEN);
			cell.setForeground(Color.GREEN);
		} else if (value != null && value.equals(-1)) {
			cell.setBackground(Color.RED);
			cell.setForeground(Color.RED);
		} else if (value != null && value.equals(0)) {
			cell.setBackground(table.getBackground().darker());
			cell.setForeground(table.getBackground().darker());
		} else {
			cell.setBackground(table.getBackground());
			cell.setForeground(table.getBackground());
		}
		return cell;
	}
}
