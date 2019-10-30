package Entities;

import Tools.Vector2f;
import edu.utc.game.Game;
import edu.utc.game.GameObject;
import edu.utc.game.Texture;

public abstract class Enemy extends GameObject {
	protected Vector2f pos;
	protected Vector2f destination;
	protected Texture texture;
	protected float health;
	protected float speed;
	protected int bulletTimer;
	protected int bulletRate;
	protected float bulletSpeed;

	public static enum Pattern {
		SPIRAL,
		CIRCLE
	}

	public Enemy(Vector2f destination, String texture) {
		this.destination = destination;
		this.texture = new Texture(texture);
	}

	public void damage(int damage) {
		health = Math.max(0, health - damage);
	}

	@Override
	public void draw() {
		texture.draw(this);
	}

	protected abstract void die();
}
