package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.SwingUtilities;

import view.tools.MessageDispatcher;

public class PlayerBoardListener extends MouseAdapter{

	@Override
	public void mousePressed(MouseEvent ev) {
		JTable board = (JTable) ev.getComponent();
		int row = board.rowAtPoint(ev.getPoint());
		int column = board.columnAtPoint(ev.getPoint());
		if (SwingUtilities.isRightMouseButton(ev)) {
			Main.getGame().getPlayer().removeShip(row, column);
			Main.getGame().actionPerformed();
		} else {
			if (Main.getGame().getPlayer().getShips() < 4)
				placeShip(row, column);
			else if (Main.getGame().getPlayer().getShips() == 4 && placeShip(row, column) == true)
				initiateGame();
		}
	}
	
	private void initiateGame() {
		if (Main.getGame().getPlayer().validateBoard()) {
			Main.getGui().getPlayerBoard().removeMouseListener(this);
			Main.getGui().getMachineBoard().addMouseListener(Main.getGui()
					.getMachineBoardListener());
			MessageDispatcher.allShipsPlacedMessage();
			Main.getGame().startGame();
		} else {
			MessageDispatcher.invalidBoardConfigurationMessage();
		}
	}
	
	private boolean placeShip(int row, int col) {
		try {
			Main.getGame().getPlayer().placeShip(row, col);
			Main.getGame().actionPerformed();
			return true;
		} catch (NullPointerException e) {
			System.out.println("Illegal action attempted");
		} catch (Exception e) {
			MessageDispatcher.shipAlreadyPlacedMessage();
		} return false;
	}

}
