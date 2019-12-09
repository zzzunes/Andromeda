package Entities;

import edu.utc.game.GameObject;
import edu.utc.game.Texture;

import java.awt.*;

public class PowerUp extends GameObject {
	private Texture texture;
	public POWER power;

	public PowerUp(Enemy enemy, Texture texture, POWER power) {
		Rectangle r = enemy.getHitbox();
		this.hitbox.setBounds(r.x + r.width / 2 - 15, r.y + r.height / 2 - 15, 50, 50);
		this.texture = texture;
		this.power = power;
	}

	@Override
	public void draw() {
		texture.draw(this);
	}
}
