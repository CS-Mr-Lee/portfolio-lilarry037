/** 
 * --------------------------
 * Programmer: Larry Li
 * Date: Febuary 9th, 2021
 * 
 * This program returns the 
 * smallest angle in a  
 * triangle given 2 sides 
 * and their contained angle.
 * --------------------------
 */

import java.util.Scanner;
public class Li_Larry_SASTriangleSolver {

	/**
	 * calculates and returns the smallest angle in a triangle
	 * @param firstSide is one side of the triangle
	 * @param secondSide is a second side of the triangle
	 * @param containedAngle is the angle in between the two given sides of the triangle
	 * @return the smallest angle in the triangle
	 */
	
	public static double smallestAngle(double firstSide, double secondSide, double containedAngle) {
		
		double side1 = firstSide;
		double side2 = secondSide;
		double angleA = containedAngle;
		
		//cosine law: c^2 = a^2 + b^2 -2abcosC
		//to solve for c, square root both sides
		//use to calculate the length of the third side:
		double side3 = Math.sqrt(Math.pow(side1, 2) + Math.pow(side2, 2) - 2 * side1 * side2 * Math.cos(angleA));
		
		//use sin law to obtain the other 2 angles:
		//sin law: sinA/a = sinB/b
		//to solve for the missing angles, rearrange the formula:
		//angle A = arcsin(asinB/b)
		double angleB = Math.asin(side1 * Math.sin(angleA) / side3);
		double angleC = Math.asin(side2 * Math.sin(angleA) / side3);
			
		//determine which of the three angles is the smallest:
		double smallestAngle = angleA;
				
		if(smallestAngle > angleB) {
				smallestAngle = angleB;
		}
				
		if(smallestAngle > angleC) {
			smallestAngle = angleC;
		}
		
		//the smallest angle is to be output in degrees, so convert smallestAngle to degrees and then print it
		smallestAngle = Math.toDegrees(smallestAngle);
		
		return smallestAngle;
		
	}
	
	
	public static void main(String[] args) {
		
		//import scanner
		Scanner sc = new Scanner(System.in);
		
		//obtain inputs with prompts
		System.out.println("Calculate the smallest angle in a triangle!");
		System.out.println("Enter the length of the first side:");
		double firstSide = sc.nextDouble();
		
		System.out.println("Enter the length of the second side:");
		double secondSide = sc.nextDouble();
		
		System.out.println("Enter the angle in between the two sides, in radians:");
		double containedAngle = sc.nextDouble();
		
		//use the smallestAngle method to find the smallest angle in the triangle
		
		double smallestAngle = smallestAngle(firstSide, secondSide, containedAngle);
		
		
		System.out.println("The smallest angle in the triangle is: " + smallestAngle + " degrees.");
		
		
	}

}
