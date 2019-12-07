package Entities;

import Game.MainGame;
import Tools.Vector2f;
import VFX.Color;
import sun.applet.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnemyGenerator {
	private static Random r = new Random();

	public static Enemy generateEyeStar(Vector2f position, Vector2f destination) {
		return new Eye(position, destination, "res/Enemy/eyeclosed.png", Enemy.Pattern.STAR, 2000);
	}

	public static Enemy generateClassEnemy(Vector2f destination, List<String> classNames) {
		int namePosition = r.nextInt(classNames.size());
		String name = classNames.get(namePosition);
		classNames.remove(namePosition);
		Color color = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
		if (Math.max(color.b, Math.max(color.r, color.g)) < 0.2) color.r = 0.5f;
		return new TextEnemy(generateEnemyOrigin(), destination, name, 2000, color);
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

	private static Vector2f generateEnemyOrigin() {
		List<Vector2f> origins = new ArrayList<>();
		origins.add(new Vector2f(-50,-50));
		origins.add(new Vector2f(MainGame.HALF_WIDTH - 100, MainGame.HEIGHT + 200));
		origins.add(new Vector2f(MainGame.WIDTH + 200,-100));
		origins.add(new Vector2f(MainGame.WIDTH + 200,0));
		origins.add(new Vector2f(MainGame.HALF_WIDTH, -200));
		origins.add(new Vector2f(MainGame.WIDTH + 300,MainGame.HALF_HEIGHT));
		origins.add(new Vector2f(-200, MainGame.HALF_HEIGHT));
		origins.add(new Vector2f(-200,MainGame.HALF_HEIGHT - 100));
		origins.add(new Vector2f(MainGame.HALF_WIDTH + MainGame.HALF_WIDTH / 2f,-100));
		origins.add(new Vector2f(MainGame.WIDTH,-150));
		int pos = r.nextInt(origins.size());
		return origins.get(pos);
	}
}
