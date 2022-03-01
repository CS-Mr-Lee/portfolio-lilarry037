/** 
 * --------------------------
 * Programmer: Larry Li
 * Date: March 1st, 2022
 * 
 * Creates a vegetable class 
 * for vegetable with a name,
 * weight, calories, and
 * packaging
 * 
 * also contains a method 
 * for the vegetable to be
 * eaten
 * --------------------------
 */

public class Vegetable {

	/** name of the vegetable*/
	private String name;
	
	/** weight of the vegetable in grams*/
	private double weight;
	
	/** number of calories of the vegetable*/
	private int calories;
	
	//default constructor
	public Vegetable() {
		this.name = "";
		this.weight = -1;
		this.calories = -1;
	}
	
	//creates a new vegetable with all the parameters
	public Vegetable(String name, double weight, int calories) {
		
		this.name = name;
		
		if(weight < 0) {
			this.weight = 0;
		} else {
			this.weight = weight;
		}
		
		if(calories < 0) {
			this.calories = 0;
		} else {
			this.calories = calories;
		}

	}
	
	//accessor methods
	public String getName() {
		return name;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public int getCalories() {
		return calories;
	}

	//mutator methods
	
	/**
	 * a portion of the vegetable is eaten
	 * @param weight: the number of grams of the vegetable being eaten
	 * remove the weight eaten from the weight of the vegetable 
	 * calculates the number of calories in the weight eaten
	 * then removes the calories eaten from the calories of the vegetable
	 * returns -1 if the parameter weight is greater than the weight of the vegetable, 
	 * because then the vegetable cannot be eaten
	 * @return the number of calories in the amount of vegetable eaten
	 * 
	 */
	public int eaten (double weight) {
		if(weight > this.weight) {
			return -1;
		} else {
			
			double removedPercentage = (weight / this.weight);
			double calories = removedPercentage * this.calories;
			this.calories -= calories;
			
			this.weight -= weight;
			
			return (int)calories;
			
		}

	}
	
	/**
	 * returns all the information of the vegtable class in a string
	 */
	public String toString() {
	    String output = "Name: ";
	    output += name;
	    output += "\n";
	    
	    output += "Weight: ";
	    output += weight;
	    output += " g\n";
	        
	    output += "Calories: ";
	    output += calories;
	    output += "\n"; 
	    
	    return output;
	}
	
	
}
