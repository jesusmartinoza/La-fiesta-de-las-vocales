package mx.betobit.fiestavocales.sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import mx.betobit.fiestavocales.screens.PlayScreen;

/**
 * Created by jesusmartinez on 21/11/16.
 */

public class Balloon extends Sprite {

	private World world;
	private PlayScreen screen;
	private SpriteBatch batch;

	private float duration;
	private Texture spriteSheet;
	private TextureRegion textureRegion;
	private Animation animation;
	private Body body;

	public Balloon(PlayScreen playScreen, Color color, float x, float y) {
		duration = 0;
		this.screen = playScreen;
		this.world = screen.getWorld();
		this.batch = screen.getGame().getBatch();

		setColor(color);
		setPosition(x, y);
		defineBox2dBody();
		defineSpriteSheet();
	}

	/**
	 * Get the frame of the animation and draw it.
	 * @param delta Delta time
	 */
	public void update(float delta) {
		duration += delta;
		TextureRegion frame = animation.getKeyFrame(duration, true);

		// Center sprite in body
		setX(body.getPosition().x - 25);
		setY(body.getPosition().y - 95);

		batch.begin();
		batch.draw(frame, getX(), getY(), 50, 120);
		batch.end();
	}
	/**
	 * Define box2d body for the balloon.
	 */
	private void defineBox2dBody() {
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();

		bdef.type = BodyDef.BodyType.DynamicBody;
		bdef.position.set(getX(), getY());

		body = world.createBody(bdef);
		shape.setRadius(25f);
		fdef.shape = shape;
		fdef.density = 1f;
		fdef.restitution = 0f;
		body.createFixture(fdef);

		body.setLinearVelocity(MathUtils.random(-10, 10), MathUtils.random(0, 20) );
	}

	/**
	 * Get the sprite sheet of the balloon, split it and create the animation.
	 */
	private void defineSpriteSheet() {
		String color = getColor().toString();
		String scarlet = Color.SCARLET.toString();

		if(color.contains(scarlet.substring(0,5)))
			spriteSheet = new Texture("balloons_pink.png");
		else
			spriteSheet = new Texture("balloons_green.png");

		textureRegion = new TextureRegion(spriteSheet, 114, 915);

		TextureRegion[][] splited = textureRegion.split(114, 305);
		TextureRegion[] frames = new TextureRegion[2];

		for (int i = 0; i < 2; i++)
			frames[i] = splited[i][0];
		animation = new Animation(MathUtils.random(0.1f, 0.3f), frames);
	}

	/**
	 * Get box2d body
	 * @return
	 */
	public Body getBody() {
		return body;
	}

	public Texture getSpriteSheet() {
		return spriteSheet;
	}
}