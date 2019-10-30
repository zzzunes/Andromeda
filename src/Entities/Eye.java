package Entities;

import Game.MainGame;
import Tools.Vector2f;
import VFX.Effect;
import VFX.EffectGenerator;
import edu.utc.game.Texture;

import java.util.ArrayList;

public class Eye extends Enemy {
	private boolean destinationReached;
	private boolean goingLeft;
	private boolean goingRight;
	private boolean goingDown;
	private boolean goingUp;
	private int circlePosition;
	private int bulletsPerFrame;
	private Pattern pattern;

	public Eye(Vector2f position, Vector2f destination, String texture, Pattern pattern, int delay) {
		super(destination, texture);
		this.pos = position;
		this.hitbox.setBounds((int) pos.x, (int) pos.y, 70, 70);
		this.health = 300;
		this.speed = .03f;
		this.destinationReached = false;
		this.bulletTimer = 0;
		this.bulletRate = delay;
		this.bulletSpeed = .1f;
		this.circlePosition = 0;
		this.bulletsPerFrame = 4;
		this.pattern = pattern;
	}

	@Override
	public void update(int delta) {
		bulletTimer += delta;
		if (health <= 0) die();
		else if (health < 50) texture = new Texture("res/Enemy/eyeopen.png");
		else if (health < 200) texture = new Texture("res/Enemy/eyehalf.png");
		if (!destinationReached) {
			goToDestination(delta);
			destinationReached = pos.distanceTo(destination) <= 1f;
			if (destinationReached) destination = pos.add(new Vector2f(0, 500));
		}
		else {
			if (pattern == Pattern.SPIRAL) fireSpiral();
			if (pattern == Pattern.CIRCLE && bulletTimer > bulletRate) {
				fireCircle();
				bulletTimer = 0;
			}
			if (pattern == Pattern.OCTO && bulletTimer > bulletRate) {
				fireOcto();
				bulletTimer = 0;
			}
		}

		adjustHitBox();
	}

	private void fireSpiral() {
		Vector2f center = new Vector2f(pos.x + hitbox.width / 2f, pos.y + hitbox.height / 2f);
		for (int i = 0; i < bulletsPerFrame; i++) {
			circlePosition++;
			if (circlePosition > 360) circlePosition = 0;
			float x = hitbox.width * (float) Math.cos(circlePosition * Math.PI / 180);
			float y = hitbox.width * (float) Math.sin(circlePosition * Math.PI / 180);
			Vector2f position = new Vector2f(x + center.x, y + center.y);
			Vector2f direction = (position.subtract(center));
			direction.normalize();
			Bullet bullet = new Bullet(position, direction, bulletSpeed);
			MainGame.enemyBullets.add(bullet);
		}
	}

	private void fireCircle() {
		Vector2f center = new Vector2f(pos.x + hitbox.width / 2f, pos.y + hitbox.height / 2f);
		for (int i = 0; i < 360; i++) {
			float x = hitbox.width * (float) Math.cos(i * Math.PI / 180);
			float y = hitbox.width * (float) Math.sin(i * Math.PI / 180);
			Vector2f position = new Vector2f(x + center.x, y + center.y);
			Vector2f direction = (position.subtract(center));
			direction.normalize();
			Bullet bullet = new Bullet(position, direction, bulletSpeed);
			MainGame.enemyBullets.add(bullet);
		}
	}

	private void fireOcto() {
		Vector2f center = new Vector2f(pos.x - 5 + hitbox.width / 2f, pos.y - 5 + hitbox.height / 2f);
		ArrayList<Vector2f> positions = new ArrayList<>();
		float outside = hitbox.width / 2f;
		positions.add(new Vector2f(center.x - outside, center.y));
		positions.add(new Vector2f(center.x + outside, center.y));
		positions.add(new Vector2f(center.x - outside, center.y - outside));
		positions.add(new Vector2f(center.x + outside, center.y + outside));
		positions.add(new Vector2f(center.x - outside, center.y + outside));
		positions.add(new Vector2f(center.x + outside, center.y - outside));
		positions.add(new Vector2f(center.x, center.y + outside));
		positions.add(new Vector2f(center.x, center.y - outside));
		for (Vector2f position : positions) {
			Vector2f direction = (position.subtract(center));
			direction.normalize();
			Bullet bullet = new Bullet(position, direction, bulletSpeed);
			MainGame.enemyBullets.add(bullet);
		}
	}

	private void goToDestination(int delta) {
		Vector2f direction = destination.subtract(pos);
		direction.normalize();
		pos.x += direction.x * speed * delta;
		pos.y += direction.y * speed * delta;
	}

	private void adjustHitBox() {
		hitbox.x = (int) pos.x;
		hitbox.y = (int) pos.y;
	}

	@Override
	protected void die() {
		deactivate();
		Effect explode = EffectGenerator.generateDeathExplosion(this);
		MainGame.effects.add(explode);
	}
}
