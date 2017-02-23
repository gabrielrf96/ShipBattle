package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import view.tools.MessageDispatcher;

public class MachinePlayer extends Player implements Serializable {
	
	private static final long serialVersionUID = 6229395779929224224L;
	private int intelligence; // Equivalent to game difficulty (0 easy ; 1 medium ; 2 hard)
	private Player enemy;
	private List<String> forbiddenBlocks;
	private List<String> prioritaryBlocks;
	private Random randomizer = new Random();
	private int x;
	private int y;
	
	public void setEnemy(Player enemy) {
		this.enemy = enemy;
	}
	
	public MachinePlayer(int intelligence) {
		super("Wallace");
		this.intelligence = intelligence;
		forbiddenBlocks = new ArrayList<String>();
		prioritaryBlocks = new ArrayList<String>();
		x = 0;
		y = 0;
	}
	
	public void placeAllShips() {
		placeTwoBlockShip();
		for (int i=0 ; i<3 ; i++)
			try {
				x = randomizer.nextInt(5);
				y = randomizer.nextInt(5);
				if (!forbiddenBlocks.contains(x + "" + y))
					this.placeShip(x, y);
				else
					throw new Exception();
			} catch (Exception e) {
				i--;
			}
		forbiddenBlocks.clear();
	}
	
	private void placeTwoBlockShip() {
		x = randomizer.nextInt(5);
		y = randomizer.nextInt(5);
		try {
			this.placeShip(x, y);
			for (String forbiddenBlock: forbiddenBlocks)
				try {
					x = Integer.parseInt(forbiddenBlock.substring(0,1));
					y = Integer.parseInt(forbiddenBlock.substring(1,2));
					this.placeShip(x, y);
					break;
				} catch (Exception e) {
					continue;
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void placeShip(int x, int y) throws Exception {
		super.placeShip(x, y);
		updateForbiddenBlocks();
	}

	public void machineTurn() {
		switch (intelligence) {
		case 0:
			easy();
			break;
		case 1:
			medium();
			break;
		case 2:
			hard();
			break;
		}
	}

	private void easy() {
		randomizeAttack();
		performAttack();
	}

	private void medium() {
		randomizeAttack();
		int attackResult = performAttack();
		if (attackResult == 2)
			updateForbiddenBlocks();
	}

	private void hard() {
		if (!prioritaryBlocks.isEmpty())
			checkPrioritaryBlocks();
		else
			randomizeAttack();
		int attackResult = performAttack();
		if (attackResult == 1) {
			updatePrioritaryBlocks();
			updateForbiddenBlocks();
		} else if (attackResult == 2) {
			prioritaryBlocks.clear();
			updateForbiddenBlocks();
		}
	}

	private void checkPrioritaryBlocks() {
		String pos;
		do {
			pos = prioritaryBlocks.get(0);
			try {
				x = Integer.parseInt(pos.substring(0, 1));
				y = Integer.parseInt(pos.substring(1, 2));
			} catch (NumberFormatException e) {
				//A "-" sign was found, and tried to convert it to a number
			}
			prioritaryBlocks.remove(0);
		} while (pos.length() != 2 || enemy.getBoard()[x][y] != null &&
				(enemy.getBoard()[x][y] == 0 || enemy.getBoard()[x][y] == -1));
	}

	private void randomizeAttack() {
		do {
			x = randomizer.nextInt(5);
			y = randomizer.nextInt(5);
		} while (enemy.getBoard()[x][y] != null && 
				 (enemy.getBoard()[x][y] == 0 || enemy.getBoard()[x][y] == -1) ||
				 forbiddenBlocks.contains(x + "" + y));
	}

	private int performAttack() {
		int attackResult = enemy.attack(x, y);
		MessageDispatcher.machineAttackPerformed(attackResult, x, y);
		return attackResult;
	}
	
	private void updateForbiddenBlocks() {
		for (int[] block: new int[][]{{x+1,y},{x-1,y},{x,y-1},{x,y+1}})
			forbiddenBlocks.add(block[0] + "" + block[1]);
	}
	
	private void updatePrioritaryBlocks() {
		for (int[] block: new int[][]{{x+1,y},{x-1,y},{x,y-1},{x,y+1}})
			prioritaryBlocks.add(block[0] + "" + block[1]);
	}

}
