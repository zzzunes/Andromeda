package VFX;

import Entities.PowerUp;
import Tools.Vector2f;
import edu.utc.game.GameObject;
import edu.utc.game.Texture;

import java.awt.*;

public class SpellSlot extends GameObject {
	private Texture backgroundTexture;
	public PowerUp power;

	public SpellSlot(Texture texture, Vector2f pos) {
		this.backgroundTexture = texture;
		this.hitbox.setBounds((int) pos.x, (int) pos.y, 65, 65);
		this.power = null;
	}

	public void setPower(PowerUp power) {
		this.power = power;
		Rectangle smallerHitbox = new Rectangle();
		smallerHitbox.setBounds(hitbox.x + 5, hitbox.y + 5, hitbox.width - 10, hitbox.height - 10);
		this.power.setHixbox(smallerHitbox);
	}

	@Override
	public void draw() {
		backgroundTexture.draw(this);
		if (power != null) {
			power.draw();
		}
	}
}
