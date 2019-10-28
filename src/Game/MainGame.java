package Game;

import Entities.*;
import Tools.Vector2f;
import Visuals.Background;
import Visuals.Effect;
import edu.utc.game.*;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.*;
import java.util.List;

public class MainGame extends Game implements Scene {
	public static void main(String[] args) {
		MainGame game = new MainGame();
		game.gameLoop();
	}

	private static final int WIDTH = 900;
	private static final int HEIGHT = 1000;
	private static final int SCROLL_SPEED = 10;
	private Background background1;
	private Background background2;
	private Texture background;
	private Target marker;
	private Player player;
	private List<Bullet> bullets;
	private List<Enemy> enemies;
	private boolean gotClick;
	private Sound song;
	private int playerBulletTimer;
	private int playerBulletTime;
	public static List<Effect> effects;

	public MainGame() {
		initUI(WIDTH, HEIGHT,"アンドロメダ");
		Game.ui.enableMouseCursor(true);
		GL11.glClearColor(0f, 0f, 0f, 0f);
		background1 = new Background(0, 0, WIDTH, HEIGHT);
		background2 = new Background(0, -HEIGHT, WIDTH, HEIGHT);
		background = new Texture("res/Backgrounds/deepspace2.png");
		marker = new Target();
		player = new Player(new Vector2f(WIDTH / 2, HEIGHT / 2));
		bullets = new ArrayList<>();
		enemies = new ArrayList<>();
		effects = new ArrayList<>();
		gotClick = false;
		song = new Sound("res/Music/cruelAngelThesis.wav");
		song.play();
		song.setLoop(true);
		playerBulletTimer = 0;
		playerBulletTime = 80;
		GLFW.glfwSetMouseButtonCallback(Game.ui.getWindow(), clickback);
		enemies.add(new Boss(new Vector2f(WIDTH / 2 - 35, 100), "res/Enemy/eyeclosed.png"));
	}

	public Scene drawFrame(int delta) {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		Vector2f coordinates = new Vector2f(Game.ui.getMouseLocation().x, Game.ui.getMouseLocation().y);

		/* Timers */
		playerBulletTimer += delta;

		if (gotClick && playerBulletTimer >= playerBulletTime) {
			fireBullet(player, marker);
			playerBulletTimer = 0;
		}

		/* Update */
		marker.setLocation(coordinates);
		player.update(delta);
		update(bullets, delta);
		update(enemies, delta);
		updateEffects(effects, delta);

		/* Do damage! */
		detectHits(bullets, enemies);

		/* Deactivate */
		deactivate(bullets);
		deactivate(enemies);
		stopEffects(effects);

		/* Draw */
		background.draw(background1);
		background.draw(background2);
		marker.draw();
		player.draw();
		draw(enemies);
		draw(bullets);
		drawEffects(effects);

		adjustBackgrounds(background1, background2);
		return this;
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

	private void updateEffects(List<Effect> effects, int delta) {
		for (Effect effect : effects) {
			effect.update(delta);
		}
	}

	private void drawEffects(List<Effect> effects) {
		for (Effect effect : effects) {
			effect.draw();
		}
	}

	private void stopEffects(List<Effect> effects) {
		effects.removeIf(effect -> !effect.isActive());
	}

	private GLFWMouseButtonCallback clickback = new GLFWMouseButtonCallback() {
		public void invoke(long window, int button, int action, int mods)
		{
			if (gotClick && action == GLFW.GLFW_RELEASE) {
				gotClick = false;
			}
			if (button == 0 && action == GLFW.GLFW_PRESS) {
				gotClick = true;
			}
		}};

	private void detectHits(List<Bullet> bullets, List<Enemy> enemies) {
		for (Bullet bullet : bullets) {
			for (Enemy enemy : enemies) {
				if (bullet.getHitbox().intersects(enemy.getHitbox())) {
					bullet.deactivate();
					enemy.damage(1);
					effects.add(new Effect(
							"res/pow.png",
							1000,
							new Rectangle(bullet.getHitbox().x
							- (bullet.getHitbox().width / 2),
							bullet.getHitbox().y - (bullet.getHitbox().height / 2),
							30, 30)));
				}
			}
		}
	}

	private void fireBullet(Player player, Target target) {
		Vector2f targetLocation = target.getLocation();
		Vector2f playerLocation = player.getLocation();
		targetLocation.x -= target.getHitbox().width / 2;
		targetLocation.y -= target.getHitbox().height / 2;
		Vector2f direction = (targetLocation.subtract(playerLocation));
		direction.normalize();
		Vector2f location = new Vector2f(playerLocation);
		location.x += player.getHitbox().width / 3f;
		location.y += player.getHitbox().height / 3f;
		location.x += direction.x * (player.getHitbox().width / 1.5f);
		location.y += direction.y * (player.getHitbox().height / 1.5f);
		Bullet bullet = new Bullet(location, direction);
		bullets.add(bullet);
	}

	private void adjustBackgrounds(Background one, Background two) {
		one.getHitbox().y += SCROLL_SPEED;
		two.getHitbox().y += SCROLL_SPEED;
		if (one.getHitbox().y >= HEIGHT) one.setHeight(-HEIGHT);
		if (two.getHitbox().y >= HEIGHT) two.setHeight(-HEIGHT);
	}
}
