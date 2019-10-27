import Tools.Vector2f;
import edu.utc.game.Game;
import edu.utc.game.Texture;

public class Boss extends Enemy {
	public Boss(Vector2f destination, String texture) {
		super(destination, texture);
		this.pos = new Vector2f(Game.ui.getWidth() / 2f - 35, -100);
		this.hitbox.setBounds((int) pos.x, (int) pos.y, 70, 70);
		this.health = 500;
		this.speed = .02f;
	}

	@Override
	public void update(int delta) {
		if (health < 100) texture = new Texture("res/eyeopen.png");
		else if (health < 400) texture = new Texture("res/eyehalf.png");
		if (Math.abs(pos.x - destination.x) > .03f || Math.abs(pos.y - destination.y) > .03f) {
			goToDestination(delta);
		}
		adjustHitBox();
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
}
