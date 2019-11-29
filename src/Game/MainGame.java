package Game;

import Entities.*;
import Tools.Vector2f;
import VFX.Background;
import VFX.BackgroundMusic;
import VFX.Effect;
import VFX.EffectGenerator;
import edu.utc.game.*;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.util.*;
import java.util.List;

public class MainGame extends Game implements Scene {
	public static void main(String[] args) {
		MainGame game = new MainGame();
		game.gameLoop();
	}

	private static final int WIDTH = 600;
	private static final int HEIGHT = 800;
	private static final int SCROLL_SPEED = 10;
	private Background background1;
	private Background background2;
	private Player player;
	private List<Bullet> bullets;
	private List<Enemy> enemies;
	private List<Player> playerTeam;
	private List<Background> backgrounds;
	private BackgroundMusic music;
	public static List<Effect> effects;
	public static List<Bullet> enemyBullets;

	public MainGame() {
		initUI(WIDTH, HEIGHT,"アンドロメダ");
		Game.ui.enableMouseCursor(false);
		GL11.glClearColor(0f, 0f, 0f, 0f);
		background1 = new Background(0, 0, WIDTH, HEIGHT, "deepspace2.png");
		background2 = new Background(0, -HEIGHT, WIDTH, HEIGHT, "deepspace2.png");
		player = new Player(new Vector2f(WIDTH / 2f, HEIGHT / 2f));
		bullets = new ArrayList<>();
		enemyBullets = new ArrayList<>();
		enemies = new ArrayList<>();
		effects = new ArrayList<>();
		backgrounds = new ArrayList<>();
		playerTeam = new ArrayList<>();
		playerTeam.add(player);
		backgrounds.add(background1);
		backgrounds.add(background2);
		music = new BackgroundMusic("cruelAngelThesis");
		music.start();
		enemies.add(EnemyGenerator.generateEyeStar(new Vector2f((Game.ui.getWidth()/2f) - 35, -100), new Vector2f((Game.ui.getWidth()/2f) - 35, 100)));
	}

	public Scene drawFrame(int delta) {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		firePlayerBullets();
		updateGame(delta);
		deactivate();
		collideAndHit();
		draw();

		return this;
	}

	private void firePlayerBullets() {
		for (Player player : playerTeam) {
			if (Game.ui.keyPressed(GLFW.GLFW_KEY_SPACE) && player.bulletTimer >= player.bulletRate) {
				fireBullet(player);
			}
		}
	}

	private void updateGame(int delta) {
		update(playerTeam, delta);
		update(bullets, delta);
		update(enemyBullets, delta);
		update(enemies, delta);
		updateEffects(effects, delta);
		updateBackgrounds(backgrounds);
	}

	private void collideAndHit() {
		detectHits(bullets, enemies);
		detectPlayerDamage(playerTeam);
	}

	private void deactivate() {
		deactivate(playerTeam);
		deactivate(bullets);
		deactivate(enemyBullets);
		deactivate(enemies);
		stopEffects(effects);
	}

	private void draw() {
		draw(backgrounds);
		draw(playerTeam);
		draw(enemies);
		draw(bullets);
		draw(enemyBullets);
		drawEffects(effects);
	}

	private <T extends GameObject> void update(List<T> gameObjects, int delta) {
		for (GameObject go : gameObjects) {
			go.update(delta);
		}
	}

	private <T extends GameObject> void draw(List<T> gameObjects) {
		for (GameObject go : gameObjects) {
			go.draw();
		}
	}

	private <T extends GameObject> void deactivate(List<T> objects) {
		objects.removeIf(o -> !o.isActive());
	}

	private void updateEffects(List<Effect> staticEffects, int delta) {
		for (Effect effect : staticEffects) {
			effect.update(delta);
		}
	}

	private void drawEffects(List<Effect> staticEffects) {
		for (Effect effect : staticEffects) {
			effect.draw();
		}
	}

	private void stopEffects(List<Effect> effects) {
		effects.removeIf(effect -> !effect.isActive());
	}

	private void detectHits(List<Bullet> bullets, List<Enemy> enemies) {
		for (Bullet bullet : bullets) {
			for (Enemy enemy : enemies) {
				if (bullet.getHitbox().intersects(enemy.getHitbox())) {
					bullet.deactivate();
					enemy.damage(1);
					effects.add(EffectGenerator.generateHitExplosion(bullet));
				}
			}
		}
	}

	private void detectHits(List<Enemy> enemies, Player player) {
		for (Enemy enemy : enemies) {
			if (player.getHitbox().intersects(enemy.getHitbox())) {
				effects.add(EffectGenerator.generateHitExplosion(player));
				player.takeHit();
			}
		}
	}

	private void detectHitsPlayer(List<Bullet> bullets, Player player) {
		for (Bullet bullet : bullets) {
			if (bullet.getHitbox().intersects(player.getHitbox())) {
				bullet.deactivate();
				player.takeHit();
				effects.add(EffectGenerator.generateHitExplosion(bullet));
			}
		}
	}

	private void detectPlayerDamage(List<Player> players) {
		for (Player player : players) {
			detectHits(enemies, player);
			detectHitsPlayer(enemyBullets, player);
		}
	}

	private void fireBullet(Player player) {
		Vector2f playerLocation = player.getLocation();
		Vector2f targetLocation = new Vector2f(0, 0);
		targetLocation.x += playerLocation.x;
		targetLocation.y -= playerLocation.y - (player.getHitbox().height / 2f);
		Vector2f direction = (targetLocation.subtract(playerLocation));
		direction.normalize();
		Vector2f location = new Vector2f(playerLocation);
		location.x += player.getHitbox().width / 3f;
		location.y += player.getHitbox().height / 3f;
		location.x += direction.x * (player.getHitbox().width / 1.5f);
		location.y += direction.y * (player.getHitbox().height / 1.5f);
		float speed = 1.75f;
		Bullet bullet = new Bullet(location, direction, speed, "res/Bullets/playerBullet.png");
		bullet.setSize(15, 30);
		bullets.add(bullet);
		player.bulletTimer = 0;
	}

	private void updateBackgrounds(List<Background> backgrounds) {
		for (Background background : backgrounds) {
			background.getHitbox().y += SCROLL_SPEED;
			if (background.getHitbox().y >= HEIGHT) background.setHeight(-HEIGHT);
		}
	}
}
