package Entities;

import Tools.Vector2f;

public class EnemyGenerator {
	public static Enemy generateEyeSpiral(Vector2f position, Vector2f destination) {
		return new Eye(position, destination, "res/Enemy/eyeclosed.png", Enemy.Pattern.SPIRAL, 0);
	}

	public static Enemy generateEyeCircle(Vector2f position, Vector2f destination) {
		return new Eye(position, destination, "res/Enemy/eyeclosed.png", Enemy.Pattern.CIRCLE, 500);
	}

	public static Enemy generateEyeOcto(Vector2f position, Vector2f destination) {
		return new Eye(position, destination, "res/Enemy/eyeclosed.png", Enemy.Pattern.OCTO, 500);
	}
}
