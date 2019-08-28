package engineTester;

import static org.lwjgl.glfw.GLFW.*;

import models.TexturedModel;
import org.lwjgl.opengl.GL;

import renderEngine.Loader;
import models.RawModel;
import renderEngine.Renderer;
import renderEngine.WindowManager;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGame {
    public static void main(String[] args) {
        WindowManager.createWindow();
        long window = WindowManager.getWindow();
        GL.createCapabilities();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();
        StaticShader shader = new StaticShader();

        float[] vertices = {
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0
        };

        int[] indices = {
                0, 1, 3,
                3, 1, 2
        };

        float[] textureCords = {
                0, 0,
                0, 1,
                1, 1,
                1, 0
        };

        RawModel model = loader.loadToVao(vertices, indices, textureCords);
        ModelTexture texture = new ModelTexture(loader.loadTexture("python"));
        TexturedModel texturedModel = new TexturedModel(model, texture);

        while (!glfwWindowShouldClose(window)) {

            //game logic

            //render
            renderer.prepare();
            shader.start();
            renderer.render(texturedModel);
            shader.stop();

            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }

        loader.cleanUp();
        WindowManager.destroyWindow();
    }
}
