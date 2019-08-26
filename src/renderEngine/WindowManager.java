package renderEngine;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class WindowManager {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int FPS_LIMIT = 120;
    private static long window;

    public static long getWindow() {
        return window;
    }

    public static void createWindow() {
        GLFWErrorCallback.createPrint(System.err).set();

        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(WIDTH, HEIGHT, "Engine Model", NULL, NULL);

        if(window == NULL) {
            throw new RuntimeException("Failed to create window");
        }

        glfwSetKeyCallback(window, (window, key, scanCode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true);
        });

        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }

    public static void updateWindow() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

        glfwSwapBuffers(window); // swap the color buffers

        glfwPollEvents();
    }

    public static void destroyWindow() {
        glfwDestroyWindow(window);
    }
}
