package VFX;

import Tools.Vector2f;
import edu.utc.game.GameObject;
import edu.utc.game.Texture;

import static Game.MainGame.HEIGHT;

public class Background extends GameObject {
	private Texture texture;
	private int speed;

	public Background(int x, int y, int width, int height, String texturePath) {
		this.hitbox.setBounds(x, y, width, height);
		this.texture = new Texture("res/Backgrounds/" + texturePath);
		this.speed = 1;
	}

	public void setHeight(int y) {
		this.hitbox.y = y;
	}

	@Override
	public void update(int delta) {
		if (hitbox.y >= HEIGHT) hitbox.y = -HEIGHT + (hitbox.y - HEIGHT);
		Vector2f dir = new Vector2f(0, 1);
		hitbox.y += speed * dir.y * delta;
	}

	@Override
	public void draw() {
		texture.draw(this);
	}
}
