/** 
 * --------------------------
 * Programmer: Larry Li
 * Date: March 1st, 2022
 * 
 * Creates a cookie class 
 * for cookies with a name,
 * weight, calories, and
 * packaging
 * 
 * also contains a method 
 * for the cookie to be
 * eaten
 * --------------------------
 */
public class Cookie {

	/** name of the cookie*/
	private String name;
	
	/** weight of the cookie in grams*/
	private double weight;
	
	/** number of calories of the cookie*/
	private int calories;
	
	/** whether or not the cookie is packaged*/
	private boolean isPackaged;
	
	//default constructor
	public Cookie() {
		this.name = "";
		this.weight = -1;
		this.calories = -1;
		this.isPackaged = false;
	}
	
	//constructor for an unpackaged cookie
	//isPackaged is false as no parameter is given for packaging
	public Cookie(String name, double weight, int calories) {
		
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
		
		this.isPackaged = false;

	}
	
	//constructor for when boolean isPackaged is provided 
	public Cookie(String name, double weight, int calories, boolean isPackaged) {
		
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
		
		this.isPackaged = isPackaged;

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
	
	public boolean getIsPackaged() {
		return isPackaged;
	}

	//mutator methods
	
	//open the cookie packaging (if it exists)
	//set isPackaged to false
	public void open() {
		isPackaged = false;
	}
	
	
	/**
	 * a portion of the cookie is eaten
	 * @param weight: the number of grams of the cookie being eaten
	 * remove the weight eaten from the weight of the cookie 
	 * calculates the number of calories in the weight eaten
	 * then removes the calories eaten from the calories of the cookie
	 * returns -1 if the parameter weight is greater than the weight of the cookie
	 * returns -2 if the cookie is packaged
	 * because then the cookie cannot be eaten
	 * @return the number of calories in the amount of cookie eaten
	 * 
	 */
	public int eaten (double weight) {
		if(isPackaged) {
			return -2;
		} else if (weight > this.weight) {
			return -1;
		}else{

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
