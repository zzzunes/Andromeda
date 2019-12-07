package VFX;

import Entities.Bullet;
import edu.utc.game.GameObject;
import edu.utc.game.Sound;
import edu.utc.game.Texture;

import java.awt.*;
import java.util.ArrayList;

public class EffectGenerator {
	private static ArrayList<Texture> explosions = new ArrayList<>();
	private static ArrayList<Texture> redFlashPlayer = new ArrayList<>();
	private static ArrayList<Texture> redFlashFollower = new ArrayList<>();
	private static ArrayList<Texture> healthBar = new ArrayList<>();
	private static Texture pow;
	private static Sound deathSound;

	public static void initialize() {
		explosions.add(new Texture("res/Explosion/one.png"));
		explosions.add(new Texture("res/Explosion/two.png"));
		explosions.add(new Texture("res/Explosion/three.png"));
		explosions.add(new Texture("res/Explosion/four.png"));
		explosions.add(new Texture("res/Explosion/five.png"));
		explosions.add(new Texture("res/Explosion/six.png"));
		explosions.add(new Texture("res/Explosion/seven.png"));
		explosions.add(new Texture("res/Explosion/eight.png"));
		explosions.add(new Texture("res/Explosion/nine.png"));
		explosions.add(new Texture("res/Explosion/ten.png"));
		redFlashPlayer.add(new Texture("res/damagedspaceship.png"));
		redFlashPlayer.add(new Texture("res/spaceship.png"));
		redFlashPlayer.add(new Texture("res/damagedspaceship.png"));
		redFlashPlayer.add(new Texture("res/spaceship.png"));
		redFlashPlayer.add(new Texture("res/damagedspaceship.png"));
		redFlashPlayer.add(new Texture("res/spaceship.png"));
		redFlashFollower.add(new Texture("res/damagedfollower.png"));
		redFlashFollower.add(new Texture("res/teamShip.png"));
		redFlashFollower.add(new Texture("res/damagedfollower.png"));
		redFlashFollower.add(new Texture("res/teamShip.png"));
		redFlashFollower.add(new Texture("res/damagedfollower.png"));
		redFlashFollower.add(new Texture("res/teamShip.png"));
		healthBar.add(new Texture("res/HealthBar/health0.png"));
		healthBar.add(new Texture("res/HealthBar/health1.png"));
		healthBar.add(new Texture("res/HealthBar/health2.png"));
		healthBar.add(new Texture("res/HealthBar/health3.png"));
		healthBar.add(new Texture("res/HealthBar/health4.png"));
		healthBar.add(new Texture("res/HealthBar/health5.png"));
		healthBar.add(new Texture("res/HealthBar/health6.png"));
		healthBar.add(new Texture("res/HealthBar/health7.png"));
		healthBar.add(new Texture("res/HealthBar/health8.png"));
		healthBar.add(new Texture("res/HealthBar/health9.png"));
		healthBar.add(new Texture("res/HealthBar/health10.png"));
		pow = new Texture("res/pow.png");
		deathSound = new Sound("res/Sounds/boom.wav");
	}

	public static Effect generateHitExplosion(GameObject obj) {
		return new StaticEffect(
				pow,
				1000,
				new Rectangle(obj.getHitbox().x
						- (obj.getHitbox().width / 2),
						obj.getHitbox().y - (obj.getHitbox().height / 2),
						30, 30));
	}

	public static Effect generateDeathExplosion(GameObject object) {
		return new Animation(explosions, 100, object.getHitbox(), deathSound);
	}

	public static Effect generateRedFlashPlayer(GameObject object) {
		return new Animation(redFlashPlayer, 50, object);
	}

	public static Effect generateRedFlashFollower(GameObject object) {
		return new Animation(redFlashFollower, 50, object);
	}

	public static Texture healthBar(int percent) {
		Texture bar;
		if (percent >= 90)
			bar = healthBar.get(10);
		else if (percent >= 80)
			bar = healthBar.get(9);
		else if (percent >= 70)
			bar = healthBar.get(8);
		else if (percent >= 60)
			bar = healthBar.get(7);
		else if (percent >= 50)
			bar = healthBar.get(6);
		else if (percent >= 40)
			bar = healthBar.get(5);
		else if (percent >= 30)
			bar = healthBar.get(4);
		else if (percent >= 20)
			bar = healthBar.get(3);
		else if (percent >= 10)
			bar = healthBar.get(2);
		else if (percent >= 1)
			bar = healthBar.get(1);
		else
			bar = healthBar.get(0);
		return bar;
	}
}
