package nqueensproject;

/**
 *
 * @author Prodromos Polichroniadis 
 * @AEM 2513
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * Implements the logic of the N-Queens Algorithm.
 * 
 */
public class NQueens {
	ExecutorService service;
	int board_size; //integer that keeps the size of the board determined by the number of Queens that the user gave us
	List<Boolean> returnList = new ArrayList<Boolean>();
        
        
	public NQueens(int size) {
		this.service = Executors.newCachedThreadPool(); //Creates a thread pool that creates new threads as needed, 
                                                                //but will reuse previously constructed threads when they are available
                                                                //threads that have not been used for 60 seconds are terminated and removed from the cache
		this.board_size = size;
	}
        
	public NQueens(int size, ExecutorService s) { // Not used but created for default options
		this.service = s;
		this.board_size = size;
	}
	
	public NQueens(int size, int nThreads) {
		this.service = Executors.newFixedThreadPool(nThreads); //Creates a thread pool that reuses a fixed number of threads operating off a shared unbounded queue. 
                                                                       //At any point, at most nThreads threads will be active processing tasks. 
                                                                       //If additional tasks are submitted when all threads are active, they will wait in the queue until a thread is available. 
                                                                       //If any thread terminates due to a failure during execution prior to shutdown, a new one will take its place if needed to execute subsequent tasks.
		this.board_size = size;
	}
	
	public void run() {
		
		for(int i = 0; i < this.board_size; i++) {
			boolean[] temp = new boolean[this.board_size]; //Create a boolean array using the size of the board - represents the first row of the board
			temp[i] = true; //Place a Queen (true = Queen placed) at the i-cell of the array
			BoardState bs = new BoardState(this.board_size); //Initialize new board state (new board)
			bs.replaceRow(temp, 0); //Replaces the 0 row (the first row) of the BoardState object using the temp[] array and the replaceRow method of the BoardState Class
			
			BoardCalculation bc = new BoardCalculation(bs, 1, this.returnList); //Initialize new board calculation

			try {
                               this.service.submit(bc).get(); // Sends one state of the board to another thread, 
                                                              // in order for that thread to calculate the permitted placements of the N-Queens.
                                                              // get() used to catch the ExecutionException
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			
		}
			

		this.service.shutdown(); //shutdown the ExecutorService after waiting for the remaining tasks to finish
		while(!this.service.isTerminated()) {} //while-loop used in order to wait for the remaining tasks to finish  
		int count = 0;
		if(this.returnList.isEmpty()) { //Check if there are no solutions
			System.out.println("No solutions have been found.");
		}
                else{
                    for(Boolean b : this.returnList) { //Count how many solutions have been found
                            if(b != null) {
                                count++;			
                            }
                    }
                    System.out.println("Number of solutions: " + count); //Print the number of solutions
                }
	}	
}
