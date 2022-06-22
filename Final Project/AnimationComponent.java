package main;

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


public class AnimationComponent extends Component {

	private int xSpeed = 0;
	private int ySpeed = 0;
	private String lastFaced = "down";
	private boolean holdUp = false;
	
	//this is in the animation component class because the code relies on walk speed
	public Music walkSound = FXGL.getAssetLoader().loadMusic("walkSound.mp3");


	private AnimatedTexture texture;
	private AnimationChannel animIdleUp, animIdleX, animIdleDown, animWalkX, animWalkUp, animWalkDown, animHoldUp;
	
	public AnimationComponent() {
		animIdleDown = new AnimationChannel (FXGL.image("idleDown.png"), Duration.seconds(5), 3);
		animIdleX = new AnimationChannel (FXGL.image("idleRight.png"), Duration.seconds(5), 3);
		animIdleUp = new AnimationChannel (FXGL.image("idleUp.png"), Duration.seconds(1), 1);
		animWalkDown = new AnimationChannel (FXGL.image("walkDown.png"), Duration.seconds(0.8), 10);
		animWalkX = new AnimationChannel (FXGL.image("walkRight.png"), Duration.seconds(0.8), 10);
		animWalkUp = new AnimationChannel (FXGL.image("walkUp.png"), Duration.seconds(0.8), 10);
		animHoldUp = new AnimationChannel (FXGL.image("holdingUp.png"), Duration.seconds(1), 1);
		
		texture = new AnimatedTexture(animIdleDown);
	}
	
	@Override
	public void onAdded() {
	    entity.getTransformComponent().setScaleOrigin(new Point2D(25, 10));
	    entity.getViewComponent().addChild(texture);
	}
	
	@Override
	public void onUpdate(double tpf) {
		//prevent diagonal movement by ensuring translation of the player only occurs in 1 axis at a time
	    
	    //play walking animations:
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
	    	FXGL.play("ZeldaGetItemSFX.mp3");
	    	
	    	//stop the player from moving
	    	xSpeed = 0;
		    ySpeed = 0;
	    	
	    	//the audio has a delay, this is to sync it
	    	runOnce(() -> {
	    		texture.loopAnimationChannel(animHoldUp);
		    	
	    	}, Duration.seconds(0.5));
	    	
	    	runOnce(() -> {
	    		holdUp = false;
	    	}, Duration.seconds(2));
	    	
	    }
	     
	    
	    //idle animations
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
	
	public void moveRight() {
		if(ySpeed == 0 && !ZeldaGameApp.isImmobile()) {
			xSpeed = 400;
			getEntity().setScaleX(1);
	    }
	    
	}

	public void moveLeft() {
		if(ySpeed == 0 && !ZeldaGameApp.isImmobile()) {
			xSpeed = -400;
			getEntity().setScaleX(-1);
	    }
	    
	}
	
	public void moveUp() {
		if(xSpeed == 0) {
			ySpeed = -400;
		}

	}

	public void moveDown() {
		if(xSpeed == 0) {
			ySpeed = 400;
		}

	}
	
	//to play the animation of link holding up an item
	public void holdUp() { 
		holdUp = true;
	}
	
	
	//play the walking sound 
	public void walkSound() {
		if(!holdUp && !ZeldaGameApp.isImmobile()) {
			FXGL.getAudioPlayer().loopMusic(walkSound);
		}
	}
	
	public void stopWalkSound() {
		FXGL.getAudioPlayer().stopMusic(walkSound);
	}
	
	
}
