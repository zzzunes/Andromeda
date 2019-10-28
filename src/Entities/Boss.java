package Entities;

import Game.MainGame;
import Tools.Vector2f;
import Visuals.Effect;
import edu.utc.game.Game;
import edu.utc.game.Texture;

import java.awt.*;
import java.util.ArrayList;

public class Boss extends Enemy {
	public Boss(Vector2f destination, String texture) {
		super(destination, texture);
		this.pos = new Vector2f(Game.ui.getWidth() / 2f - 35, -100);
		this.hitbox.setBounds((int) pos.x, (int) pos.y, 70, 70);
		this.health = 50;
		this.speed = .02f;
	}

	@Override
	public void update(int delta) {
		if (health <= 0) {
			die();
			return;
		}
		else if (health < 100) texture = new Texture("res/Enemy/eyeopen.png");
		else if (health < 400) texture = new Texture("res/Enemy/eyehalf.png");
		if (Math.abs(pos.x - destination.x) > .05f || Math.abs(pos.y - destination.y) > .05f) {
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
		Effect explode = new Effect(explosionPics, 100, this.hitbox);
		MainGame.effects.add(explode);
	}
}