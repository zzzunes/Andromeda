package VFX;

import Entities.Enemy;

public class TextEnemyHealthBar extends EnemyHealthBar {

	public TextEnemyHealthBar(int percent, Enemy parent) {
		super(percent, parent);
	}

	@Override
	public void update(int delta) {
		percent = (int) ((parent.health / parent.maxHealth) * 100);
		this.hitbox.setBounds(parent.getHitbox().x,
				parent.getHitbox().y - 20,
				60,
				20);
		this.healthBar = getHealthBar(percent);
	}
}
