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

public class Player extends GameObject {
	protected Vector2f pos;
	protected Texture texture;
	private HealthBar healthBar;
	private Vector2f direction;
	private float speed;
	public float health;
	public float maxHealth;
	public int bulletTimer;
	public int bulletRate;
	public float bulletSpeed;
	public boolean isLeader;

	public Player(Vector2f position) {
		this.pos = position;
		this.hitbox.setBounds((int) pos.x, (int) pos.y, 32, 32);
		this.speed = .35f;
		this.direction = new Vector2f(0, 0);
		this.texture = new Texture("res/spaceship.png");
		this.healthBar = new HealthBar(100, this);
		this.health = 125;
		this.maxHealth = 125;
		this.bulletTimer = 0;
		this.bulletRate = 35;
		this.bulletSpeed = 1.75f;
		this.isLeader = true;
	}

	public Vector2f getLocation() {
		return pos;
	}

	public void takeHit() {
		this.health--;
	}

	@Override
	public void update(int delta) {
		bulletTimer += delta;
		direction = new Vector2f(0, 0);
		if (health <= 0) die();
		healthBar.update(delta);
		getMovementInput();
		move(delta);
		checkBounds();
		adjustHitBox();
	}

	private void getMovementInput() {
		if (Game.ui.keyPressed(GLFW.GLFW_KEY_A)) {
			direction.x = -1;
		}
		if (Game.ui.keyPressed(GLFW.GLFW_KEY_D)) {
			direction.x = 1;
		}
		if (Game.ui.keyPressed(GLFW.GLFW_KEY_W)) {
			direction.y = -1;
		}
		if (Game.ui.keyPressed(GLFW.GLFW_KEY_S)) {
			direction.y = 1;
		}
	}

	private void move(int delta) {
		pos.x += direction.x * speed * delta;
		pos.y += direction.y * speed * delta;
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