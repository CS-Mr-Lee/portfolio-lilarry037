/** 
 * --------------------------
 * Programmer: Larry Li
 * Date: March 1st, 2022
 * 
 * Creates a human class with
 * a name, weight, and 
 * energy level
 * 
 * also contains methods to
 * eat a cookie and eat a 
 * vegetable
 * --------------------------
 */
public class Human {

  /** The name of the human */
  private String name;

  /** the weight of the human in kg */
  private double weight;


  /** the energy level of the human as a percentage from 0 to 100 */
  private int energyLevel;

  //default constructor
  public Human() {
	  this.name = "";
	  this.weight = -1;
	  this.energyLevel = -1;
  }

   /** 
    * Creates a new human with the given name, weight, and energyLevel
    */
  public Human(String name, double weight, int energyLevel) {
    
    this.name = name;

    if (weight < 0) {
      this.weight = 0;
    } else {
      this.weight = weight;
    }	
    
    if (energyLevel < 0) {
      this.energyLevel = 0;
    } else if (energyLevel > 100) {
      this.energyLevel = 100;
    } else {
      this.energyLevel = energyLevel;
    }

  }

  //accessor methods

  /** method for the name */
  public String getName() {
    return name;
  }

  /** method for the weight */
  public double getWeight() {
    return weight;
  }

  /** method for the energy level */
  public int getEnergyLevel() {
    return energyLevel;
  }


  //mutator methods

  /** eating a vegetable
   *  @param veg: the vegtable object being eaten
   *  @param grams: the number of grams of the vegetable eaten
   *  contains a check to see if the vegetable has enough grams to fufil the eat request
   *  you cannot eat 100 grams of a 50 gram vegetable; check to ensure that does not happen
   */
  public void eat(Vegetable veg, double grams) {
	  int calories = veg.eaten(grams);
	  
	  if(calories == -1) {
		  System.out.println("I don’t have that much food.");
	  }else {
		  this.weight += grams * 0.001;
		  this.energyLevel += calories / 15;
	  }
	  
  }
  
  /** eating a cookie
   *  @param food: the cookie object being eaten
   *  @param grams: the number of grams of the cookie eaten
   *  similarly to vegetable, check that the weight of the cookie is greater than the parameter grams
   *  add an additional error message for when the cookie is still in it's packaging
   */
  public void eat(Cookie food, double grams) {
	  int calories = food.eaten(grams);
	  
	  if(calories == -2) {
		  System.out.println("I can’t eat the bag.");
	  }else if(calories == -1){
		  System.out.println("I don’t have that much food.");
	  }else {
		  this.weight += grams * 0.001;
		  this.energyLevel += calories / 15;
	  }
  }

  /** energy increases with more sleep 
   *  @param the number of hours slept
   *  hours slept must be positive
   */
  public void sleep(int hours) {
    if(hours > 0){
      energyLevel += hours * 10;
    }
  }

  /** weight and energy decrease for every km run 
   *  @param km: the distance run in km
   *  you cannot run a negative distance, so check input to ensure that does not happen
   */
  public void run(double km) {
    if(km > 0){
      weight -= 0.001 * km;
      energyLevel -= km * 3;
    }
  }

  /** method to collect all parameters in one string using String concatenation*/
  public String toString() {
    String output = "Name: ";
    output += name;
    output += "\n";
    
    output += "Weight: ";
    output += weight;
    output += " kg\n";
        
    output += "Energy Level: ";
    output += energyLevel;
    output += " %\n"; 
    
    return output;
  }
}
