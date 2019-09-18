package engineTester;

import entities.*;
import models.RawModel;
import models.TexturedModel;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.*;
import shaders.StaticShader;
import textures.ModelTexture;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainGameLoop {

    public static void main(String[] args) throws LWJGLException, IOException {

        DisplayManager.createDisplay();
        Loader loader = new Loader();
        StaticShader shader = new StaticShader();
        MasterRenderer renderer = new MasterRenderer();

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

        Entity crankshaftEntity = new Entity(crankshaftStaticModel, new Vector3f(0, 0, 0), 0, 0, 0, 0.11f);
        Entity valveEntity = new Entity(valveStaticModel, new Vector3f(17.5f, 11, 0.9f), 35f, 0f, 0, 0.03f);
        Entity valveEntity2 = new Entity(valveStaticModel, new Vector3f(17.5f, 11, -0.9f), -35f, 0f, 0, 0.03f);

        Entity pistonEntity = new Entity(pistonStaticModel, new Vector3f(0, 0, -40), 0, 0, 0.5f, 1);
        Entity camshaftEntity = new Entity(camshaftStaticModel, new Vector3f(1, 0, -40), 0, 0.5f, 0, 0.1f);
        Entity pistonHeadEntity = new Entity(pistonHeadStaticModel, new Vector3f(5, 10, -40), 0, 0.5f, 0, 1f);
        Entity pistonRodEntity = new Entity(pistonRodStaticModel, new Vector3f(-5, 10, -40), 0.5f, 0.5f, 0, 1f);

        FocusPoint focusPoint = new FocusPoint(ballStaticModel, new Vector3f(-0.5f, 11, 20), 0, 0, 0, 1);
        Camera camera = new Camera(focusPoint);
        Light light = new Light(new Vector3f(10, 20, 40), new Vector3f(1, 1, 1));

        float SPEED = 4f;
        float crankshaftRotationSpeed = SPEED * 0.108f;

        float firstPistonX = -0.61f;
        float pistonInterval = 4.52f;
        float[] startingHeights = {4.42f, 6.6f, 6.6f, 5.7f, 7.73f, 5.7f};
        float[] startingRotations = {0f, 10f, -10f, -9f, 0f, 9f};
        int[] startingCycles = {1, 4, 1, 3, 1, 2};

        List<Piston> pistons = new ArrayList<Piston>();
        for (int i = 0; i < 6; i++) {
            Piston piston = new Piston(pistonRodStaticModel, pistonHeadStaticModel, valveStaticModel,
                    new Vector3f(firstPistonX + i * pistonInterval, startingHeights[i], 0), SPEED, startingRotations[i], startingCycles[i]);
            pistons.add(piston);
        }

        while (!Display.isCloseRequested()) {
            camera.move();

            for (Piston piston : pistons) {
                renderer.processEntity(piston.getRod());
                renderer.processEntity(piston.getHead());
                renderer.processEntity(piston.getValveIn());
                renderer.processEntity(piston.getValveOut());
                piston.move();
            }
            crankshaftEntity.increaseRotation(crankshaftRotationSpeed, 0, 0);
            renderer.processEntity(crankshaftEntity);
            renderer.render(light, camera);
            DisplayManager.updateDisplay();
        }

        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
