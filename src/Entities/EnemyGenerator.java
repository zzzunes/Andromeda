package Entities;

import Tools.Vector2f;

public class EnemyGenerator {
	public static Enemy generateEyeStar(Vector2f position, Vector2f destination) {
		return new Eye(position, destination, "res/Enemy/eyeclosed.png", Enemy.Pattern.STAR, 4000);
	}

	public static Enemy generateEyeCircle(Vector2f position, Vector2f destination) {
		return new Eye(position, destination, "res/Enemy/eyeclosed.png", Enemy.Pattern.CIRCLE, 4000);
	}

	public static Enemy generateEyeOcto(Vector2f position, Vector2f destination) {
		return new Eye(position, destination, "res/Enemy/eyeclosed.png", Enemy.Pattern.OCTO, 500);
	}
}
