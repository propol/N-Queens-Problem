package nqueensproject;

/**
 *
 * @author Prodromos Polichroniadis 
 * @AEM 2513
 */

import java.util.Scanner;

//The main class used to initialize the procedure to solve the NQueens Problem

public class NQueensMain {
	public static void main(String[] args) {

		int num_of_Threads = 0;
                
                System.out.println("Give the number of queens on the board:"); //Take the number of Queens to be placed on the board from the user
                Scanner in = new Scanner(System.in);
                int num_of_Queens = in.nextInt();
                in.close();
                
		long start_timer = System.currentTimeMillis(); //Start the timer to determine the time it took to find the solutions
		
		NQueens q; //Initialize the NQueens object
		if(num_of_Threads != 0) //Use different NQueens constructor based on the number of threads
			q = new NQueens(num_of_Queens, num_of_Threads); 
		else
			q = new NQueens(num_of_Queens);
		
		q.run();
                
		long end_timer = System.currentTimeMillis(); //End the timer
                
		System.out.println("The " + num_of_Queens + "-Queens problem is solved.");
                
                //Calculate the time it took to find the solutions (if there are any) to the NQueens problem
		System.out.println("Time it took to find the solutions: " + ((end_timer - start_timer) / 1000) + " " +
				"seconds and " + (end_timer - start_timer) + " milliseconds");
		
	}
}
