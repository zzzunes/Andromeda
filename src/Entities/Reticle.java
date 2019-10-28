package Entities;

import Tools.Vector2f;
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
}