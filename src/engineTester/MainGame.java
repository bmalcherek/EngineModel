package engineTester;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL;

import renderEngine.Loader;
import renderEngine.RawModel;
import renderEngine.Renderer;
import renderEngine.WindowManager;

public class MainGame {
    public static void main(String[] args) {
        WindowManager.createWindow();
        long window = WindowManager.getWindow();
        GL.createCapabilities();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();

        float[] vertices = {
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,

                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f,
                -0.5f, 0.5f, 0f,
        };

        RawModel model = loader.loadToVao(vertices);

        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            //game logic

            //render
            renderer.prepare();
            renderer.render(model);

            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }

        loader.cleanUp();
        WindowManager.destroyWindow();
    }
}
