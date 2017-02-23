package view.tools;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import view.MainScreen;
import controller.Main;

public class MessageDispatcher {
	
	public static Integer displayExitConfirmDialog() {
		return JOptionPane.showConfirmDialog(Main.getGui(), Main.lang.getString("confirmExit"), 
				Main.lang.getString("title"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
				new ImageIcon(MainScreen.class.getResource("images/icon.png")));
	}

	public static void displayInfo() {
		JOptionPane.showMessageDialog(Main.getGui(), Main.lang.getString("info1") + "\n"
				+ Main.lang.getString("info2") + " Gabriel Rodríguez Fernández\n" + "Ver. 2.2", 
				Main.lang.getString("title"), JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static String askForPlayerName() {
		String pName = JOptionPane.showInputDialog(Main.getGui(), Main.lang.getString("enterName"), 
				Main.lang.getString("title"), JOptionPane.QUESTION_MESSAGE);
		if (pName != null && pName.length()>12)
			nameExceedsLimitMessage();
		return pName;
	}
	
	public static void nameExceedsLimitMessage() {
		JOptionPane.showMessageDialog(Main.getGui(), 
				Main.lang.getString("nameTooLong"), Main.lang.getString("title"), 
				JOptionPane.INFORMATION_MESSAGE, 
				new ImageIcon(MainScreen.class.getResource("images/info.png")));
	}
	
	public static void machineShipsPlacedMessage() {
		JOptionPane.showMessageDialog(Main.getGui(), 
				Main.lang.getString("machineReady1") + "\n" + Main.lang.getString("validBoardReq")
				+ "\n" + Main.lang.getString("removeShips"), 
				Main.lang.getString("title"), JOptionPane.INFORMATION_MESSAGE, 
				new ImageIcon(MainScreen.class.getResource("images/icon.png")));
	}
	
	public static void shipAlreadyPlacedMessage() {
		JOptionPane.showMessageDialog(Main.getGui(), 
				Main.lang.getString("quadrantOccupied1") + "\n" 
				+ Main.lang.getString("quadrantOccupied2"), Main.lang.getString("quadrantOccupied"), 
				JOptionPane.INFORMATION_MESSAGE, 
				new ImageIcon(MainScreen.class.getResource("images/icon.png")));
	}
	
	public static void invalidBoardConfigurationMessage() {
		JOptionPane.showMessageDialog(Main.getGui(), 
				Main.lang.getString("invalidBoard") + "\n" + Main.lang.getString("validBoardReq")
				+ "\n" + Main.lang.getString("removeShips"),
				Main.lang.getString("title"), JOptionPane.INFORMATION_MESSAGE, 
				new ImageIcon(MainScreen.class.getResource("images/icon.png")));
	}

	public static void allShipsPlacedMessage() {
		JOptionPane.showMessageDialog(Main.getGui(), 
				Main.lang.getString("playerReady"), Main.lang.getString("title"), 
				JOptionPane.INFORMATION_MESSAGE, 
				new ImageIcon(MainScreen.class.getResource("images/info.png")));
	}
	
	public static void firstTurnMessage(int turn) {
		JOptionPane.showMessageDialog(Main.getGui(), 
				Main.lang.getString("first") + " " + (turn == 0 ? Main.getGame().getMachine().getName():
															Main.getGame().getPlayer().getName()), 
				Main.lang.getString("title"), JOptionPane.INFORMATION_MESSAGE, 
				new ImageIcon(MainScreen.class.getResource("images/icon.png")));
	}
	
	public static void alreadyAttackedMessage() {
		JOptionPane.showMessageDialog(Main.getGui(), Main.lang.getString("alreadyAttacked"),
				Main.lang.getString("title"), JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon(MainScreen.class.getResource("images/info.png")));
	}

	public static void machineAttackPerformed(int sunk, int row, int col) {
		Main.getGui().addMessageToDisplayer( 
				Main.lang.getString("machineAttacked") + " " + Main.getGame().getPlayer().getName() 
				+ " " + Main.lang.getString("inQuadrant") + " " + row + "-" + col + "\nWallace " + 
				Main.lang.getString(sunk==2 ? "sunk": (sunk==1 ? "touched":"water"))
			);
	}
	
	public static void playerAttackPerformed(int sunk, int row, int col) {
		Main.getGui().addMessageToDisplayer( 
				Main.getGame().getPlayer().getName() + " " + Main.lang.getString("playerAttacked") + " " +
				Main.lang.getString("inQuadrant") + " " + row + "-" + col + "\n" +
				Main.getGame().getPlayer().getName() + " " + 
				Main.lang.getString(sunk==2 ? "sunk": (sunk==1 ? "touched":"water"))
			);
	}
	
	public static void displayDefeatedMessage() {
		JOptionPane.showMessageDialog(Main.getGui(), 
				Main.lang.getString("defeated1") + "\n" + Main.lang.getString("defeated2"), 
				Main.lang.getString("title"), JOptionPane.INFORMATION_MESSAGE, 
				new ImageIcon(MainScreen.class.getResource("images/icon.png")));
	}
	
	public static void displayVictoryMessage() {
		JOptionPane.showMessageDialog(Main.getGui(), 
				Main.lang.getString("victory1") + "\n" + Main.lang.getString("victory2"), 
				Main.lang.getString("title"), JOptionPane.INFORMATION_MESSAGE, 
				new ImageIcon(MainScreen.class.getResource("images/icon.png")));
	}

	public static void gameSavedMessage() {
		JOptionPane.showMessageDialog(Main.getGui(), 
				Main.lang.getString("gameSaved"), Main.lang.getString("title"), 
				JOptionPane.INFORMATION_MESSAGE, 
				new ImageIcon(MainScreen.class.getResource("images/icon.png")));
	}

	public static void gameLoadedMessage() {
		JOptionPane.showMessageDialog(Main.getGui(), 
				Main.lang.getString("gameLoaded"), Main.lang.getString("title"), 
				JOptionPane.INFORMATION_MESSAGE, 
				new ImageIcon(MainScreen.class.getResource("images/icon.png")));
	}

	public static void gameNotStartedMessage() {
		JOptionPane.showMessageDialog(Main.getGui(), 
				Main.lang.getString("noGame"), Main.lang.getString("title"), JOptionPane.INFORMATION_MESSAGE, 
				new ImageIcon(MainScreen.class.getResource("images/info.png")));
	}

	public static void couldNotLoadGameMessage() {
		JOptionPane.showMessageDialog(Main.getGui(), 
				Main.lang.getString("loadError") + "\n" + Main.lang.getString("fileError"),
				Main.lang.getString("title"), JOptionPane.INFORMATION_MESSAGE, 
				new ImageIcon(MainScreen.class.getResource("images/info.png")));
	}

	public static void changelogMissingMessage() {
		JOptionPane.showMessageDialog(Main.getGui(), 
				Main.lang.getString("changelogMissing") + "\n" + Main.lang.getString("fileError"), 
				Main.lang.getString("title"), JOptionPane.INFORMATION_MESSAGE, 
				new ImageIcon(MainScreen.class.getResource("images/info.png")));
	}

	public static void IOErrorMessage() {
		JOptionPane.showMessageDialog(Main.getGui(), 
				Main.lang.getString("unexpectedError") + "\n" + Main.lang.getString("actionError"), 
				Main.lang.getString("title"), JOptionPane.INFORMATION_MESSAGE, 
				new ImageIcon(MainScreen.class.getResource("images/info.png")));
	}

	public static void langCorruptedMessage() {
		JOptionPane.showMessageDialog(Main.getGui(), 
				"The application has detected a problem. \nThe \"lang\" file is missing, empty or corrupted."
				+ "\nThe game will be initiated in English by default.", 
				"Lang error", JOptionPane.INFORMATION_MESSAGE, 
				new ImageIcon(MainScreen.class.getResource("images/info.png")));
	}
	
	public static void langResourcesMissingMessage(String lang) {
		JOptionPane.showMessageDialog(Main.getGui(), 
				"The application has detected a problem. The LangResources_" + lang + " file is missing.\n"
				+ "The game will be initiated in English by default. You should consider re-downloading\n"
				+ "the game files in order to correct this problem.",
				"Lang error", JOptionPane.INFORMATION_MESSAGE, 
				new ImageIcon(MainScreen.class.getResource("images/info.png")));
	}
	
}
