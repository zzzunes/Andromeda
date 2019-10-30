package VFX;

public interface Effect {
	boolean isActive();
	void update(int delta);
	void draw();
}
