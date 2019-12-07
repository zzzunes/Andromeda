package VFX;

import Entities.Player;
import edu.utc.game.GameObject;
import edu.utc.game.Texture;

public class HealthBar extends GameObject {
	protected Texture healthBar;
	protected int percent;
	private Player parent;

	public HealthBar() {}

	public HealthBar(int percent, Player player) {
		this.healthBar = getHealthBar(percent);
		this.percent = percent;
		this.parent = player;
	}

	@Override
	public void update(int delta) {
		percent = (int) ((parent.health / parent.maxHealth) * 100);
		this.hitbox.setBounds((int) parent.getLocation().x - 15, (int) parent.getLocation().y + 32, 60, 20);
		this.healthBar = getHealthBar(percent);
	}

	@Override
	public void draw() {
		healthBar.draw(this);
	}

	protected Texture getHealthBar(int percent) {
		return EffectGenerator.healthBar(percent);
	}
}
