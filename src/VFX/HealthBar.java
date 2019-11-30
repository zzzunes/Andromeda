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
		Texture bar;
		if (percent >= 90)
			bar = new Texture("res/HealthBar/health10.png");
		else if (percent >= 80)
			bar = new Texture("res/HealthBar/health9.png");
		else if (percent >= 70)
			bar = new Texture("res/HealthBar/health8.png");
		else if (percent >= 60)
			bar = new Texture("res/HealthBar/health7.png");
		else if (percent >= 50)
			bar = new Texture("res/HealthBar/health6.png");
		else if (percent >= 40)
			bar = new Texture("res/HealthBar/health5.png");
		else if (percent >= 30)
			bar = new Texture("res/HealthBar/health4.png");
		else if (percent >= 20)
			bar = new Texture("res/HealthBar/health3.png");
		else if (percent >= 10)
			bar = new Texture("res/HealthBar/health2.png");
		else if (percent >= 1)
			bar = new Texture("res/HealthBar/health1.png");
		else
			bar = new Texture("res/HealthBar/health0.png");
		return bar;
	}
}
