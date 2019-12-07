package VFX;

import Entities.Bullet;
import edu.utc.game.GameObject;

import java.awt.*;
import java.util.ArrayList;

public class EffectGenerator {
	public static Effect generateHitExplosion(GameObject obj) {
		return new StaticEffect(
				"res/pow.png",
				1000,
				new Rectangle(obj.getHitbox().x
						- (obj.getHitbox().width / 2),
						obj.getHitbox().y - (obj.getHitbox().height / 2),
						30, 30));
	}

	public static Effect generateDeathExplosion(GameObject object) {
		ArrayList<String> explosionPics = new ArrayList<String>();
		explosionPics.add("res/Explosion/one.png");
		explosionPics.add("res/Explosion/two.png");
		explosionPics.add("res/Explosion/three.png");
		explosionPics.add("res/Explosion/four.png");
		explosionPics.add("res/Explosion/five.png");
		explosionPics.add("res/Explosion/six.png");
		explosionPics.add("res/Explosion/seven.png");
		explosionPics.add("res/Explosion/eight.png");
		explosionPics.add("res/Explosion/nine.png");
		explosionPics.add("res/Explosion/ten.png");
		return new Animation(explosionPics, 100, object.getHitbox(), "res/Sounds/boom.wav");
	}

	public static Effect generateRedFlashPlayer(GameObject object) {
		ArrayList<String> hitPics = new ArrayList<String>();
		hitPics.add("res/damagedspaceship.png");
		hitPics.add("res/spaceship.png");
		hitPics.add("res/damagedspaceship.png");
		hitPics.add("res/spaceship.png");
		hitPics.add("res/damagedspaceship.png");
		hitPics.add("res/spaceship.png");
		return new Animation(hitPics, 50, object);
	}

	public static Effect generateRedFlashFollower(GameObject object) {
		ArrayList<String> hitPics = new ArrayList<String>();
		hitPics.add("res/damagedfollower.png");
		hitPics.add("res/teamShip.png");
		hitPics.add("res/damagedfollower.png");
		hitPics.add("res/teamShip.png");
		hitPics.add("res/damagedfollower.png");
		hitPics.add("res/teamShip.png");
		return new Animation(hitPics, 50, object);
	}
}
