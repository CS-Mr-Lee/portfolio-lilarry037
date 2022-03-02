/** 
 * --------------------------
 * Programmer: Larry Li
 * Date: March 1st, 2022
 * 
 * Creates 1 human,
 * 1 vegetable, and 
 * 3 cookies. Have the human
 * perform the following 
 * actions:
 * 
 * - try to eat a packaged 
 * cookie
 * - try to eat too much of 
 * a vegetable
 * - eat a cookie and gain 
 * energy
 *  
 * --------------------------
 */
public class Li_Larry_OOQuiz1 {

	public static void main(String[] args) {
		
		Human person1 = new Human("Larry", 85, 50);
		
		Vegetable vegetable1 = new Vegetable("Celery", 300, 20);
		
		Cookie cookie1 = new Cookie();
		Cookie cookie2 = new Cookie("Chocolate chip", 200, 500);
		Cookie cookie3 = new Cookie("Shortbread", 100, 300, true);

		person1.eat(cookie3, 100);
		person1.eat(vegetable1, 500);
		person1.eat(cookie2, 100);


	}

}
