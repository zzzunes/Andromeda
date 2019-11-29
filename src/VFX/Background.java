package VFX;

import edu.utc.game.GameObject;
import edu.utc.game.Texture;

public class Background extends GameObject {
	private Texture texture;

	public Background(int x, int y, int width, int height, String texturePath) {
		this.hitbox.setBounds(x, y, width, height);
		this.texture = new Texture("res/Backgrounds/" + texturePath);
	}

	public void setHeight(int y) {
		this.hitbox.y = y;
	}

	@Override
	public void draw() {
		texture.draw(this);
	}
}
