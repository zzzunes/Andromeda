package VFX;

import edu.utc.game.GameObject;
import edu.utc.game.Sound;
import edu.utc.game.Texture;

import java.awt.*;
import java.util.ArrayList;

public class Animation implements Effect {
	private Texture texture;
	private int duration;
	private Rectangle location;
	private GameObject parent;
	private int timeActive;
	private boolean active;
	private boolean animated;
	private int currentAnimation;
	private ArrayList<Texture> paths;
	private Sound sound;
	private boolean soundPlayed;

	public Animation(ArrayList<Texture> textures, int duration, GameObject parent) {
		this.texture = textures.get(0);
		this.paths = textures;
		this.duration = duration;
		this.location = parent.getHitbox();
		this.parent = parent;
		this.timeActive = 0;
		this.active = true;
		this.animated = true;
		this.currentAnimation = 0;
	}

	public Animation(ArrayList<Texture> textures, int duration, Rectangle location, Sound sound) {
		this.texture = textures.get(0);
		this.paths = textures;
		this.duration = duration;
		this.location = location;
		this.timeActive = 0;
		this.active = true;
		this.animated = true;
		this.currentAnimation = 0;
		this.sound = sound;
		this.soundPlayed = false;
	}

	public boolean isActive() {
		return active;
	}

	public void update(int delta) {
		if (parent != null) location = parent.getHitbox();
		timeActive += delta;
		if (animated) {
			if (timeActive >= duration) {
				timeActive = 0;
				currentAnimation++;
				if (currentAnimation >= paths.size()) {
					active = false;
					return;
				}
				this.texture = paths.get(currentAnimation);
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
