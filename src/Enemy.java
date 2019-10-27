import Tools.Vector2f;
import edu.utc.game.Game;
import edu.utc.game.GameObject;
import edu.utc.game.Texture;

public abstract class Enemy extends GameObject {
	protected Vector2f pos;
	protected Vector2f destination;
	protected Texture texture;
	protected float health;
	protected float speed;

	public Enemy(Vector2f destination, String texture) {
		this.pos = new Vector2f(Game.ui.getWidth() / 2f, -100);
		this.destination = destination;
		this.texture = new Texture(texture);
	}

	public void damage(int damage) {
		health = Math.max(0, health - damage);
	}

	@Override
	public void draw() {
		texture.draw(this);
	}
}
