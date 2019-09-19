package entities;

import models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

public class Piston {
    private final Entity rod;
    private final Entity head;
    private final Entity valveIn;
    private final Entity valveOut;

    private final float valveSpeed;

    private final float rodSpeedY;
    private final float rodRotationX;

    private int cycle;

    public Piston(TexturedModel texturedRod, TexturedModel texturedHead, TexturedModel texturedValve,
                  Vector3f rodPosition, float speed, float startingRotation, int cycle) {
        this.rodSpeedY = speed * 0.002f;
        this.rodRotationX = speed * 0.018f;
        this.valveSpeed = speed * 0.0007f;
        this.cycle = cycle;

        this.rod = new Entity(texturedRod, rodPosition, startingRotation, 90, 180, 1);
        this.head = new Entity(texturedHead, new Vector3f(rodPosition.x, rodPosition.y + 1.13f, rodPosition.z - 0.38f), 0, 0, 270, 0.18f);
        this.valveIn = new Entity(texturedValve, new Vector3f(rodPosition.x, 10.5f, 0.9f), 35, 0, 0, 0.03f);
        this.valveOut = new Entity(texturedValve, new Vector3f(rodPosition.x, 10.5f, -0.9f), -35, 0, 0, 0.03f);
    }

    public void move() {
        float rodHighestY = 7.74f;
        float rodLowestY = 4.42f;
        if (rod.getPosition().y >= rodHighestY || rod.getPosition().y <= rodLowestY) {
            cycle = cycle % 4 + 1;
        }
        rod.increasePosition(0, (cycle % 2 == 0 ? 1 : -1) * rodSpeedY, 0);
        head.increasePosition(0, (cycle % 2 == 0 ? 1 : -1) * rodSpeedY, 0);

        float rodMiddleY = (rodLowestY + rodHighestY) / 2;
        rod.increaseRotation((rod.getPosition().y <= rodMiddleY ? 1 : -1) * rodRotationX, 0, 0);

        if (cycle == 2) {
            float valveOutOpenHeight = 6f;
            if (rod.getPosition().y >= valveOutOpenHeight) {
                float valveOutCloseHeight = valveOutOpenHeight + (rodHighestY - valveOutOpenHeight) / 2;
                if (rod.getPosition().y <= valveOutCloseHeight) {
                    valveOut.increasePosition(0, -valveSpeed, valveSpeed);
                } else {
                    valveOut.increasePosition(0, valveSpeed, -valveSpeed);
                }
            }
        }

        if (cycle == 3) {
            float valveInCloseHeight = 6f;
            if (rod.getPosition().y >= valveInCloseHeight) {
                float valveInGoBackHeight = valveInCloseHeight + (rodHighestY - valveInCloseHeight) / 2;
                if (rod.getPosition().y >= valveInGoBackHeight) {
                    valveIn.increasePosition(0, -valveSpeed, -valveSpeed);
                } else {
                    valveIn.increasePosition(0, valveSpeed, valveSpeed);
                }
            }
        }
    }

    public Entity getRod() {
        return rod;
    }

    public Entity getHead() {
        return head;
    }

    public Entity getValveIn() {
        return valveIn;
    }

    public Entity getValveOut() {
        return valveOut;
    }

}
