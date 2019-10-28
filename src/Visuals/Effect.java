package Visuals;

import edu.utc.game.Sound;
import edu.utc.game.Texture;

import java.awt.*;
import java.util.ArrayList;

public class Effect {
	private Texture texture;
	private int duration;
	private Rectangle location;
	private int timeActive;
	private boolean active;
	private boolean animated;
	private int currentAnimation;
	private ArrayList<String> paths = new ArrayList<>();
	private Sound sound;
	private boolean soundPlayed;

	public Effect(String path, int duration, Rectangle location) {
		this.texture = new Texture(path);
		this.duration = duration;
		this.location = location;
		this.timeActive = 0;
		this.active = true;
		this.animated = false;
	}

	public Effect(ArrayList<String> paths, int duration, Rectangle location) {
		this.texture = new Texture(paths.get(0));
		this.paths = paths;
		this.duration = duration;
		this.location = location;
		this.timeActive = 0;
		this.active = true;
		this.animated = true;
		this.currentAnimation = 0;
	}

	public Effect(ArrayList<String> paths, int duration, Rectangle location, String soundPath) {
		this.texture = new Texture(paths.get(0));
		this.paths = paths;
		this.duration = duration;
		this.location = location;
		this.timeActive = 0;
		this.active = true;
		this.animated = true;
		this.currentAnimation = 0;
		this.sound = new Sound(soundPath);
		this.soundPlayed = false;
	}

	public boolean isActive() {
		return active;
	}

	public void update(int delta) {
		timeActive += delta;
		if (animated) {
			if (timeActive >= duration) {
				timeActive = 0;
				currentAnimation++;
				if (currentAnimation >= paths.size()) {
					active = false;
					return;
				}
				this.texture = new Texture(paths.get(currentAnimation));
			}
		}
		else {
			active = timeActive >= duration;
		}
	}

	public void draw() {
		if (sound != null && !soundPlayed) {
			sound.play();
			soundPlayed = true;
		}
		texture.draw(location);
	}
}
