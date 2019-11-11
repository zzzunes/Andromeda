package Entities;

import Tools.Vector2f;
import edu.utc.game.Game;
import edu.utc.game.GameObject;
import edu.utc.game.Texture;

public class Reticle extends GameObject
{
	private Vector2f location;
	private Texture texture;

	public void setLocation(Vector2f location)
	{
		this.hitbox.setBounds((int) location.x, (int) location.y, 20, 20);
		this.location = location;
		this.texture = new Texture("res/reticle.png");
	}

	public Vector2f getLocation() {
		return this.location;
	}

	public void setColor(float r, float g, float b)
	{
		super.setColor(r, g, b);
	}

	@Override
	public void draw() {
		texture.draw(this);
	}

	private void checkBounds() {
		if (location.x + hitbox.width > Game.ui.getWidth()) {
			location.x = Game.ui.getWidth() - hitbox.width;
		}
		if (location.y + hitbox.height > Game.ui.getHeight()) {
			location.y = Game.ui.getHeight() - hitbox.height;
		}
		if (location.x < 0) location.x = 0;
		if (location.y < 0) location.y = 0;
	}
}