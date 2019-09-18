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
    private float rodRotation = 12;

    private float SPEED;
    private float rodSpeedY;
    private float rodRotationX;

    private int cycle;

    public Piston(TexturedModel texturedRod, TexturedModel texturedHead, TexturedModel texturedValve,
                  Vector3f rodPosition, float speed, float startingRotation, int cycle) {
        this.SPEED = speed;
        this.rodSpeedY = speed * 0.002f;
        this.rodRotationX = speed * 0.018f;
        this.cycle = cycle;

        this.rod = new Entity(texturedRod, rodPosition, startingRotation, 90,180, 1);
        this.head = new Entity(texturedHead, new Vector3f(rodPosition.x, rodPosition.y + 1.13f, rodPosition.z - 0.38f), 0,0,270, 0.18f);
    }

    public void move() {
        if (rod.getPosition().y >= rodHighestY || rod.getPosition().y <= rodLowestY) {
            cycle++;
        }
        rod.increasePosition(0, (cycle % 2 == 0 ? 1 : -1) * rodSpeedY, 0);
        head.increasePosition(0, (cycle % 2 == 0 ? 1 : -1) * rodSpeedY, 0);

        boolean goingUp = cycle % 2 == 0;

        rod.increaseRotation((rod.getPosition().y <= rodMiddleY ? 1 : -1) * rodRotationX, 0 ,0);

//        if (rod.getPosition().y <= rodMiddleY) {
//            rod.increaseRotation((goingUp ? 1 : -1) * rodRotationX, 0, 0);
//            System.out.println('1');
//        } else {
//            rod.increaseRotation((goingUp ? -1 : 1) * rodRotationX, 0, 0);
//            System.out.println('2');
//        }
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

    public void setSPEED(float SPEED) {
        this.SPEED = SPEED;
    }
}
