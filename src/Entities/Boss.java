package Entities;

import Game.MainGame;
import Tools.Vector2f;
import VFX.Animation;
import VFX.Effect;
import edu.utc.game.Game;
import edu.utc.game.Texture;

import java.util.ArrayList;

public class Boss extends Enemy {
	private boolean destinationReached;

	public Boss(Vector2f destination, String texture) {
		super(destination, texture);
		this.pos = new Vector2f(Game.ui.getWidth() / 2f - 35, -100);
		this.hitbox.setBounds((int) pos.x, (int) pos.y, 70, 70);
		this.health = 300;
		this.speed = .02f;
		this.destinationReached = false;
	}

	@Override
	public void update(int delta) {
		if (health <= 0) die();
		else if (health < 50) texture = new Texture("res/Enemy/eyeopen.png");
		else if (health < 200) texture = new Texture("res/Enemy/eyehalf.png");
		destinationReached = pos.distanceTo(destination) <= .04f;
		if (!destinationReached) {
			goToDestination(delta);
		}

		adjustHitBox();
	}

	private void goToDestination(int delta) {
		Vector2f direction = destination.subtract(pos);
		direction.normalize();
		pos.x += direction.x * speed * delta;
		pos.y += direction.y * speed * delta;
	}

	private void adjustHitBox() {
		hitbox.x = (int) pos.x;
		hitbox.y = (int) pos.y;
	}

	@Override
	protected void die() {
		deactivate();
		ArrayList<String> explosionPics = new ArrayList<String>();
		explosionPics.add("res/Explosion/one.png");
		explosionPics.add("res/Explosion/two.png");
		explosionPics.add("res/Explosion/three.png");
		explosionPics.add("res/Explosion/four.png");
		explosionPics.add("res/Explosion/five.png");
		explosionPics.add("res/Explosion/six.png");
		explosionPics.add("res/Explosion/seven.png");
		explosionPics.add("res/Explosion/eight.png");
		explosionPics.add("res/Explosion/nine.png");
		explosionPics.add("res/Explosion/ten.png");
		Effect explode = new Animation(explosionPics, 100, this.hitbox, "res/Sounds/boom.wav");
		MainGame.effects.add(explode);
	}
}
