package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import model.Game;
import view.ChangelogScreen;
import view.LoadScreen;
import view.MainScreen;
import view.NewGameScreen;
import view.tools.MessageDispatcher;

public class MenuBarListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent ev) {
		switch ( ((JMenuItem) ev.getSource()).getName() ) {
		case "newGame":
			new NewGameScreen();
			break;
		case "saveGame":
			if (Main.getGame() != null)
				Main.getGame().saveGame();
			else
				MessageDispatcher.gameNotStartedMessage();
			break;
		case "loadGame":
			new LoadScreen();
			break;
		case "exit":
			if (MessageDispatcher.displayExitConfirmDialog() == 0)
				System.exit(0);
			break;
		case "Info":
			MessageDispatcher.displayInfo();
			break;
		case "Version":
			new ChangelogScreen();
			break;
		default:
			Main.setLang( ((JMenuItem) ev.getSource()).getName() );
			MainScreen newWindow = new MainScreen();
			newWindow.setSize(Main.getGui().getSize());
			newWindow.setLocationRelativeTo(Main.getGui());
			Main.getGui().dispose();
			Main.setGui(newWindow);
			try {
				Game.prepareReactivatedGame();
			} catch (NullPointerException e) {
				// No game has been initiated yet.
			}
		}
	}

}
