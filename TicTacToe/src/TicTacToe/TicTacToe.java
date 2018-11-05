package TicTacToe;

import java.util.Scanner;

// our file

public class TicTacToe {
	public static void main(String[] args){
		
		int[] x = { 4, 11, -3, 0, 46, 11, 9, -77, 3, 11};
		search(x);
		
	}
	public static void search( int [] x) {
		Scanner input = new Scanner(System.in);
		System.out.printf("Enter a number: ");
		int userInput = input.nextInt();
		for(int i = 0; i<10; i++) {
			if (userInput == x[i]) {
				System.out.printf("%d", x[i]);
				System.exit(0);
			}
			else {
				System.out.printf("-1");
				System.exit(0);
			
			}
			i++;
		}
		
	} 
}
