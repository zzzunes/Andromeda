package Entities;

import Tools.Vector2f;

public class EnemyGenerator {
	public static Enemy generateEyeSpiral(Vector2f position, Vector2f destination) {
		return new Eye(position, destination, "res/Enemy/eyeclosed.png", Enemy.Pattern.SPIRAL);
	}

	public static Enemy generateEyeCircle(Vector2f position, Vector2f destination) {
		return new Eye(position, destination, "res/Enemy/eyeclosed.png", Enemy.Pattern.CIRCLE);
	}
}
