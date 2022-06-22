/** 
 * --------------------------
 * Programmer: Larry Li
 * Date: June 23th, 2022
 * 
 * This program implements
 * the sprite animation of 
 * the player character, as 
 * well as the walking sound
 * effect played while the 
 * player is walking.
 *  
 * --------------------------
 */


package main;

//imports
import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.audio.Sound;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.util.Duration;
import static com.almasb.fxgl.dsl.FXGL.*;

//create a subclass of component to attach to the player
public class AnimationComponent extends Component {

	//declare variables
	//x and y speed are used to determine which direction the player is facing, and in turn, which animation to play
	private int xSpeed = 0;
	private int ySpeed = 0;
	
	//this determines which idle animation to loop once the player stops moving. It is set to down as the player is 
	//facing down when they initally spawn in
	private String lastFaced = "down";
	
	//boolean for whether or not the hold up animation is playing
	//the animation plays whenever the player obtains a new item, and they should be unable to move during then
	//as such, no new animations should play when hold up is true
	private boolean holdUp = false;
	
	//this is in the animation component class because the code that plays it relies on walk speed
	//the code could be changed to use player velocity and be relocated to the main class, but this works
	public Music walkSound = FXGL.getAssetLoader().loadMusic("walkSound.mp3");

	//declare the different animations that the player will have
	private AnimatedTexture texture;
	private AnimationChannel animIdleUp, animIdleX, animIdleDown, animWalkX, animWalkUp, animWalkDown, animHoldUp;
	
	/**AnimationComponent constructor
	 * Creates a new instance of AnimationComponent
	 * No paramaters 
	 */
	
	public AnimationComponent() {
		//load in all the animations that the player has from resources
		animIdleDown = new AnimationChannel (FXGL.image("idleDown.png"), Duration.seconds(5), 3);
		animIdleX = new AnimationChannel (FXGL.image("idleRight.png"), Duration.seconds(5), 3);
		animIdleUp = new AnimationChannel (FXGL.image("idleUp.png"), Duration.seconds(1), 1);
		animWalkDown = new AnimationChannel (FXGL.image("walkDown.png"), Duration.seconds(0.8), 10);
		animWalkX = new AnimationChannel (FXGL.image("walkRight.png"), Duration.seconds(0.8), 10);
		animWalkUp = new AnimationChannel (FXGL.image("walkUp.png"), Duration.seconds(0.8), 10);
		animHoldUp = new AnimationChannel (FXGL.image("holdingUp.png"), Duration.seconds(1), 1);
		
		//animations play by setting them as the current animated texture
		//set the player to be idling down by default
		texture = new AnimatedTexture(animIdleDown);
	}
	
	 /**
	   * Method Name: onAdded
	   * Runs by default (in the Component class that AnimationComponent is a subclass of)
	   * whenever a new asset is added
	   */
	@Override
	public void onAdded() {
		//set the scale origin of the player
		//this is the midpoint of the player that will be used when mirroring animations 
		//this is used in the left and right walking and idling animations as there is only one animation channel 
		//for those, walkX and idleX, respectivley
	    entity.getTransformComponent().setScaleOrigin(new Point2D(25, 10));
	    //adds the texture to the player
	    entity.getViewComponent().addChild(texture);
	}
	
	 /**
	   * Method Name: onUpdate
	   * Runs by default once a frame
	   * Used to implement game logic
	   * @param tpf: the default double that keeps tracks of frames, stands for Transaction Processing Facility
	   */
	@Override
	public void onUpdate(double tpf) {
		
		//player movement used to be handled here, but that was changed because implementing collisions required
		//the player to have a PhysicsComponent, which required the player to move based on velocity which is 
		//done in the main method
		
	    //play walking animations:
		//checks for the player direction and plays the proper animation
		//ensures the player is not currently stunned
		//repeat for all 4 direction
	    if (xSpeed > 0 && !ZeldaGameApp.isImmobile()) {
	    	
	        if (texture.getAnimationChannel() != animWalkX) {
	            texture.loopAnimationChannel(animWalkX);
	            lastFaced = "right";
	        }

	        //natural brief slowdown of the speed prevents glitches in the animation
	        xSpeed = (int) (xSpeed * 0.4);

	        
	    } else if (xSpeed < 0 && !ZeldaGameApp.isImmobile()) {
	    	
	        if (texture.getAnimationChannel() != animWalkX) {
	            texture.loopAnimationChannel(animWalkX);
	            lastFaced = "left";

	        }

	        xSpeed = (int) (xSpeed * 0.4);

	        
	    }else if (ySpeed < 0 && xSpeed == 0 && !ZeldaGameApp.isImmobile()) {
	    	
	        if (texture.getAnimationChannel() != animWalkUp) {
	            texture.loopAnimationChannel(animWalkUp);
	            lastFaced = "up";

	        }

	        ySpeed = (int) (ySpeed * 0.4);

	        
	    }else if (ySpeed > 0 && xSpeed == 0 && !ZeldaGameApp.isImmobile()) {
	    	
	        if (texture.getAnimationChannel() != animWalkDown) {
	            texture.loopAnimationChannel(animWalkDown);
	            lastFaced = "down";

	        }

	       ySpeed = (int) (ySpeed * 0.4);
	        
	    }
	   
	    
	    //whenever the player stops moving, stop playing the walking sound
	    if(ySpeed == 0 && xSpeed == 0) {
	    	stopWalkSound();
		}
	    
	    
	    //obtain item animation + sfx
	    if(holdUp) {
	    	//play the get item sound effect
	    	FXGL.play("ZeldaGetItemSFX.wav");
	    	
	    	//stop the player from moving
	    	ZeldaGameApp.stun();
	    	
	    	//the audio has a delay, pause for hald a second to sync it
	    	runOnce(() -> {
	    		texture.loopAnimationChannel(animHoldUp);
	    	}, Duration.seconds(0.5));
	    	
	    	//stop the holding up animation after 2 seconds
	    	//unstun the player
	    	runOnce(() -> {
	    		holdUp = false;
	    		ZeldaGameApp.unStun();
	    	}, Duration.seconds(2));
	    	
	    }
	     
	    
	    //play the idle animations
	    //once the player stops moving , check for their last faced direction and play the proper idle animation
	    if (!entity.getComponent(PhysicsComponent.class).isMoving() && lastFaced.equals("up")) {
        	ySpeed = 0;
            texture.loopAnimationChannel(animIdleUp);
        } else if (!entity.getComponent(PhysicsComponent.class).isMoving() && lastFaced.equals("down")) {
        	ySpeed = 0;
            texture.loopAnimationChannel(animIdleDown);
        } else if (!entity.getComponent(PhysicsComponent.class).isMoving() && lastFaced.equals("left")) {
        	xSpeed = 0;
            texture.loopAnimationChannel(animIdleX);
        } else if (!entity.getComponent(PhysicsComponent.class).isMoving() && lastFaced.equals("right")) {
        	xSpeed = 0;
            texture.loopAnimationChannel(animIdleX);
        }
	    	    
	}
	
	 /**
	   * Method Name: moveRight()
	   * sets xSpeed to a positive number so the walkRight animation plays
	   * sets the proper scale for the walkRight animation
	   * No parameters
	   */
	public void moveRight() {
		if(ySpeed == 0 && !ZeldaGameApp.isImmobile()) {
			xSpeed = 1;
			//set the scale so the player animation faces right
			getEntity().setScaleX(1);
	    }
	    
	}

	/**
	   * Method Name: moveLeft()
	   * sets xSpeed to a negative number so the walkLeft animation plays
	   * sets the proper scale for the walkLeft animation
	   * No parameters
	   */
	public void moveLeft() {
		if(ySpeed == 0 && !ZeldaGameApp.isImmobile()) {
			xSpeed = -1;
			//reverse the scale so the player animation faces left
			getEntity().setScaleX(-1);
	    }
	    
	}
	
	/**
	   * Method Name: moveUp()
	   * sets ySpeed to a negative number so the walkUp animation plays
	   * No parameters
	   */
	public void moveUp() {
		if(xSpeed == 0) {
			ySpeed = -1;
		}

	}
	
	/**
	   * Method Name: moveDown()
	   * sets ySpeed to a positive number so the walkDown animation plays
	   * No parameters
	   */
	public void moveDown() {
		if(xSpeed == 0) {
			ySpeed = 1;
		}

	}
	
	/**
	   * Method Name: holdUp()
	   * sets boolean holdUp to true so the holding up animation plays
	   * No parameters
	   */
	public void holdUp() { 
		holdUp = true;
	}
	
	
	/**
	   * Method Name: walkSound()
	   * loops the walking sound effect if the player is not stunned
	   * No parameters
	   */
	public void walkSound() {
		if(!holdUp && !ZeldaGameApp.isImmobile()) {
			FXGL.getAudioPlayer().loopMusic(walkSound);
		}
	}
	
	/**
	   * Method Name: stopWalkSound()
	   * stops playing the walking sound effect 
	   * No parameters
	   */
	public void stopWalkSound() {
		FXGL.getAudioPlayer().stopMusic(walkSound);
	}
	
}
