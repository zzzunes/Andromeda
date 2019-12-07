package Entities;

import Tools.Vector2f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnemyGenerator {
	private static Random r = new Random();

	public static Enemy generateEyeStar(Vector2f position, Vector2f destination) {
		return new Eye(position, destination, "res/Enemy/eyeclosed.png", Enemy.Pattern.STAR, 2000);
	}

	public static Enemy generateClassEnemy(Vector2f position, Vector2f destination, List<String> classNames) {
		int namePosition = r.nextInt(classNames.size());
		String name = classNames.get(namePosition);
		classNames.remove(namePosition);
		return new TextEnemy(position, destination, name, 2000);
	}

	public static List<String> generateEnemyList() {
		List<String> theClass = new ArrayList<>();
		theClass.add("Juliana Brava");
		theClass.add("Zachary Dyar");
		theClass.add("Mia Fletcher");
		theClass.add("Noah Gaston");
		theClass.add("Jonathan Gautreaux");
		theClass.add("Brittany Jaggars");
		theClass.add("Xandar Kehoe");
		theClass.add("Quintin Lester");
		theClass.add("Andrew Nguyen");
		theClass.add("Hampton Nowack");
		theClass.add("Sevaughn Orr");
		theClass.add("Melissa Otis");
		theClass.add("Gabriel Santos");
		theClass.add("Addison Schley-Ritchie");
		theClass.add("Riley Shipley");
		theClass.add("Dan Smith");
		theClass.add("Jared Taylor");
		theClass.add("Benjamin Treine");
		theClass.add("Holly Trollinger");
		theClass.add("Christopher Vaugn");
		theClass.add("Stephen Weiler");
		theClass.add("Bowen Wexler");
		theClass.add("Jackson Whitfield");
		return theClass;
	}
}
