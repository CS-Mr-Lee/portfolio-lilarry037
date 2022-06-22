package main;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;
import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;
import static com.almasb.fxgl.dsl.FXGL.getInput;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

import com.almasb.fxgl.dsl.FXGL;

import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.dsl.components.RandomMoveComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyDef;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import main.ZeldaGameApp.EntityType;

public class ZeldaGameAppFactory implements EntityFactory{


	@Spawns("player")
    public Entity newPlayer(SpawnData data) {
		
		PhysicsComponent physics = new PhysicsComponent();
		physics.setFixtureDef(new FixtureDef().friction(0.5f).density(0.1f));
        BodyDef bd = new BodyDef();
        bd.setFixedRotation(true);
        bd.setType(BodyType.DYNAMIC);
        physics.setBodyDef(bd);
		
        return FXGL.entityBuilder(data)
        		.type(EntityType.PLAYER)
        		.bbox(new HitBox(BoundingShape.box(50,60)))
                .with(new AnimationComponent())
                .with(physics)
                .collidable()
                .build();
    }

	
	@Spawns("barrier")
    public Entity newBarrier(SpawnData data) {
        return FXGL.entityBuilder(data)
        		.type(EntityType.BARRIER)
        		.bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
        		.with(new PhysicsComponent())
                .build();
    }
	
	@Spawns("hazard")
    public Entity newHazard(SpawnData data) {
        return FXGL.entityBuilder(data)
        		.type(EntityType.HAZARD)
        		.bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
        		.collidable()
                .build();
    }
	
	 @Spawns("enemy")
	    public Entity newEnemy(SpawnData data) {
	        Circle circle = new Circle(20, 20, 20, Color.RED);
	        circle.setStroke(Color.BLACK);
	        circle.setStrokeWidth(2.0);

	        return entityBuilder()
	                .type(EntityType.ENEMY)
	                .viewWithBBox(circle)
	                .collidable()
	                .with(new PhysicsComponent())
	                .with(new RandomMoveComponent(
	                    new Rectangle2D(0, 0, 
	                    getAppWidth(), getAppHeight()), 100))
	                .build();
	    }
	
	
	 @Spawns("sword")
	    public Entity newSword(SpawnData data) {
	        
	        return entityBuilder()
	                .type(EntityType.SWORD)
	                .viewWithBBox(new Rectangle(30, 20, Color.BLACK))
	                //.with(new PhysicsComponent())
	                .collidable()
	                .build();
	    }
	 
	 @Spawns("arrow")
	    public Entity newArrow(SpawnData data) {
		 
		 	return entityBuilder()
	                .type(EntityType.ARROW)
	                .viewWithBBox(new Rectangle(10, 5, Color.BLACK))
	                .collidable()
	                .with(new ProjectileComponent(data.get("direction"), 1000))
	                .with(new OffscreenCleanComponent())
	                .build();
	    }
	 
	@Spawns("warpZone1")
    public Entity newWarpZone1(SpawnData data) {
        return FXGL.entityBuilder(data)
        		.type(EntityType.WARPZONE1)
        		.bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
        		.collidable()
                .build();
    }
	
	@Spawns("warpZone2")
    public Entity newWarpZone2(SpawnData data) {
        return FXGL.entityBuilder(data)
        		.type(EntityType.WARPZONE2)
        		.bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
        		.collidable()
                .build();
    }
	
	
	
}
