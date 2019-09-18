package entities;

import models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

public class Piston {
    private Entity rod;
    private Entity head;
    private Entity valveIn;
    private Entity valveOut;

    private float rodLowestY = 4.42f;
    private float rodHighestY = 7.74f;
    private float rodMiddleY = (rodLowestY + rodHighestY) / 2;
    private float valveOutOpenHeight = 6f;
    private float valveOutCloseHeight = valveOutOpenHeight + (rodHighestY - valveOutOpenHeight) / 2;
    private float valveInCloseHeight = 6f;
    private float valveInGoBackHeight = valveInCloseHeight + (rodHighestY - valveInCloseHeight) / 2;

    private float valveSpeed;

    private float SPEED;
    private float rodSpeedY;
    private float rodRotationX;

    private int cycle;

    public Piston(TexturedModel texturedRod, TexturedModel texturedHead, TexturedModel texturedValve,
                  Vector3f rodPosition, float speed, float startingRotation, int cycle) {
        this.SPEED = speed;
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
        if (rod.getPosition().y >= rodHighestY || rod.getPosition().y <= rodLowestY) {
            cycle = cycle % 4 + 1;
        }
        rod.increasePosition(0, (cycle % 2 == 0 ? 1 : -1) * rodSpeedY, 0);
        head.increasePosition(0, (cycle % 2 == 0 ? 1 : -1) * rodSpeedY, 0);

        boolean goingUp = cycle % 2 == 0;

        rod.increaseRotation((rod.getPosition().y <= rodMiddleY ? 1 : -1) * rodRotationX, 0, 0);

        if (cycle == 2) {
            if (rod.getPosition().y >= valveOutOpenHeight) {
                if (rod.getPosition().y <= valveOutCloseHeight) {
                    valveOut.increasePosition(0, -valveSpeed, valveSpeed);
                } else {
                    valveOut.increasePosition(0, valveSpeed, -valveSpeed);
                }
            }
        }

        if (cycle == 3) {
            if (rod.getPosition().y >= valveInCloseHeight) {
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

    public int getCycle() {
        return cycle;
    }
}
