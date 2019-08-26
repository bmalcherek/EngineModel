package engineTester;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL;

import renderEngine.WindowManager;

public class MainGame {
    public static void main(String[] args) {
        WindowManager.createWindow();
        long window = WindowManager.getWindow();
        int loop = 0;

        while (!glfwWindowShouldClose(window)) {
            //game logic
            //render
            GL.createCapabilities();

            glClearColor(1.0f, 1.0f, 0.0f, 0.0f);
            WindowManager.updateWindow();
        }

        WindowManager.destroyWindow();
    }
}
