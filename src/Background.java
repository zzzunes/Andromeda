import edu.utc.game.GameObject;

public class Background extends GameObject {
	public Background(int x, int y, int width, int height) {
		this.hitbox.setBounds(x, y, width, height);
	}

	public void setHeight(int y) {
		this.hitbox.y = y;
	}
}
