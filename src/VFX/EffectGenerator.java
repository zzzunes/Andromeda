package VFX;

import Entities.Bullet;

import java.awt.*;

public class EffectGenerator {
	public static Effect generateHitExplosion(Bullet bullet) {
		return new StaticEffect(
				"res/pow.png",
				1000,
				new Rectangle(bullet.getHitbox().x
						- (bullet.getHitbox().width / 2),
						bullet.getHitbox().y - (bullet.getHitbox().height / 2),
						30, 30));
	}
}
