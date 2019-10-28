package Entities;

import Tools.Vector2f;
import edu.utc.game.Game;
import edu.utc.game.GameObject;

public class Bullet extends GameObject {
	private Vector2f pos;
	private Vector2f dir;
	private float speed;

	public Bullet(Vector2f position, Vector2f direction) {
		this.pos = position;
		this.dir = direction;
		this.hitbox.setBounds((int) pos.x, (int) pos.y, 10, 10);
		this.speed = 1f;
		super.setColor(1, 1, 1);
	}

	@Override
	public void update(int delta) {
		pos.x += dir.x * speed * delta;
		pos.y += dir.y * speed * delta;
		hitbox.x = (int) pos.x;
		hitbox.y = (int) pos.y;

		if (pos.x < 0 || pos.x > Game.ui.getWidth() || pos.y < 0 || pos.y > Game.ui.getHeight()) deactivate();
	}
}