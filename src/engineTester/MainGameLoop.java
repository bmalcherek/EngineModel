package engineTester;

import entities.FocusPoint;
import entities.Light;
import models.RawModel;
import models.TexturedModel;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;
import entities.Camera;
import entities.Entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainGameLoop{

    public static void main(String[] args)  throws LWJGLException, IOException  {

        DisplayManager.createDisplay();
        Loader loader = new Loader();
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);

        ModelTexture steel = new ModelTexture(loader.loadTexture("steel"));
        steel.setShineDamper(10);
        steel.setReflectivity(1);

        RawModel pistonModel = OBJLoader.loadObjModel("piston", loader);
        RawModel camshaftModel = OBJLoader.loadObjModel("camshaft", loader);
        RawModel crankshaftModel = OBJLoader.loadObjModel("crankshaft", loader);
        RawModel pistonHeadModel = OBJLoader.loadObjModel("pistonHead", loader);
        RawModel pistonRodModel = OBJLoader.loadObjModel("pistonRod", loader);
        RawModel valveModel = OBJLoader.loadObjModel("valve", loader);
        RawModel ballModel = OBJLoader.loadObjModel("ball", loader);

        TexturedModel pistonStaticModel = new TexturedModel(pistonModel, steel);
        TexturedModel camshaftStaticModel = new TexturedModel(camshaftModel, steel);
        TexturedModel crankshaftStaticModel = new TexturedModel(crankshaftModel, steel);
        TexturedModel pistonHeadStaticModel = new TexturedModel(pistonHeadModel, steel);
        TexturedModel pistonRodStaticModel = new TexturedModel(pistonRodModel, steel);
        TexturedModel valveStaticModel = new TexturedModel(valveModel, steel);
        TexturedModel ballStaticModel = new TexturedModel(ballModel, steel);

        Entity pistonEntity = new Entity(pistonStaticModel, new Vector3f(0, 0, -40), 0, 0, 0.5f, 1);
        Entity camshaftEntity = new Entity(camshaftStaticModel, new Vector3f(1, 0, -40), 0, 0.5f, 0, 0.1f);
        Entity crankshaftEntity = new Entity(crankshaftStaticModel, new Vector3f(-5, -10, -40), 0, 0.5f, 0, 0.1f);
        Entity pistonHeadEntity = new Entity(pistonHeadStaticModel, new Vector3f(5, 10, -40), 0, 0.5f, 0, 0.1f);
        Entity pistonRodEntity = new Entity(pistonRodStaticModel, new Vector3f(-5, 10, -40), 0.5f, 0.5f, 0, 0.1f);
        Entity valveEntity = new Entity(valveStaticModel, new Vector3f(-5, 0, -40), 0.5f, 0.5f, 0, 0.1f);

        FocusPoint focusPoint = new FocusPoint(ballStaticModel, new Vector3f(0,0,0), 0, 0, 0, 1);
        Camera camera = new Camera(focusPoint);
        Light light = new Light(new Vector3f(10, 20, -20), new Vector3f(1, 1, 1));

        while (!Display.isCloseRequested()) {
        	crankshaftEntity.increaseRotation(0.3f, 0, 0);
        	camshaftEntity.increaseRotation(0.5f, 0, 0);
            camera.move();
            renderer.prepare();
            shader.start();
            shader.loadViewMatrix(camera);
            shader.loadLight(light);
            renderer.render(pistonEntity, shader);
            renderer.render(camshaftEntity, shader);
            renderer.render(crankshaftEntity, shader);
            renderer.render(pistonHeadEntity, shader);
            renderer.render(pistonRodEntity, shader);
            renderer.render(valveEntity, shader);
            shader.stop();
            DisplayManager.updateDisplay();
        }

        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();

    }

}
