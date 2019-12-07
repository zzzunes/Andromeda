package VFX;

import Entities.Bullet;
import edu.utc.game.GameObject;
import edu.utc.game.Texture;

import java.awt.*;
import java.util.ArrayList;

public class EffectGenerator {
	private static ArrayList<Texture> explosions = new ArrayList<>();
	private static ArrayList<Texture> redFlashPlayer = new ArrayList<>();
	private static ArrayList<Texture> redFlashFollower = new ArrayList<>();
	private static Texture pow;

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
		pow = new Texture("res/pow.png");
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
		return new Animation(explosions, 100, object.getHitbox(), "res/Sounds/boom.wav");
	}

	public static Effect generateRedFlashPlayer(GameObject object) {
		return new Animation(redFlashPlayer, 50, object);
	}

	public static Effect generateRedFlashFollower(GameObject object) {
		return new Animation(redFlashFollower, 50, object);
	}
}
