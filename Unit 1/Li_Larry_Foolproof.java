/** 
 * --------------------------
 * Programmer: Larry Li
 * Date: Febuary 12th, 2021
 * 
 * This program outputs the
 * divident of 2 numbers 
 * inputted by the user, 
 * looping until the user
 * inputs a string beginning
 * with the letter "q". 
 * 
 * The program is also 
 * designed to catch bad 
 * inputs and continue 
 * running until the user
 * exits the program.
 * --------------------------
 */

import java.util.Scanner;
import java.io.*;

public class Li_Larry_Foolproof {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		//declare variables
		double numerator = 0;
		double denominator = 0;
		double quotient;
		boolean valid = false;
		String numeratorString = "";
		String denominatorString = "";

		//loop the entire program until the user quits
	     	do { 
	    	 
			//loop the numerator prompt until the user quits or enters proper data
			do {
				try {  
				// prompt for numerator; get String input
				System.out.print("Enter the numerator: "); 
				numeratorString = sc.nextLine();

				// check to see if program should quit (ensure the first letter of the input is not q)
				//failing both checks means program proceeds
				if (!String.valueOf(numeratorString.charAt(0)).equals("q") && !String.valueOf(numeratorString.charAt(0)).equals("Q")){ 
					//ensure valid is true and then parse the input into a double
					valid = true;
					numerator = Double.parseDouble(numeratorString); 
				}
			    }

			    //catch bad data (inuts that are not a double and do not contain q or Q)
			catch (NumberFormatException e) {
				System.out.println("You entered bad data.");
				System.out.println("Please try again.\n");
				valid = false;
			}

			 }while (valid == false && !numeratorString.substring(0,1).equals("q") && !numeratorString.substring(0,1).equals("Q")); 


			 //do not continue running the code if the user has chosen to exit
			 if (!numeratorString.substring(0,1).equals("q") && !numeratorString.substring(0,1).equals("Q")) {
				 //loop the denominator code just like the numerator code
				 //the code is essentially the same as the code for the numerator input, so no comments
				do {
					try {

						System.out.print("Enter the denominator: "); 
						denominatorString = sc.nextLine();  
						valid = true;
						denominator = Double.parseDouble(denominatorString);      
					} catch (NumberFormatException e) {
						System.out.println("You entered bad data.");
						System.out.println("Please try again.\n");
						valid = false;
				       }
				 } while (valid == false);  
 
				 //attempt to divide the numerator by the denominator
				 try{  
					 valid = true;
					 quotient = (double)(numerator)/denominator;

					 //catch the edge vase of divding by zero
					 if (denominator != 0){

						 //print the output of the division
						 //remove the decimals from integer values to match the sample output
						 //if the quotient is a double, round it to 2 decimal places in the output to match the sample output
						 if (numerator % 1 == 0) {
							 System.out.print((int)numerator);
						 } else {
							 System.out.print(numerator);
						 }

						 System.out.print(" / ");

						 if (denominator % 1 == 0) {
							 System.out.print((int)denominator);
						 } else {
							 System.out.print(denominator);
						 }

						 System.out.print(" is ");

						 if (quotient % 1 == 0) {
							 System.out.println((int)quotient);
						 } else {
							 System.out.printf("%1.2f\n", quotient);
						 }

					 }
					 //catch the edge case of division by zero
					 //also ensure decimals are removed from integers
					 else{

						 System.out.print("You can't divide ");

						 if (numerator % 1 == 0) {
							 System.out.print((int)numerator);
						 } else {
							 System.out.print(numerator);
						 }
						 System.out.println(" by 0");

					 }   
				 }

				 //catch all other cases of impossible divison
				 catch (ArithmeticException e) {

					 System.out.print("You can't divide ");
					 
					 if (numerator % 1 == 0) {
						 System.out.print((int)numerator);
					 } else {
						 System.out.print(numerator);
					 }
					 System.out.print(" by ");
					 if (denominator % 1 == 0) {
						 System.out.print((int)denominator);
					 } else {
						 System.out.print(denominator);
					 }
					 System.out.println(".");
				       valid = false;

				}
				//create a blank line to match the sample output
				System.out.println();
			 }
         
		// continure looping the entire program until the numerator starts with 'Q' or 'q'
		} while (!String.valueOf(numeratorStr.charAt(0)).equals("q") && !String.valueOf(numeratorStr.charAt(0)).equals("Q")); 
		
	}
}
