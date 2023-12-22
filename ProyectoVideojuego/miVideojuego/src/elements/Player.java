package elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import game.Parametros;
import managers.AudioManager;

public class Player extends Element {
	private Animation<TextureRegion> frente;
	private Animation<TextureRegion> espalda;
	private Animation<TextureRegion> drcha;
	private Animation<TextureRegion> izqda;
	private Animation<TextureRegion> idleLookingRight;
	private Animation<TextureRegion> idleLookingLeft;
	private float velocidad;

	private enum Direction {
		LEFT, RIGHT, NONE
	};

	private Direction lastHorizontalDirection = Direction.NONE;

	public Player(float x, float y, Stage s) {
		super(x, y, s);
		this.velocidad = 35;
	    idleLookingRight = loadFullAnimation("player/IdleLookingRight.png", 1, 2, 0.3f, true);
	    idleLookingLeft = loadFullAnimation("player/IdleLookingLeft.png", 1, 2, 0.3f, true); 

		frente = loadFullAnimation("player/frenteWalk.png", 4, 1, 0.2f, true);
		espalda = loadFullAnimation("player/espaldaWalk.png", 1, 4, 0.2f, true);
		drcha = loadFullAnimation("player/dchaWalk.png", 1, 4, 0.2f, true);
		izqda = loadFullAnimation("player/izqdaWalk.png", 1, 4, 0.2f, true);

		this.setPolygon(8);

	}

	@Override
	public void act(float delta) {
		super.act(delta);

		controles();

		// aplico graviedad

		this.applyPhysics(delta);
		// colocarPies();

	}

	private void controles() {
		if (Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W)) {
			this.velocity.y = velocidad;
			this.setAnimation(espalda);

		} else if (Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S)) {
			this.velocity.y = -velocidad;
			this.setAnimation(frente);

		} else {
			this.velocity.y = 0;
		}

		if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) {
			this.velocity.x = -velocidad;
			this.setAnimation(izqda);
			lastHorizontalDirection = Direction.LEFT;

		} else if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) {
			this.velocity.x = velocidad;
			this.setAnimation(drcha);
			lastHorizontalDirection = Direction.RIGHT;

		} else {
			this.velocity.x = 0;
			if (lastHorizontalDirection == Direction.RIGHT) {
				this.setAnimation(idleLookingRight);
			} else if (lastHorizontalDirection == Direction.LEFT) {
				this.setAnimation(idleLookingLeft);
			}
		}
	}

}
