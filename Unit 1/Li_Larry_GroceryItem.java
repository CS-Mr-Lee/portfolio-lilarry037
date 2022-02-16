/** 
 * --------------------------
 * Programmer: Larry Li
 * Date: Febuary 8th, 2021
 * 
 * This program formats 2 
 * inputted grocery items 
 * and their prices.
 * --------------------------
 */

import java.util.Scanner;
public class Li_Larry_GroceryItem {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		//obtain inputs with prompts
		System.out.println("Create a grocery list of 2 items!");
		System.out.println("Input the name of the first grocery item:");
		String firstItem = sc.nextLine();
		
		System.out.println("Input the price of the first grocery item:");
		double firstPrice = sc.nextDouble();
		
		sc.nextLine(); //to catch the "phantom input" 
		
		System.out.println("Input the name of the second grocery item:");
		String secondItem = sc.nextLine();
	
		System.out.println("Input the price of the second grocery item:");
		double secondPrice = sc.nextDouble();
		
		
		//the item names are 20 characters at most
		//determine the number of spaces required to properly space the item prices
		int spacing1 = 20 - firstItem.length();
		int spacing2 = 20 - secondItem.length();
		
		
		//print the formatted output:
		
		System.out.println("---------------------------");
		
		//printing out the first grocery item:
		
		System.out.print(firstItem);
		//print out the proper number of spaces
		//use <= so that if the item name is exactly 20 characters, 
		//there is still a space between the item name and price
		for(int i = 0; i <= spacing1; i++) {
			System.out.print(" ");
		}
		
		//to properly space prices under $10, an extra space must be added 
		//max price that will be entered is $99.99
		//so adding extra code for prices $100+ is not nessecary - this is sufficent
		if(firstPrice < 10) {
			System.out.print(" ");
		}
		
		//print the price with proper 2 decimal place formatting
		//this ensures proper formatting of the price
		System.out.printf("$%1.2f\n", firstPrice);
		
		
		//repeat the process for the second grocery item
		System.out.print(secondItem);
		for(int i = 0; i <= spacing2; i++) {
			System.out.print(" ");
		}
		if(secondPrice < 10) {
			System.out.print(" ");
		}
		System.out.printf("$%1.2f\n", secondPrice);
		System.out.println("---------------------------");
		
	}

}
