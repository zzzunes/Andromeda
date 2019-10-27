import Tools.Vector2f;
import edu.utc.game.Game;
import edu.utc.game.GameObject;
import edu.utc.game.Texture;
import org.lwjgl.glfw.GLFW;

public class Player extends GameObject {
	private Vector2f pos;
	private Vector2f direction;
	private Texture texture;
	private float speed;

	public Player(Vector2f position) {
		this.pos = position;
		this.hitbox.setBounds((int) pos.x, (int) pos.y, 32, 32);
		this.setColor(1, 0, 0);
		this.speed = .3f;
		this.direction = new Vector2f(0, 0);
		this.texture = new Texture("res/spaceship.png");
	}

	public Vector2f getLocation() {
		return pos;
	}

	@Override
	public void update(int delta) {
		direction = new Vector2f(0, 0);
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

	private void adjustHitBox() {
		hitbox.x = (int) pos.x;
		hitbox.y = (int) pos.y;
	}

	@Override
	public void draw() {
		texture.draw(this);
	}
}
