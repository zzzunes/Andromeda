package Entities;

import Tools.Vector2f;
import VFX.Effect;
import Game.MainGame;
import VFX.EffectGenerator;
import VFX.HealthBar;
import edu.utc.game.Game;
import edu.utc.game.GameObject;
import edu.utc.game.Texture;
import org.lwjgl.glfw.GLFW;
import sun.awt.X11.XDestroyWindowEvent;

public class Player extends GameObject {
	protected Vector2f pos;
	protected Texture texture;
	private HealthBar healthBar;
	public Vector2f direction;
	private float speed;
	public float health;
	public float maxHealth;
	public int bulletTimer;
	public int permanentBulletRate;
	public int bulletRate;
	public float bulletSpeed;
	public boolean isLeader;
	public boolean hit;
	public int hitTimer;
	public int hitTimePeriod;
	private int speedBulletTimer;
	private boolean speedBulletsActive;
	private int maxSpeedBulletTime;

	public Player(Vector2f position) {
		this.pos = position;
		this.hitbox.setBounds((int) pos.x, (int) pos.y, 32, 32);
		this.speed = .35f;
		this.direction = new Vector2f(0, 0);
		this.texture = new Texture("res/spaceship.png");
		this.healthBar = new HealthBar(100, this);
		this.health = 150;
		this.maxHealth = 150;
		this.bulletTimer = 0;
		this.bulletRate = 35;
		this.permanentBulletRate = this.bulletRate;
		this.bulletSpeed = 1.75f;
		this.isLeader = true;
		this.hit = false;
		this.hitTimer = 0;
		this.hitTimePeriod = 500;
		this.speedBulletTimer = 0;
		this.speedBulletsActive = false;
		this.maxSpeedBulletTime = 10000;
	}

	public void speedShot(int newRate) {
		this.bulletRate = newRate;
		speedBulletsActive = true;
	}

	public Vector2f getLocation() {
		return pos;
	}

	public void takeHit() {
		this.health--;
	}

	@Override
	public void update(int delta) {
		if (speedBulletsActive) {
			speedBulletTimer += delta;
			if (speedBulletTimer > maxSpeedBulletTime) {
				speedBulletsActive = false;
				bulletRate = permanentBulletRate;
			}
		}
		bulletTimer += delta;
		if (hit) {
			hitTimer += delta;
			if(hitTimer >= hitTimePeriod) {
				hitTimer = 0;
				hit = false;
			}
		}
		else {
			direction = new Vector2f(0, 0);
		}
		if (health <= 0) die();
		healthBar.update(delta);
		getMovementInput();
		move(delta);
		checkBounds();
		adjustHitBox();
	}

	private void getMovementInput() {
		if (hit) {
			return;
		}
		if (Game.ui.keyPressed(GLFW.GLFW_KEY_A) || Game.ui.keyPressed(GLFW.GLFW_KEY_LEFT)) {
			direction.x = -1;
		}
		if (Game.ui.keyPressed(GLFW.GLFW_KEY_D) || Game.ui.keyPressed(GLFW.GLFW_KEY_RIGHT)) {
			direction.x = 1;
		}
		if (Game.ui.keyPressed(GLFW.GLFW_KEY_W) || Game.ui.keyPressed(GLFW.GLFW_KEY_UP)) {
			direction.y = -1;
		}
		if (Game.ui.keyPressed(GLFW.GLFW_KEY_S) || Game.ui.keyPressed(GLFW.GLFW_KEY_DOWN)) {
			direction.y = 1;
		}
	}

	private void move(int delta) {
		if (hit) {
			pos.x += direction.x * speed * delta * 2;
			pos.y += direction.y * speed * delta * 2;
		}
		else {
			pos.x += direction.x * speed * delta;
			pos.y += direction.y * speed * delta;
		}
	}

	private void checkBounds() {
		if (pos.x + hitbox.width > Game.ui.getWidth()) {
			pos.x = Game.ui.getWidth() - hitbox.width;
		}
		if (pos.y + hitbox.height > Game.ui.getHeight()) {
			pos.y = Game.ui.getHeight() - hitbox.height;
		}
		if (pos.x < 0) pos.x = 0;
		if (pos.y < 0) pos.y = 0;
	}

	protected void adjustHitBox() {
		hitbox.x = (int) pos.x;
		hitbox.y = (int) pos.y;
	}

	public float getSpeed() {
		return speed;
	}

	@Override
	public void draw() {
		texture.draw(this);
		healthBar.draw();
	}

	protected void die() {
		deactivate();
		Effect explode = EffectGenerator.generateDeathExplosion(this);
		MainGame.effects.add(explode);
	}
}