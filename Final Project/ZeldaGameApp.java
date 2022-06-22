/*
 * FXGL - JavaFX Game Library. The MIT License (MIT).
 * Copyright (c) AlmasB (almaslvl@gmail.com).
 * See LICENSE for details.
 */

package main;

//imports
import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;
import static com.almasb.fxgl.dsl.FXGL.getGameController;
import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGL.onBtnDown;
import static com.almasb.fxgl.dsl.FXGL.onCollisionBegin;
import static com.almasb.fxgl.dsl.FXGL.onKey;
import static com.almasb.fxgl.dsl.FXGL.run;
import static com.almasb.fxgl.dsl.FXGL.runOnce;
import static com.almasb.fxgl.dsl.FXGL.showMessage;
import static com.almasb.fxgl.dsl.FXGL.spawn;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsComponent;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Map;

public class ZeldaGameApp extends GameApplication {

	final int WALK_SPEED = 300;
	
	final int MAX_HP = 12;
	
	static String direction = "";
	
    Text HP = new Text();
    
    private static boolean immobile = false;
	
	boolean swordCooldown = false;
	
	private static int startLevel = 1;
	
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("My ICS Culminating");
        settings.setVersion("0.1"); 
        settings.setWidth(1200);
        settings.setHeight(1200);
        settings.setDeveloperMenuEnabled(true);
        settings.setMainMenuEnabled(true);

    }
    
    public enum EntityType {
    	PLAYER, ENEMY, SWORD, ARROW, BARRIER, HAZARD, WARPZONE1, WARPZONE2
    }
    
    private final ZeldaGameAppFactory factory = new ZeldaGameAppFactory();
    
    public static Entity player;
    public static Entity sword;
    public static Entity arrow;
    
    
    UserAction walkUp = new UserAction("walkUp") {
        @Override
        protected void onActionBegin() {
        	direction = "up";
        	if(player.getComponent(PhysicsComponent.class).getVelocityX() == 0 && !immobile) {
        		player.getComponent(PhysicsComponent.class).setVelocityY(-WALK_SPEED); 
        	}
        }

        @Override
        protected void onAction() {
        	
        	if(player.getComponent(PhysicsComponent.class).getVelocityX() == 0 && !immobile) {
        		player.getComponent(PhysicsComponent.class).setVelocityY(-WALK_SPEED); 
        	}
        	
        	player.getComponent(AnimationComponent.class).moveUp();
        	player.getComponent(AnimationComponent.class).walkSound();        
        	
        }

        @Override
        protected void onActionEnd() {
        	player.getComponent(PhysicsComponent.class).setVelocityY(0);
        }
    };
    UserAction walkRight = new UserAction("walkRight") {
        @Override
        protected void onActionBegin() {
        	direction = "right";
        	if(player.getComponent(PhysicsComponent.class).getVelocityY() == 0 && !immobile) {
        		player.getComponent(PhysicsComponent.class).setVelocityX(WALK_SPEED); 
        	}
        }

        @Override
        protected void onAction() {
        	if(player.getComponent(PhysicsComponent.class).getVelocityY() == 0 && !immobile) {
        		player.getComponent(PhysicsComponent.class).setVelocityX(WALK_SPEED); 
        	}
        	player.getComponent(AnimationComponent.class).moveRight();
        	player.getComponent(AnimationComponent.class).walkSound();        
        }
        

        @Override
        protected void onActionEnd() {
        	player.getComponent(PhysicsComponent.class).setVelocityX(0);
        	
        }
    };
    UserAction walkLeft = new UserAction("walkLeft") {
        @Override
        protected void onActionBegin() {
        	direction = "left";
        	if(player.getComponent(PhysicsComponent.class).getVelocityY() == 0 && !immobile) {
        		player.getComponent(PhysicsComponent.class).setVelocityX(-WALK_SPEED); 
        	}
        }

        @Override
        protected void onAction() {
        	if(player.getComponent(PhysicsComponent.class).getVelocityY() == 0 && !immobile) {
        		player.getComponent(PhysicsComponent.class).setVelocityX(-WALK_SPEED); 
        	}
        	player.getComponent(AnimationComponent.class).moveLeft();
        	player.getComponent(AnimationComponent.class).walkSound();        
            
        }

        @Override
        protected void onActionEnd() {
        	player.getComponent(PhysicsComponent.class).setVelocityX(0);
        	

        }
    };
    UserAction walkDown = new UserAction("walkDown") {
        @Override
        protected void onActionBegin() {
        	direction = "down";
        	if(player.getComponent(PhysicsComponent.class).getVelocityX() == 0 && !immobile) {
        		player.getComponent(PhysicsComponent.class).setVelocityY(WALK_SPEED); 
        	}
        }

        @Override
        protected void onAction() {
        	if(player.getComponent(PhysicsComponent.class).getVelocityX() == 0 && !immobile) {
        		player.getComponent(PhysicsComponent.class).setVelocityY(WALK_SPEED); 
        	}    	
        	player.getComponent(AnimationComponent.class).moveDown();
        	player.getComponent(AnimationComponent.class).walkSound();        
        	    
        }
        
        @Override
        protected void onActionEnd() {
        	player.getComponent(PhysicsComponent.class).setVelocityY(0);
        }
    };

    
    @Override
    protected void initInput() {
	
    	Input input = FXGL.getInput();

    	input.addAction(walkRight, KeyCode.D);
    	input.addAction(walkLeft, KeyCode.A);
    	
    	input.addAction(walkUp, KeyCode.W);
    	input.addAction(walkDown, KeyCode.S);

    	
        FXGL.onKey(KeyCode.F, () -> {
            player.getComponent(AnimationComponent.class).holdUp();
        });
        
        
        FXGL.onKeyDown(KeyCode.J, () -> {
        	arrow = getGameWorld().spawn("arrow", new SpawnData(
                    player.getX(),
                    player.getY())
                    .put("direction", launchDirection()));
        
        });
        
        FXGL.onKeyDown(KeyCode.K, () -> {
        	if(swordCooldown == false) {
        		
        		sword = getGameWorld().spawn("sword", player.getX(), player.getY());
        		swordCooldown = true;
        		
        		stun();
                runOnce(() -> {
    	    		unStun();
    	    		sword.removeFromWorld();
    	    		swordCooldown = false;
    	    	}, Duration.seconds(1));
        	}
        	
        });
        
    }
    
	public void stun() {
		immobile = true;
		player.getComponent(PhysicsComponent.class).setVelocityY(0); 
		player.getComponent(PhysicsComponent.class).setVelocityX(0); 
		player.getComponent(AnimationComponent.class).stopWalkSound();
	}
	
	public void unStun() {
		immobile = false;
	}
    
    public static boolean isImmobile() {
    	return immobile;
    }
    
    @Override
    protected void initUI() {
    	
        HP.setTranslateX(50); 
        HP.setTranslateY(50); 

        HP.textProperty().bind(FXGL.getWorldProperties().intProperty("HP").asString());
        
        FXGL.getGameScene().addUINode(HP); 
        
    }
    
    public static Point2D launchDirection() {
    	switch(direction) {
			case("up"):{
				return new Point2D(player.getX(), player.getY() + 50);
			
			}case("right"):{
				return new Point2D(player.getX() - 50, player.getY());
			
			}case("left"):{
				return new Point2D(player.getX() + 50, player.getY());
			
			}default:{
				return new Point2D(player.getX(), player.getY() - 50);
			
			}
    		}
    }
    
    @Override
    protected void initPhysics() {
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.HAZARD) {
            @Override
            protected void onCollision(Entity player, Entity hazard) {
            	FXGL.play("hitSFX.wav");
            	FXGL.inc("HP", -1);
            	
            	switch(direction) {
            		case("up"):{
            			player.getComponent(PhysicsComponent.class).overwritePosition(launchDirection());
            			break;
            		}case("right"):{
            			player.getComponent(PhysicsComponent.class).overwritePosition(launchDirection());
            			break;
            		}case("left"):{
            			player.getComponent(PhysicsComponent.class).overwritePosition(launchDirection());
            			break;
            		}case("down"):{
            			player.getComponent(PhysicsComponent.class).overwritePosition(launchDirection());
            			break;
            		}
            	}
            	
                stun();
                
                runOnce(() -> {
    	    		unStun();
    	    	}, Duration.seconds(1));
	
            }
        });
        
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.ENEMY) {
            @Override
            protected void onCollision(Entity player, Entity enemy) {
            	enemy.removeFromWorld();
            	FXGL.play("hitSFX.wav");
            	FXGL.inc("HP", -2);
            	
            	switch(direction) {
            		case("up"):{
            			player.getComponent(PhysicsComponent.class).overwritePosition(new Point2D(player.getX(), player.getY() + 50));
            			break;
            		}case("right"):{
            			player.getComponent(PhysicsComponent.class).overwritePosition(new Point2D(player.getX() - 50, player.getY()));
            			break;
            		}case("left"):{
            			player.getComponent(PhysicsComponent.class).overwritePosition(new Point2D(player.getX() + 50, player.getY()));
            			break;
            		}case("down"):{
            			player.getComponent(PhysicsComponent.class).overwritePosition(new Point2D(player.getX(), player.getY() - 50));
            			break;
            		}
            	}
            	
                stun();
                
                runOnce(() -> {
    	    		unStun();
    	    	}, Duration.seconds(1));
	
            }
        });
         
        
        //I think this requires a loading screen which requires a new class, not enough time to implement
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.WARPZONE1) {
            @Override
            protected void onCollision(Entity player, Entity warpZone1) {
            	if(FXGL.geti("level") == 1) {
            		FXGL.inc("level", 1);
            		Platform.runLater(() -> initLevel());
            		
            	}else if(FXGL.geti("level") == 2){
            		FXGL.inc("level", -1);
            		Platform.runLater(() -> initLevel());
            		
            	}
            		
            }
        });
               
	 
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.SWORD, EntityType.ENEMY) {
            @Override
            protected void onCollision(Entity sword, Entity enemy) {
            	enemy.removeFromWorld();
            		
            }
        });
        
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.ARROW, EntityType.ENEMY) {
            @Override
            protected void onCollision(Entity arrow, Entity enemy) {
            	arrow.removeFromWorld();
            	enemy.removeFromWorld();
            		
            }
        });

        FXGL.getPhysicsWorld().setGravity(0, 0);
        
    }
    
    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("HP", MAX_HP);
        vars.put("level", startLevel);
    }
    
    @Override
    protected void onUpdate(double tpf) {
    	if(HP.getText().equals("0")) {
    		showMessage("You Died!", () -> {
                getGameController().gotoMainMenu();
                unStun();
            });
    	}
    	
    	if(swordCooldown) {
    		 runOnce(() -> {
 	    		swordCooldown = false;
 	    	}, Duration.seconds(1));
    	}
    }
    
    
    private void initLevel() {
    	FXGL.setLevelFromMap("level" + FXGL.geti("level") +  ".tmx");
    	
    }
    
    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(factory);

        initLevel();
        
        player = getGameWorld().spawn("player", 300, 500);
        FXGL.getGameScene().getViewport().setBounds(0, 0, 1530, 1530);
        FXGL.getGameScene().getViewport().bindToEntity(player, getAppWidth() / 2, getAppHeight() / 2);
        
    }

    public static void main(String[] args) {
    	if (args.length > 0) {
            startLevel = Integer.parseInt(args[0]);
        }
        launch(args);
    }
	
}
