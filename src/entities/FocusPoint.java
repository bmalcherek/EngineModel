package entities;

import models.TexturedModel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;

public class FocusPoint extends Entity {
    private static final float HORIZONTAL_SPEED = 20;
    private static final float VERTICAL_SPEED = 10;
    private static final float TURN_SPEED = 100;
    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float currentUpwardsSpeed = 0;
    private float currentHorizontalSpeed = 0;

    public FocusPoint(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }

    void move() {
        checkInputs();
//        super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
        float rotation = (180 - super.getRotY()) % 360;
        float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
//        float dx = (float) (distance * Math.sin(Math.toRadians(rotation)));
        float dx = currentHorizontalSpeed * DisplayManager.getFrameTimeSeconds();
        float dy = currentUpwardsSpeed * DisplayManager.getFrameTimeSeconds();
        float dz = (float) (distance * Math.cos(Math.toRadians(rotation)));
        super.increasePosition(dx, dy, dz);
    }

    private void checkInputs() {
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            this.currentSpeed = HORIZONTAL_SPEED;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            this.currentSpeed = -HORIZONTAL_SPEED;
        } else {
            this.currentSpeed = 0;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            this.currentHorizontalSpeed = -HORIZONTAL_SPEED;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            this.currentHorizontalSpeed = HORIZONTAL_SPEED;
        } else {
            this.currentHorizontalSpeed= 0;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            this.currentUpwardsSpeed = VERTICAL_SPEED;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
            this.currentUpwardsSpeed = -VERTICAL_SPEED;
        } else {
            this.currentUpwardsSpeed = 0;
        }
    }

    public float getRotation() {
        return super.getRotY();
    }
}

