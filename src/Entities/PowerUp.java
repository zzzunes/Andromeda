package Entities;

import edu.utc.game.GameObject;
import edu.utc.game.Texture;

import java.awt.*;

public class PowerUp extends GameObject {
	private Texture texture;
	public POWER power;
	private int lifeTimer;
	private int lifeTime;

	public PowerUp(Enemy enemy, Texture texture, POWER power) {
		Rectangle r = enemy.getHitbox();
		this.hitbox.setBounds(r.x + r.width / 2 - 25, r.y + r.height / 2 - 25, 50, 50);
		this.texture = texture;
		this.power = power;
		this.lifeTimer = 0;
		this.lifeTime = 15000;
	}

	public PowerUp(PowerUp powerUp) {
		this.texture = powerUp.texture;
		this.power = powerUp.power;
	}

	@Override
	public void update(int delta) {
		lifeTimer += delta;
		if (lifeTimer > lifeTime) deactivate();
	}

	@Override
	public void draw() {
		texture.draw(this);
	}
}
