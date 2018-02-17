package nqueensproject;

/**
 *
 * @author Prodromos Polichroniadis 
 * @AEM 2513
 */

/**
 *
 * Implements the logic of the n*n chess-board.
 * The board is represented by a boolean[n][n] array. In its cells the values stored are true or false.
 * Having false in a cell [x][y] means that no Queen is placed there
 * Having true in a cell [x][y] means that we have a Queen placed in that cell
 * 
 */

public class BoardState {
	
	private boolean[][] board;
	
	public BoardState(boolean[][] b) { //BoardState contstructor where a board is passed into this.board
		this.board = new boolean[b.length][b.length];
		for(int i = 0; i < b.length; i++) {
			for(int j = 0; j < b[i].length; j++) {
				this.board[i][j] = b[i][j];
			}
		}
	}
	
	public BoardState(int n) { //BoardState constructor where int n = board_size
		this.board = new boolean[n][n]; //Initialize a board with n rows and n columns
		for(int i = 0; i < this.board.length; i++) { //Initialize its cell of the board with false meaning that there are no Queens placed on it 
			for(int j = 0; j < this.board[i].length; j++) {
				this.board[i][j] = false; //false = no queen placed in [i,j] cell
			}
		}
	}
	
	public void replaceRow(boolean[] row, int rowNum) {
		boolean[] tempRow = new boolean[row.length]; //Initialize a temporary boolean array with the size of the board (row.length)
		for(int i = 0; i < row.length; i++) { //Copy all the cells of the row[] array into the tempRow[] array
			tempRow[i] = row[i];
		}
		this.board[rowNum] = tempRow; //Replace the row of the board determined by the rowNum integer with the tempRow[]
	}
	
	public void modifyBoard(int x, int y, boolean b) { //Change the boolean value of the [x,y] cell to b (true or false)
		this.board[x][y] = b;
	}
	
	public boolean isOccupied(int x, int y) { //Check the value stored in the [x,y] cell of the board
		return this.board[x][y];
	}
	
	public int getBoardSize() { //Returns board size
		return this.board.length;
	}
	
	public boolean[][] getBoard() { //Return the whole board
		return this.board;
	}
	
	public String toString() { //Method that is used during the printing of the rows of the board
		String r = "["; //String variable that at the end contains all the boolean values of a board row as a string in order to be printed
		for(int i = 0; i < this.board.length; i++) {
			for(int j = 0; j < this.board[i].length; j++) {
				if(j == 0)
					r += this.board[i][j];
				else
					r += ", " + this.board[i][j];
			}
			if(i != (this.board.length - 1))
				r += "]\n[";
			else
				r += "]";
		}
		return r;
	}

}
