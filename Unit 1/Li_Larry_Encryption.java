/** 
 * --------------------------
 * Programmer: Larry Li
 * Date: Febuary 10th, 2021
 * 
 * This program encrypts a 
 * string with the following
 * specifications: 
 * 
 *  - switch the first and 
 * last characters of every 
 * word
 * 
 *  - shift the middle 
 *  characters to the 
 *  characters two after 
 *  them in the ASCII table
 *  
 *  - keep spaces unchanged
 * --------------------------
 */

import java.util.Scanner;

public class Li_Larry_Encryption {

	public static void main(String[] args) {
		//import scanner
		Scanner sc = new Scanner(System.in);
		//obtain input
		String unencryptedInput = sc.nextLine();
		
		//create an array of all the words in the inputted string
		//and an empty array to store them after they have been encrypted
		String[] unencryptedWords = unencryptedInput.split(" ");
		String[] encryptedWords = new String[unencryptedWords.length];
		
		//encrypt all the words
		//outer for loop to perform the same encryption process on all the words; loop through the array of inputted words
		for(int i = 0; i < unencryptedWords.length; i++) {
			
			//declare variables
			String middleCharacters;
			String encryptedMiddle = "";
			
			//catch words with no middle characters (2 letter words)
			if(unencryptedWords[i].length() > 2) {
				
				//first perform the encryption of the middle characters
				//create an array of chars of the middle characters
				middleCharacters = unencryptedWords[i].substring(1, unencryptedWords[i].length() - 1);
				char[] middleASCII = middleCharacters.toCharArray();
				//temp string to contain the encrypted middle characters
				encryptedMiddle = ""; 
				
				//add 2 to each char to shift their ASCII values up by 2
				for(int k = 0; k < middleASCII.length; k++){
					middleASCII[k] += 2;
					encryptedMiddle = encryptedMiddle + String.valueOf(middleASCII[k]);
				}
				
			}
			
			//catch words without first and last characters (1 letter words)
			if(unencryptedWords[i].length() > 1) {
			
				//use string concatenation to swap the first and last character
				//create a new string of the last character, then concatenate the middle characters, then concatenate the first character
				String encryptedWord = String.valueOf(unencryptedWords[i].charAt(unencryptedWords[i].length() - 1));
				encryptedWord = encryptedWord + encryptedMiddle;
				encryptedWord = encryptedWord + String.valueOf(unencryptedWords[i].charAt(0));
				
				//add the final encrypted word to the arrays of encrypted words
				encryptedWords[i] = encryptedWord;
			}
			//cover edge case
			else {
				encryptedWords[i] = unencryptedWords[i];
			}
			
			
		} //end of outer for loop

		//loop through the array of encrypted words to output the encrypted sentence
		for(String word : encryptedWords) {
			System.out.print(word + " ");
		}
		
		
	}

}
