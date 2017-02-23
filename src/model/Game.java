package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;

import view.tools.MessageDispatcher;
import controller.Main;

public class Game implements Serializable {

	private static final long serialVersionUID = 3970960802977027955L;
	private Player player;
	private MachinePlayer machine;
	private boolean gameIsStarted;
	private boolean gameIsFinished = false;

	public Player getPlayer() {
		return player;
	}

	public MachinePlayer getMachine() {
		return machine;
	}

	public Game(String playerName, int difficulty) {
		Main.getGui().clearMessageDisplayer();
		Main.getGui().clearBoardListeners();
		Main.getGui().getPlayerBoard().addMouseListener(Main.getGui().getPlayerBoardListener());
		player = new Player(playerName);
		machine = new MachinePlayer(difficulty);
		player.setEnemy(machine);
		machine.setEnemy(player);
		Main.getGui().getPlayerCounterLabel().setText(playerName);
		machine.placeAllShips();
		actionPerformed();
		MessageDispatcher.machineShipsPlacedMessage();
	}
	
	public void startGame() {
		gameIsStarted = true;
		int turn = new Random().nextInt(2);
		MessageDispatcher.firstTurnMessage(turn);
		if (turn == 0)
			machine.machineTurn();
		actionPerformed();
	}
	
	public boolean checkGameFinished() {
		if (player.getShips() == 0)
			MessageDispatcher.displayDefeatedMessage();
		else if (machine.getShips() == 0)
			MessageDispatcher.displayVictoryMessage();
		else 
			return false;
		Main.getGui().clearBoardListeners();
		gameIsFinished = true;
		return true;
	}
	
	public void actionPerformed() {
		Main.getGui().refreshCounters(player, machine);
		Main.getGui().refreshBoards(player.getBoard(), machine.getBoard());
	}
	
	public void saveGame() {
		try {
			new File(Main.appRoot + "/saves").mkdir();
			FileOutputStream fos = new FileOutputStream(Main.appRoot + "/saves/" 
					+ System.currentTimeMillis() + ".sav");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
			MessageDispatcher.gameSavedMessage();
			oos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error. Archivo no encontrado.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error. No se pudo guardar la partida.");
			e.printStackTrace();
		}
	}
	
	public static void loadGame(File saveFile) {
		try {
			FileInputStream fis = new FileInputStream(saveFile);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Main.setGame((Game) ois.readObject());
			ois.close();
			fis.close();
			prepareReactivatedGame();
			MessageDispatcher.gameLoadedMessage();
		} catch (FileNotFoundException e) {
			MessageDispatcher.couldNotLoadGameMessage();
		} catch (ClassNotFoundException e) {
			MessageDispatcher.couldNotLoadGameMessage();
		} catch (IOException e) {
			MessageDispatcher.couldNotLoadGameMessage();
		}
	}
	
	public static void prepareReactivatedGame() {
		Main.getGui().getPlayerCounterLabel().setText(Main.getGame().getPlayer().getName());
		Main.getGui().clearBoardListeners();
		if (Main.getGame().gameIsStarted && !Main.getGame().gameIsFinished)
			Main.getGui().getMachineBoard().addMouseListener(Main.getGui().getMachineBoardListener());
		else if (!Main.getGame().gameIsFinished)
			Main.getGui().getPlayerBoard().addMouseListener(Main.getGui().getPlayerBoardListener());
		Main.getGame().actionPerformed();
	}
	
}
