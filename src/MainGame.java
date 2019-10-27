import Tools.Vector2f;
import edu.utc.game.*;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.opengl.GL11;

import java.util.*;

public class MainGame extends Game implements Scene {
	public static void main(String[] args) {
		MainGame game = new MainGame();
		game.gameLoop();
	}

	private Target marker;
	private boolean gotClick = false;

	public MainGame() {
		initUI(1280,720,"すご");
		Game.ui.enableMouseCursor(true);
		GL11.glClearColor(0f, 0f, 0f, 0f);
		marker = new Target();
		GLFW.glfwSetMouseButtonCallback(Game.ui.getWindow(), clickback);
	}

	public Scene drawFrame(int delta) {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		Vector2f coordinates = new Vector2f(Game.ui.getMouseLocation().x, Game.ui.getMouseLocation().y);

		/* Update */
		marker.setLocation(coordinates);

		/* Draw */
		marker.draw();

		gotClick = false;
		return this;
	}

	private <T extends GameObject> void update(List<T> gameObjects, int delta) {
		for (GameObject go : gameObjects) {
			go.update(delta);
		}
	}

	private <T extends GameObject> void draw(List<T> gameObjects) {
		for (GameObject go : gameObjects) {
			go.draw();
		}
	}

	private <T extends GameObject> void deactivate(List<T> objects) {
		objects.removeIf(o -> !o.isActive());
	}

	private GLFWMouseButtonCallback clickback = new GLFWMouseButtonCallback() {
		public void invoke(long window, int button, int action, int mods)
		{
			if (button == 0 && action == GLFW.GLFW_PRESS) {
				gotClick = true;
			}
		}};
}
