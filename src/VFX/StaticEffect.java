package VFX;

import edu.utc.game.Sound;
import edu.utc.game.Texture;

import java.awt.*;
import java.util.ArrayList;

public class StaticEffect implements Effect {
	private Texture texture;
	private int duration;
	private Rectangle location;
	private int timeActive;
	private boolean active;
	private boolean soundPlayed;
	private Sound sound;

	public StaticEffect(Texture texture, int duration, Rectangle location) {
		this.texture = texture;
		this.duration = duration;
		this.location = location;
		this.timeActive = 0;
		this.active = true;
		this.soundPlayed = false;
	}

	public StaticEffect(String path, int duration, Rectangle location, String soundPath) {
		this.texture = new Texture(path);
		this.duration = duration;
		this.location = location;
		this.timeActive = 0;
		this.active = true;
		this.soundPlayed = false;
		this.sound = new Sound(soundPath);
	}

	public boolean isActive() {
		return active;
	}

	public void update(int delta) {
		timeActive += delta;
		active = timeActive > duration;
	}

	public void draw() {
		if (sound != null && !soundPlayed) {
			sound.play();
			soundPlayed = true;
		}
		texture.draw(location);
	}
}
