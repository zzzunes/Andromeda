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
		CIRCLE,
		OCTO
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
		if (!outOfBounds())  {
			texture.draw(this);
		}
	}

	protected boolean outOfBounds() {
		int x = hitbox.x;
		int y = hitbox.y;
		int w = hitbox.width;
		int h = hitbox.height;
		int winW = Game.ui.getWidth();
		int winH = Game.ui.getHeight();
		return (x + w <= 0 || y + h <= 0 || x >= winW || y >= winH);
	}

	protected abstract void die();
}
