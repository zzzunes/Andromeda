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

	public static final int WIDTH = 600;
	public static final int HEIGHT = 800;
	public static final int HALF_WIDTH = WIDTH / 2;
	public static final int HALF_HEIGHT = HEIGHT / 2;
	private static final float FOLLOWER_COST = .9f;
	private static final int PURCHASE_WAIT_TIME = 500;
	private static final int TEXT_FLASH_RATE = 500;
	private static final float DEATH_PENALTY_PERCENT = .75f;
	private static final int REVIVE_INVINCIBILITY_TIME = 2000;
	private static final int AFTER_DEATH_HANG_TIME = 2500;
	private static final int ENEMY_DISPATCH_TIME = 7000;
	private static final int FINAL_BOSS_WAIT_TIME = 5000;
	private static final int HAND_WAIT_TIME = 30000;
	private static final int INVINCIBILITY_TIME = 7500;
	private static final int SLOW_DOWN_TIME = 7500;
	private static final int REGEN_AMOUNT = 50;
	private static final int DOUBLE_BULLET_RATE = 15;
	private Background background1;
	private Background background2;
	private Background pauseBackground;
	private Background blue;
	private Background yellow;
	private Text pauseText;
	private Text scoreText;
	private Text pauseScoreText;
	private Text deathText;
	private Text continueYes;
	private Text continueNo;
	private Text gameOverText;
	private Text welcomeText;
	private Text pressEnterText;
	private Player player;
	private Follower leftFollower;
	private Follower rightFollower;
	private List<Bullet> bullets;
	private List<Enemy> enemies;
	private List<Player> playerTeam;
	private List<Background> backgrounds;
	private List<String> classNames;
	private BackgroundMusic music;
	private boolean paused;
	private boolean gameOver;
	private boolean setupDeath;
	private boolean hasStartedGame;
	private int score;
	private int timeSincePurchased;
	private int textFlashTimer;
	private int introSongTimer;
	private int invincibilityTimer;
	private int afterDeathTimer;
	private int enemyDispatchTimer;
	private int finalBossUnleashTimer;
	private int handsDispatchTimer;
	private int powerInvincibilityTimer;
	private int powerSlowDownTimer;
	private boolean timeToDie;
	private boolean finalBossSpawned;
	private boolean isInvincible;
	private boolean isSlowedDown;
	public static Texture enemyBulletTexture;
	public static Texture playerBulletTexture;
	private Texture handTexture;
	private Texture fistTexture;
	private Texture blackSlot;
	private SpellSlot spellSlotOne;
	private SpellSlot spellSlotTwo;
	public static List<Effect> effects;
	public static List<Bullet> enemyBullets;
	public static List<PowerUp> availablePowerUps;
	public static List<POWER> powers;

	public MainGame() {
		initUI(WIDTH, HEIGHT,"アンドロメダ");
		Game.ui.enableMouseCursor(false);
		GL11.glClearColor(0f, 0f, 0f, 0f);
		EffectGenerator.initialize();
		background1 = new Background(0, 0, WIDTH, HEIGHT, "deepspace2.png");
		background2 = new Background(0, -HEIGHT, WIDTH, HEIGHT, "deepspace2.png");
		pauseBackground = new Background(0, 0, WIDTH, HEIGHT, "gray.png");
		blue = new Background(0, 0, WIDTH, HEIGHT, "blue.png");
		yellow = new Background(0, 0, WIDTH, HEIGHT, "yellow.png");
		pauseText = new Text(HALF_WIDTH - 70, HALF_HEIGHT - 140, 40, 30, "PAUSED");
		score = 0;
		scoreText = new Text(0, HEIGHT - 50, 15, 10, "Score:" + score);
		pauseScoreText = new Text(HALF_WIDTH - 60, HALF_HEIGHT - 80, 40, 30, "Score:" + score);
		deathText = new Text(HALF_WIDTH-80, HALF_HEIGHT - 200, 40, 30, "Continue?");
		continueYes = new Text(HALF_WIDTH-160, HALF_HEIGHT - 150, 40, 30, "Yes (Z)");
		continueNo = new Text(HALF_WIDTH+60, HALF_HEIGHT - 150, 40, 30, "No (X)");
		gameOverText = new Text(HALF_WIDTH-100, HALF_HEIGHT-150, 40, 30, "GAME OVER");
		welcomeText = new Text(HALF_WIDTH-100, HALF_HEIGHT-150, 40, 30, "ANDROMEDA");
		pressEnterText = new Text(HALF_WIDTH-220, HALF_HEIGHT-100, 40, 30, "Press (Enter) to Begin");
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
		availablePowerUps = new ArrayList<>();
		classNames = EnemyGenerator.generateEnemyList();
		powers = Collections.unmodifiableList(Arrays.asList(POWER.values()));
		playerTeam.add(player);
		playerTeam.add(leftFollower);
		playerTeam.add(rightFollower);
		backgrounds.add(background1);
		backgrounds.add(background2);
		timeSincePurchased = 0;
		textFlashTimer = 0;
		introSongTimer = 0;
		afterDeathTimer = 0;
		finalBossUnleashTimer = 0;
		handsDispatchTimer = 0;
		powerInvincibilityTimer = INVINCIBILITY_TIME;
		powerSlowDownTimer = SLOW_DOWN_TIME;
		enemyDispatchTimer = ENEMY_DISPATCH_TIME;
		timeToDie = false;
		finalBossSpawned = false;
		isInvincible = false;
		isSlowedDown = false;
		invincibilityTimer = REVIVE_INVINCIBILITY_TIME;
		enemyBulletTexture = new Texture("res/Bullets/roundBullet.png");
		playerBulletTexture = new Texture("res/Bullets/playerBullet.png");
		handTexture = new Texture("res/hand.png");
		fistTexture = new Texture("res/hand2.png");
		blackSlot = new Texture("res/Backgrounds/blackSlot.png");
		spellSlotOne = new SpellSlot(blackSlot, new Vector2f(WIDTH - 175,HEIGHT - 75));
		spellSlotTwo = new SpellSlot(blackSlot, new Vector2f(WIDTH - 100,HEIGHT - 75));
		GLFW.glfwSetKeyCallback(Game.ui.getWindow(), pause);
		GLFW.glfwSetKeyCallback(Game.ui.getWindow(), ability);
		music = new BackgroundMusic("weightOfTheWorld");
		music.start();
	}

	public Scene drawFrame(int delta) {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		if (!hasStartedGame) mainMenuScreen(delta);
		else if (gameOver) gameOverScreen();
		else if (timeToDie) deathScreen(delta);
		else if (paused) pauseScreen();
		else runGame(delta);

		return this;
	}

	/* ******************************************************************** */
	/* ********************** Game Scene Options Begin ******************** */
	/* ******************************************************************** */

	private void mainMenuScreen(int delta) {
		update(backgrounds, delta);
		draw(backgrounds);
		textFlashTimer += delta;
		introSongTimer += delta;
		if (textFlashTimer > TEXT_FLASH_RATE && textFlashTimer < TEXT_FLASH_RATE * 2) {
			pressEnterText.draw();
		}
		if (textFlashTimer > TEXT_FLASH_RATE * 2) {
			textFlashTimer = 0;
		}
		if (introSongTimer > 9300) {
			music.rewind();
			music.start();
			introSongTimer = 0;
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
			setupDeath = false;
			score *= DEATH_PENALTY_PERCENT;
			invincibilityTimer = 0;
			afterDeathTimer = 0;
			timeToDie = false;
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

	private void spawnFinalBoss(int delta) {
		music.stop();
		finalBossUnleashTimer += delta;
		if (finalBossUnleashTimer >= FINAL_BOSS_WAIT_TIME) {
			backgrounds.clear();
			backgrounds.add(new Background(0, 0, WIDTH, HEIGHT, "Zone-202-big.png"));
			backgrounds.add(new Background(0, -HEIGHT, WIDTH, HEIGHT, "Zone-202-big.png"));
			music.change("cruelAngelThesis");
			music.start();
			enemies.add(EnemyGenerator.generateEyeStar(new Vector2f(HALF_WIDTH - 150, -100), new Vector2f(HALF_WIDTH + 75, 100)));
			enemies.add(EnemyGenerator.generateEyeStar(new Vector2f(HALF_WIDTH + 75, -100), new Vector2f(HALF_WIDTH - 150, 100)));
			finalBossSpawned = true;
		}
	}

	private void spawnHands(int delta) {
		handsDispatchTimer += delta;
		if (handsDispatchTimer > HAND_WAIT_TIME) {
			enemies.add(new Hand(handTexture, fistTexture, player));
			handsDispatchTimer = 0;
		}
	}

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
		if (powerSlowDownTimer < SLOW_DOWN_TIME) {
			updateLogic(delta / 4);
			update(playerTeam, delta);
			update(bullets, delta);
			powerSlowDownTimer += delta;
			isSlowedDown = true;
		}
		else {
			updateLogic(delta);
			update(playerTeam, delta);
			update(bullets, delta);
			isSlowedDown = false;
		}
	}

	private void updateLogic(int delta) {
		if (invincibilityTimer < REVIVE_INVINCIBILITY_TIME) {
			invincibilityTimer += delta;
		}
		isInvincible = powerInvincibilityTimer < INVINCIBILITY_TIME;
		if (isInvincible) {
			powerInvincibilityTimer += delta;
		}
		if (leaderIsDead()) {
			afterDeathTimer += delta;
			timeToDie = afterDeathTimer >= AFTER_DEATH_HANG_TIME;
		}
		if (classNames.isEmpty() && enemies.isEmpty() && !finalBossSpawned) {
			spawnFinalBoss(delta);
		}
		if (finalBossSpawned) {
			spawnHands(delta);
		}
		spawnEnemies(delta);
		purchaseFollowers(delta);
		update(availablePowerUps, delta);
		update(enemyBullets, delta);
		update(enemies, delta);
		updateEffects(effects, delta);
		update(backgrounds, delta);
		updateScore();
	}

	private void collideAndHit() {
		detectHits(bullets, enemies);
		if (invincibilityTimer >= REVIVE_INVINCIBILITY_TIME && powerInvincibilityTimer >= INVINCIBILITY_TIME) {
			detectPlayerDamage(playerTeam);
			detectPush(enemies, playerTeam);
		}
		detectPickUp(availablePowerUps, player);
	}

	private void deactivate() {
		deactivate(availablePowerUps);
		deactivate(playerTeam);
		deactivate(bullets);
		deactivate(enemyBullets);
		deactivate(enemies);
		stopEffects(effects);
	}

	private void draw() {
		draw(backgrounds);
		if (isSlowedDown) blue.draw();
		if (isInvincible) yellow.draw();
		draw(availablePowerUps);
		draw(playerTeam);
		draw(enemies);
		draw(bullets);
		draw(enemyBullets);
		drawEffects(effects);
		spellSlotOne.draw();
		spellSlotTwo.draw();
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

	private void detectPush(List<Enemy> enemies, List<Player> players) {
		for (Enemy enemy : enemies) {
			for (Player player : players) {
				if (!player.isLeader || player.hit || !enemy.canPush) continue;
				if (enemy.getHitbox().intersects(player.getHitbox())) {
					player.hit = true;
					Vector2f enemyPos = new Vector2f(enemy.getHitbox().x + enemy.getHitbox().width / 2f, enemy.getHitbox().y + enemy.getHitbox().height / 2f);
					Vector2f playerPos = new Vector2f(player.getLocation().x + player.getHitbox().width / 2f, player.getLocation().y + player.getHitbox().height / 2f);
					Vector2f direction = playerPos.subtract(enemyPos);
					direction.normalize();
					player.direction = direction;
				}
			}
		}
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
			if (Math.abs(bullet.getHitbox().x-player.getHitbox().x) > 20) continue;
			if (bullet.getHitbox().intersects(player.getHitbox())) {
				bullet.deactivate();
				player.takeHit();
				effects.add(EffectGenerator.generateHitExplosion(bullet));
				if (player.isLeader) {
					effects.add(EffectGenerator.generateRedFlashPlayer(player));
				}
				else {
					effects.add(EffectGenerator.generateRedFlashFollower(player));
				}
			}
		}
	}

	private void detectPickUp(List<PowerUp> powers, Player player) {
		for (PowerUp power : powers) {
			if (power.getHitbox().intersects(player.getHitbox())) {
				System.out.println("Power picked up: " + power.power);
				if (spellSlotOne.power == null) {
					spellSlotOne.setPower(new PowerUp(power));
				}
				else if (spellSlotTwo.power == null) {
					spellSlotTwo.setPower(new PowerUp(power));
				}
				power.deactivate();
			}
		}
	}

	private void usePower(PowerUp power) {
		switch(power.power) {
			case SLOW_TIME:
				powerSlowDownTimer = 0;
				break;
			case DOUBLE_SHOT:
				player.speedShot(DOUBLE_BULLET_RATE);
				break;
			case HEALTH_REGEN:
				player.health = Math.min(player.health + REGEN_AMOUNT, player.maxHealth);
				break;
			case INVINCIBILITY:
				powerInvincibilityTimer = 0;
				break;
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
		Bullet bullet = new Bullet(location, direction, player.bulletSpeed, playerBulletTexture);
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
				score *= FOLLOWER_COST;
				timeSincePurchased = 0;
			}
			else if (!rightFollower.isActive() && score > FOLLOWER_COST && timeSincePurchased > PURCHASE_WAIT_TIME) {
				rightFollower.activate();
				rightFollower.health = rightFollower.maxHealth;
				playerTeam.add(rightFollower);
				score *= FOLLOWER_COST;
				timeSincePurchased = 0;
			}
		}
	}

	private void spawnEnemies(int delta) {
		enemyDispatchTimer += delta;
		if (!classNames.isEmpty() && enemyDispatchTimer > ENEMY_DISPATCH_TIME * enemies.size()) {
			Enemy newEnemy = EnemyGenerator.generateClassEnemy(new Vector2f(HALF_WIDTH, HALF_HEIGHT), classNames);
			enemies.add(newEnemy);
			enemyDispatchTimer = 0;
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

	GLFWKeyCallback ability = new GLFWKeyCallback() {
		@Override
		public void invoke(long window, int key, int scancode, int action, int mods) {
			if (action == GLFW.GLFW_PRESS && key == GLFW.GLFW_KEY_C && spellSlotOne.power != null) {
				usePower(spellSlotOne.power);
				spellSlotOne.power = null;
			}
			if (action == GLFW.GLFW_PRESS && key == GLFW.GLFW_KEY_V && spellSlotTwo.power != null) {
				usePower(spellSlotTwo.power);
				spellSlotTwo.power = null;
			}
		}
	};

	/* ******************************************************************** */
	/* ************************* Callback Functions *********************** */
	/* ******************************************************************** */
}
