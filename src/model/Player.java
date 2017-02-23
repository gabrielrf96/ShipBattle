package model;

import java.io.Serializable;

import controller.Main;
import view.tools.MessageDispatcher;

public class Player implements Serializable {

	private static final long serialVersionUID = 4054481676221631973L;
	private String name;
	private Integer[][] board;
	private int ships;
	private MachinePlayer enemy;
	
	public String getName() {
		return name;
	}

	public Integer[][] getBoard() {
		return board;
	}

	public Integer getShips() {
		return ships;
	}
	
	public void setEnemy(MachinePlayer enemy) {
		this.enemy = enemy;
	}

	public Player(String name){
		this.name = name;
		board = new Integer[5][5];
		ships = 0;
	}
	
	public int attack(int x, int y){
		if (board[x][y] != null && board[x][y] == 1) {
			board[x][y] = -1;
			ships--;
			for (int[] surroundingBlock: new int[][]{{x-1,y}, {x+1,y}, {x,y-1}, {x,y+1}})
				try {
					if (board[surroundingBlock[0]][surroundingBlock[1]]!= null &&
						board[surroundingBlock[0]][surroundingBlock[1]] == 1)
						return 1;
				} catch (ArrayIndexOutOfBoundsException e) {
					continue;
				}
			return 2;
		} else if (board[x][y] == null) {
			board[x][y] = 0;
		}
		return 0;
	}
	
	public void placeShip(int x, int y) throws Exception{
		if (board[x][y] == null) {
			board[x][y] = 1;
			ships++;
		} else {
			throw new Exception();
		}
	}
	
	public void removeShip(int x, int y) {
		if (board[x][y] != null) {
			board[x][y] = null;
			ships--;
		}
	}
	
	public boolean validateBoard() {
		int twoBlockShips = 0; // Number of blocks forming two-block ships
		int[][] surroundingBlocks = {{0,-1},{0,+1},{-1,0},{+1,0}}; //i,j
		for (int i=0 ; i<board.length ; i++)
			for (int j=0 ; j<board[i].length ; j++)
				if (board[i][j]!=null && board[i][j]==1)
					for (int[] surroundingBlock: surroundingBlocks) {
						int ci = i+surroundingBlock[0];
						int cj = j+surroundingBlock[1];
						try {
							if (board[ci][cj]!=null && board[ci][cj]==1)
								twoBlockShips++;
						} catch (ArrayIndexOutOfBoundsException e) {
							continue;
						}
					}
		return twoBlockShips==2;
	}
	
	public void playerTurn(int row, int col) {
		if (enemy.getBoard()[row][col] != null)
			if (enemy.getBoard()[row][col] == -1 || enemy.getBoard()[row][col] == 0) {
				MessageDispatcher.alreadyAttackedMessage();
				return;
			}
		MessageDispatcher.playerAttackPerformed(enemy.attack(row, col), row, col);
		Main.getGame().actionPerformed();
		if (Main.getGame().checkGameFinished() == false) {
			enemy.machineTurn();
			Main.getGame().actionPerformed();
			Main.getGame().checkGameFinished();
		}
	}
	
}
