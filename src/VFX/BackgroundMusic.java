package VFX;

import edu.utc.game.Sound;

public class BackgroundMusic {
	private Sound song;

	public BackgroundMusic(String songName) {
		song = new Sound("res/Music/" + songName + ".wav");
	}

	public void start() {
		song.play();
		song.setLoop(true);
	}

	public void stop() {
		song.stop();
	}

	public void change(String soundPath) {
		stop();
		song = new Sound(soundPath);
	}
}
