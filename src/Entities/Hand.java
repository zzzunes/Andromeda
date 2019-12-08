package Entities;

import Game.MainGame;
import Tools.Vector2f;
import VFX.Effect;
import VFX.EffectGenerator;
import edu.utc.game.Texture;

public class Hand extends Enemy {
	private int positionTimer;
	private int windupTimer;
	private Player target;
	private boolean positioning;
	private boolean hitting;
	private int windupTime;
	private Texture fist;
	private Texture open;

	public Hand(Texture texture, Texture fist, Player mainCharacter) {
		this.positionTimer = 0;
		this.health = 100;
		this.maxHealth = 100;
		this.texture = texture;
		this.target = mainCharacter;
		this.pos = new Vector2f(-40, MainGame.HALF_HEIGHT);
		this.hitbox.setBounds((int) pos.x, (int) pos.y, 80, 80);
		this.speed = mainCharacter.getSpeed() * 1.2f;
		this.positioning = true;
		this.hitting = false;
		this.canPush = true;
		this.windupTime = 1000;
		this.open = texture;
		this.fist = fist;
	}

	@Override
	public void update(int delta) {
		positionTimer += delta;
		Vector2f dir = new Vector2f(0, 0);
		positioning = (positionTimer < 6000);
		if (positioning) {
			dir.y = target.pos.y - pos.y - 25;
			dir.x = 20 - pos.x;
		}
		if (!positioning) {
			windupTimer += delta;
			hitting = windupTimer >= windupTime;
			texture = fist;
		}
		if (hitting) {
			dir.x = 1;
			pos.x += dir.x * speed * delta * 2;
			texture = open;
		}
		else if (Math.abs(dir.y) > 1 || Math.abs(dir.x) > 1) {
			dir.normalize();
			pos.x += dir.x * speed * delta;
			pos.y += dir.y * speed * delta;
		}

		adjustHitBox();
		if (outOfBounds()) {
			deactivate();
		}
	}

	protected void adjustHitBox() {
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
