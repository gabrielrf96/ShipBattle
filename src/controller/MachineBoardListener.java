package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.SwingUtilities;

public class MachineBoardListener extends MouseAdapter {

	@Override
	public void mousePressed(MouseEvent ev) {
		JTable board = (JTable) ev.getComponent();
		int row = board.rowAtPoint(ev.getPoint());
		int column = board.columnAtPoint(ev.getPoint());
		try {
			if (SwingUtilities.isLeftMouseButton(ev))
				Main.getGame().getPlayer().playerTurn(row, column);
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println("Illegal action attempted");
		}
	}

}
