package Game;

import Entities.*;
import Tools.Vector2f;
import VFX.*;
import edu.utc.game.*;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
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
	private static final int HALF_WIDTH = WIDTH / 2;
	private static final int HALF_HEIGHT = HEIGHT / 2;
	private static final int FOLLOWER_COST = 1000;
	private static final int PURCHASE_WAIT_TIME = 500;
	private static final int TEXT_FLASH_RATE = 500;
	private Background background1;
	private Background background2;
	private Background pauseBackground;
	private Text pauseText;
	private Text scoreText;
	private Text pauseScoreText;
	private Text deathText;
	private Text continueYes;
	private Text continueNo;
	private Text gameOverText;
	private Text welcomeText;
	private Text pressEnterText;
	private Text emptyText;
	private Player player;
	private Follower leftFollower;
	private Follower rightFollower;
	private List<Bullet> bullets;
	private List<Enemy> enemies;
	private List<Player> playerTeam;
	private List<Background> backgrounds;
	private BackgroundMusic music;
	private boolean paused;
	private boolean gameOver;
	private boolean setupDeath;
	private boolean hasStartedGame;
	private int score;
	private int timeSincePurchased;
	private int textFlashTimer;
	public static List<Effect> effects;
	public static List<Bullet> enemyBullets;

	public MainGame() {
		initUI(WIDTH, HEIGHT,"アンドロメダ");
		Game.ui.enableMouseCursor(false);
		GL11.glClearColor(0f, 0f, 0f, 0f);
		background1 = new Background(0, 0, WIDTH, HEIGHT, "deepspace2.png");
		background2 = new Background(0, -HEIGHT, WIDTH, HEIGHT, "deepspace2.png");
		pauseBackground = new Background(0, 0, WIDTH, HEIGHT, "gray.png");
		pauseText = new Text(HALF_WIDTH - 60, HALF_HEIGHT - 140, 40, 30, "PAUSED");
		score = 0;
		scoreText = new Text(0, HEIGHT - 50, 15, 10, "Score:" + score);
		pauseScoreText = new Text(HALF_WIDTH - 60, HALF_HEIGHT - 80, 40, 30, "Score:" + score);
		deathText = new Text(HALF_WIDTH-80, HALF_HEIGHT - 200, 40, 30, "Continue?");
		continueYes = new Text(HALF_WIDTH-160, HALF_HEIGHT - 150, 40, 30, "Yes (Z)");
		continueNo = new Text(HALF_WIDTH+60, HALF_HEIGHT - 150, 40, 30, "No (X)");
		gameOverText = new Text(HALF_WIDTH-100, HALF_HEIGHT-150, 40, 30, "GAME OVER");
		welcomeText = new Text(HALF_WIDTH-100, HALF_HEIGHT-150, 40, 30, "ANDROMEDA");
		pressEnterText = new Text(HALF_WIDTH-220, HALF_HEIGHT-100, 40, 30, "Press (Enter) to Begin");
		emptyText = new Text(HALF_WIDTH-220, HALF_HEIGHT-100, 40, 30, "");
		paused = false;
		gameOver = false;
		setupDeath = false;
		hasStartedGame = false;
		player = new Player(new Vector2f(WIDTH / 2f, HEIGHT / 2f));
		leftFollower = new Follower(player, true, "res/teamShip.png");
		rightFollower = new Follower(player, false, "res/teamShip.png");
		bullets = new ArrayList<>();
		enemyBullets = new ArrayList<>();
		enemies = new ArrayList<>();
		effects = new ArrayList<>();
		backgrounds = new ArrayList<>();
		playerTeam = new ArrayList<>();
		playerTeam.add(player);
		playerTeam.add(leftFollower);
		playerTeam.add(rightFollower);
		backgrounds.add(background1);
		backgrounds.add(background2);
		timeSincePurchased = 0;
		textFlashTimer = 0;
		GLFW.glfwSetKeyCallback(Game.ui.getWindow(), pause);
		music = new BackgroundMusic("weightOfTheWorld");
		music.start();
		enemies.add(EnemyGenerator.generateEyeStar(new Vector2f(HALF_WIDTH - 35, -100), new Vector2f(HALF_WIDTH - 35, 100)));
	}

	public Scene drawFrame(int delta) {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		if (!hasStartedGame) mainMenuScreen(delta);
		else if (gameOver) gameOverScreen();
		else if (leaderIsDead()) deathScreen(delta);
		else if (paused) pauseScreen();
		else runGame(delta);

		return this;
	}

	/* ******************************************************************** */
	/* ********************** Game Scene Options Begin ******************** */
	/* ******************************************************************** */

	private void mainMenuScreen(int delta) {
		updateBackgrounds(backgrounds);
		draw(backgrounds);
		textFlashTimer += delta;
		if (textFlashTimer > TEXT_FLASH_RATE && textFlashTimer < TEXT_FLASH_RATE * 2) {
			pressEnterText.draw();
		}
		if (textFlashTimer > TEXT_FLASH_RATE * 2) {
			textFlashTimer = 0;
		}
		welcomeText.draw();
		if (Game.ui.keyPressed(GLFW.GLFW_KEY_ENTER)) {
			hasStartedGame = true;
		}
	}

	private void pauseScreen() {
		draw();
		pauseBackground.draw();
		pauseText.draw();
		pauseScoreText.draw();
	}

	private void deathScreen(int delta) {
		if (!setupDeath) {
			music.pause();
			bullets.clear();
			setupDeath = true;
		}
		singlePlayerBullet(player);
		player.update(delta);
		update(bullets, delta);
		draw(bullets);
		pauseBackground.draw();
		pauseScoreText.draw();
		deathText.draw();
		continueYes.draw();
		continueNo.draw();
		player.draw();
		if (Game.ui.keyPressed(GLFW.GLFW_KEY_Z)) {
			player.health = player.maxHealth;
			player.activate();
			playerTeam.add(player);
			music.start();
			setupDeath = false;
		}
		if (Game.ui.keyPressed(GLFW.GLFW_KEY_X)) {
			gameOver = true;
		}
	}

	private void gameOverScreen() {
		pauseBackground.draw();
		// gameOverMusic.play();
		gameOverText.draw();
		pauseScoreText.draw();
	}

	private void runGame(int delta) {
		firePlayerBullets();
		updateGame(delta);
		deactivate();
		collideAndHit();
		draw();
	}

	/* ******************************************************************** */
	/* ********************** Game Scene Options End ********************** */
	/* ******************************************************************** */

	private void firePlayerBullets() {
		for (Player player : playerTeam) {
			if (Game.ui.keyPressed(GLFW.GLFW_KEY_SPACE) && player.bulletTimer >= player.bulletRate) {
				fireBullet(player);
			}
		}
	}

	private void singlePlayerBullet(Player player) {
		if (Game.ui.keyPressed(GLFW.GLFW_KEY_SPACE) && player.bulletTimer >= player.bulletRate) {
			fireBullet(player);
		}
	}

	private void updateGame(int delta) {
		purchaseFollowers(delta);
		update(playerTeam, delta);
		update(bullets, delta);
		update(enemyBullets, delta);
		update(enemies, delta);
		updateEffects(effects, delta);
		updateBackgrounds(backgrounds);
		updateScore();
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
		scoreText.draw();
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
					if (enemy.readyToDie()) score += enemy.getPoints();
					effects.add(EffectGenerator.generateHitExplosion(bullet));
					score += 5;
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

	private boolean leaderIsDead() {
		boolean dead = true;
		for (Player player : playerTeam) {
			if (player.isLeader) dead = false;
		}
		return dead;
	}

	private void fireBullet(Player player) {
		Vector2f playerLocation = player.getLocation();
		Vector2f targetLocation = new Vector2f(0, 0);
		targetLocation.x += playerLocation.x;
		targetLocation.y -= playerLocation.y - (player.getHitbox().height / 2f);
		Vector2f direction = (targetLocation.subtract(playerLocation));
		direction.normalize();
		Vector2f location = new Vector2f(playerLocation);
		if (player.isLeader) location.x += player.getHitbox().width / 4f;
		location.y += player.getHitbox().height / 3f;
		location.y += direction.y * (player.getHitbox().height / 1.5f);
		Bullet bullet = new Bullet(location, direction, player.bulletSpeed, "res/Bullets/playerBullet.png");
		bullet.setSize(15, 30);
		bullets.add(bullet);
		player.bulletTimer = 0;
	}

	private void purchaseFollowers(int delta) {
		/* P is for Purchase */
		timeSincePurchased += delta;
		if (Game.ui.keyPressed(GLFW.GLFW_KEY_P)) {
			if (!leftFollower.isActive() && score > FOLLOWER_COST && timeSincePurchased > PURCHASE_WAIT_TIME) {
				leftFollower.activate();
				leftFollower.health = leftFollower.maxHealth;
				playerTeam.add(leftFollower);
				score -= FOLLOWER_COST;
				timeSincePurchased = 0;
			}
			else if (!rightFollower.isActive() && score > FOLLOWER_COST && timeSincePurchased > PURCHASE_WAIT_TIME) {
				rightFollower.activate();
				rightFollower.health = rightFollower.maxHealth;
				playerTeam.add(rightFollower);
				score -= FOLLOWER_COST;
				timeSincePurchased = 0;
			}
		}
	}

	private void updateBackgrounds(List<Background> backgrounds) {
		for (Background background : backgrounds) {
			background.getHitbox().y += SCROLL_SPEED;
			if (background.getHitbox().y >= HEIGHT) background.setHeight(-HEIGHT);
		}
	}

	private void updateScore() {
		int leftShift = 0;
		int scoreCopy = score;
		while (scoreCopy > 0) {
			scoreCopy /= 10;
			leftShift += 10;
		}
		scoreText = new Text(0, HEIGHT - 50, 30, 25, "Score: " + score);
		pauseScoreText = new Text(HALF_WIDTH - 80  - leftShift, HALF_HEIGHT - 80, 40, 30, "Score: " + score);
	}

	/* ******************************************************************** */
	/* ************************* Callback Functions *********************** */
	/* ******************************************************************** */

	GLFWKeyCallback pause = new GLFWKeyCallback() {
		@Override
		public void invoke(long window, int key, int scancode, int action, int mods) {
			if (!paused && action == GLFW.GLFW_PRESS && key == GLFW.GLFW_KEY_BACKSPACE) {
				paused = true;
				music.pause();
			}
			else if (paused && action == GLFW.GLFW_PRESS && key == GLFW.GLFW_KEY_BACKSPACE) {
				paused = false;
				music.start();
			}
		}
	};

	/* ******************************************************************** */
	/* ************************* Callback Functions *********************** */
	/* ******************************************************************** */
}
