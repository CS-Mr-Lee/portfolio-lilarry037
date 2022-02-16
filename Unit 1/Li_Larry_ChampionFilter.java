/** 
 * --------------------------
 * Programmer: Larry Li
 * Date: Febuary 11th, 2021
 * 
 * This program reads 
 * champions.json and 
 * returns the following:
 * 
 *  - the name and hp of the 
 * champion with the 
 * highest hp
 * 
 *  - the name and armor of 
 *  the champion with the 
 *  lowest armor
 *  
 *  Then it writes those to
 *  MaxChampStats.txt.
 * --------------------------
 */

import java.io.*;

public class Li_Larry_ChampionFilter {

	public static void main(String[] args) {	
		
		
		try {
			//use buffered reader to read the file
			FileReader inputfile = new FileReader("champions.json");
			BufferedReader br = new BufferedReader(inputfile);
			FileWriter outputFile = new FileWriter(new File("MaxChampStats.txt"));
			
			
			//declare variables
			//variables to output
			String highestHPName = null;
			String lowestArmorName = null;
			double highestHP = 0;
			double lowestArmor = 0;
			
			//temporary variables
			String currentChampionName = null;
			double currentArmor = 0;
			double currentHP = 0;
			String currentLine;
			
			//loop through every line of the json file
			while(true) {
				
				//read a line of the file
				currentLine = br.readLine();
				
				//exit the while loop after every line in the file has been read
				if(currentLine == null) {
					inputfile.close();
					br.close();
					break;
				}
				
				//when the read line contains "name":, store the champion name into the temp variable for champion name
				//do the same for "armor": and "hp":
				if(currentLine.contains("\"name\":")) {
					currentChampionName = currentLine.substring(13, currentLine.length() - 2);
				}	
				
				if(currentLine.contains("\"armor\":")) {
					currentArmor = Double.valueOf(currentLine.substring(15, currentLine.length() - 1));
				}
				
				if(currentLine.contains("\"hp\":")) {
					currentHP = Double.valueOf(currentLine.substring(12, currentLine.length() - 1));
				}
				
				//set the armor of the first champion as lowestArmor to begin (no champion has zero armor)
				if(lowestArmor == 0) {
					lowestArmor = currentArmor;
				}
				
				//update lowestArmor and lowestArmorName whenever a new champion has lower armor 
				//do the same with highestHP and highestHPName
				if(currentArmor < lowestArmor) {
					lowestArmor = currentArmor;
					lowestArmorName = currentChampionName;
				}

				if(currentHP > highestHP) {
					highestHP = currentHP;
					highestHPName = currentChampionName;
				}
				
			}

			//write answers to MaxChampStats.txt
			outputFile.write("The champion with the highest HP is " + highestHPName + ", who has " + highestHP + " HP.\n");
			outputFile.write("The champion with the lowest armor is " + lowestArmorName + ", who has " + lowestArmor + " armor.");
			
			//close fileWriter
			outputFile.close();
			
		} catch (IOException e) {
			System.out.println("Could not locate file.");
		}
	
		
	}
}
