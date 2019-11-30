package Entities;

import Tools.Vector2f;
import edu.utc.game.Texture;

public class Follower extends Player {
	private boolean left;
	private Player leader;

	public Follower(Player player, boolean onLeft, String texturePath) {
		super(player.getLocation());
		this.pos = new Vector2f(0, 0);
		this.leader = player;
		this.hitbox.setBounds((int) pos.x, (int) pos.y, 16, 16);
		this.left = onLeft;
		this.texture = new Texture(texturePath);
		this.bulletTimer = leader.bulletTimer;
		this.bulletRate = leader.bulletRate * 4;
		this.health = leader.health;
		this.isLeader = false;
		this.bulletSpeed = player.bulletSpeed;
	}

	@Override
	public void update(int delta) {
		bulletTimer += delta;
		if (health <= 0 || !leader.isActive()) die();
		float leaderX = leader.getLocation().x;
		float leaderY = leader.getLocation().y;
		pos.y = leaderY + 16;
		if (left) {
			pos.x = leaderX - 16;
		}
		else {
			pos.x = leaderX + 32;
		}
		adjustHitBox();
	}
}
