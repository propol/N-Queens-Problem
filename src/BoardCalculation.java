package nqueensproject;

/**
 *
 * @author Prodromos Polichroniadis 
 * @AEM 2513
 */

/**
 *
 * Implements the logic of the algorithm's calculations.
 * Each different task tries to find a solution starting from each one of the n possible
 * placements of a queen on the first row.
 * 
 */

import java.util.List;
import java.util.concurrent.Callable;

public class BoardCalculation implements Callable<BoardState> { //We implement Callable and not Runnable as we care about the result returned 
                                                                //Also works well with the ExecutorService 
	
	final BoardState bState; //The final state of the board after modifications are done
	int row_num; //The number of the row being checked in order to determine a Queen placement availability
	List<Boolean> returnList; //Contains the solutions to the N-Queens Problem
	int boardSize; //The size of the board
	
	public BoardCalculation(BoardState boardState, int rowNumber, List<Boolean> rList) { //BoardCalculation Constructor
		this.bState = boardState;
		this.row_num = rowNumber; 
		this.returnList = rList;
		this.boardSize = this.bState.getBoardSize();
	}

	@Override
	public BoardState call() throws Exception { //A task that returns the result of the board calculation and may throw an exception, we also use Callable in order to Override it
		
		BoardState r = recCheck(); //Start the board calculation
		
		if(r == null) //if no solution is found
			return null;
		return r; //if solution is found
	}
	
	private BoardState recCheck() {
		if(this.row_num >= this.boardSize) { //If the number of rows is equal or bigger than the size of the board, print the solution to the N-Queens problem
			System.out.println("New solution: ");
			System.out.println(this.bState);
			BoardState return_bs = new BoardState(this.bState.getBoard()); //Also store the final state of the board as a new BoardState object (return_bs)
		
			synchronized (this.returnList) { //Only one thread has access to the returnList at a time
				this.returnList.add(true); //Add the new solution found
			}
			
			return return_bs; //return the final state of the board to the recursive call returnState = recCheck();
		}
		BoardState returnState = null; //If the previous condition isn't met then create a null BoardState and do the following
		for(int i = 0; i < this.bState.getBoardSize(); i++) { //for each collumn index (= i) of the row_num of the board - from the first to the last one
			if(this.checkUp(this.bState, i, this.row_num) && //If there is no queen on the upper path starting from the collumn index i
			   this.checkUpLeft(this.bState, i, this.row_num) && //AND there is no queen on the upper-left path starting from the collumn index i
			   this.checkUpRight(this.bState, i, this.row_num)) { //AND there is no queen on the upper-right path starting from the collumn index i
				this.bState.modifyBoard(this.row_num, i, true); //THEN modify the row of the board (bState) storing the boolean value true
                                                                                //in the cell [this.row_num, i], meaning that a queen is placed on that cell of the board
				this.row_num++; //Increment this.row_num by 1 because we have to move onto the next row as we have placed a queen on the row_num checked 
				returnState = recCheck(); //Do the same procedure for the next row - recursive call of the method
				this.row_num--; //When we get to this point all the rows have been checked and all queens have been placed
                                                //So we go back to each row (that has a queen) and remove the placed queen 
				
				this.bState.modifyBoard(this.row_num, i, false); //remove the queen by putting false to the cell we previously put in true
			}                                                        //we need to do this in order to return the board state to its initial condition when all cells were false
			else { //if at least one of checkUp, checkUpLeft, checkUpRight didn't return true
				returnState = null; //we are not allowed to put a queen to the [i, this.row_num] cell
			}
		}
		return returnState; //return the final state of the board to the overriden call() method
	}
	
	private boolean checkUpRight(BoardState bs, int colIndex, int rowIndex) { //Check if there is Queen placed at the Up-Right direction path of the colIndex
		for(int i = rowIndex, j = colIndex; i > -1; i--, j++) {
			if(j < this.boardSize) {
				if(bs.isOccupied(i, j)) // isOccupied(i,j) returns true if there is a queen placed at the [i,j] cell on the upper-right path of the board
					return false;  //So, if true, return false meaning that we cannot place a Queen on the colIndex of that rowIndex
			}
		}
		return true;
	}

	private boolean checkUpLeft(BoardState bs, int colIndex, int rowindex) { //Check if there is Queen placed at the Up-Left direction path of the colIndex
		for(int i = rowindex, j = colIndex; i > -1; i--, j--) {
			if(j > -1) {
				if(bs.isOccupied(i, j)) //Same usage as the checkUpRight method with only the direction checked (= Up-Left path) changing
					return false;
			}
		}
		return true;
	}

	private boolean checkUp(BoardState bs, int colIndex, int rowindex) { //Check if there is Queen placed at the Up direction path of the colIndex
		for(int i = 0; i < rowindex; i++) {
			if(bs.isOccupied(i, colIndex)) //Same usage as the checkUpRight method with only the direction checked (= Up path) changing
				return false;
		}
		return true;
	}
	
	public int getRowNumber() { //returns the row number that is being calculated
		return this.row_num;
	}
	
}