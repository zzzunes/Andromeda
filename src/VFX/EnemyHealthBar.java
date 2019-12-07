package VFX;

import Entities.Enemy;

public class EnemyHealthBar extends HealthBar {
	protected Enemy parent;

	public EnemyHealthBar(int percent, Enemy parent) {
		this.healthBar = getHealthBar(percent);
		this.percent = percent;
		this.parent = parent;
	}

	@Override
	public void update(int delta) {
		percent = (int) ((parent.health / parent.maxHealth) * 100);
		this.hitbox.setBounds(parent.getHitbox().x,
				parent.getHitbox().y - (int) parent.getHitbox().getHeight(),
				60,
				20);
		this.healthBar = getHealthBar(percent);
	}
}
