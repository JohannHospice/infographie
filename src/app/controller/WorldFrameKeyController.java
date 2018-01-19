package app.controller;

import app.modele.math.Calculus;
import app.modele.math.Matrix;
import app.modele.math.Vector;
import app.view.WorldObjectPanel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class WorldFrameKeyController extends KeyAdapter {
    private final Set<Integer> pressed = new HashSet<>();
    private final WorldObjectPanel wp;
    private double x = 0, y = 0, z = 0,
            rX = 0, rY = 0, rZ = 0;
    private double speedTranslate = 10, speedRotation = 1;


    public WorldFrameKeyController(WorldObjectPanel wp) {
        this.wp = wp;
        wp.getCurrentObject();
        /*
        new Thread(() -> {
            while (true) {
                for (Integer code : pressed) {
                    handleInput(code);
                    wp.setCamera(makeCamera());
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();*/
    }


    @Override
    public void keyPressed(KeyEvent keyEvent) {
        super.keyPressed(keyEvent);
        pressed.add(keyEvent.getKeyCode());
        handleInput(keyEvent.getKeyCode());
        // wp.setCamera(makeCamera());
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        super.keyPressed(keyEvent);
        pressed.remove(keyEvent.getKeyCode());
    }

    private Matrix makeCamera() {
        return Calculus.multiply(
                Matrix.createPerspectiveZ(400),
                Calculus.multiply(
                        Calculus.multiply(
                                Calculus.multiply(
                                        Matrix.createRotationY(Math.PI / 100 * rY * -2),
                                        Matrix.createRotationZ(Math.PI * 2 / 100 * rZ / 2)),
                                Matrix.createRotationX(Math.PI * 2 / 100 * rX)),
                        Matrix.createTranslation(new Vector(x, y, z)))
        );
    }

    private void handleInput(int code) {
        switch (code) {
            case KeyEvent.VK_UP:
                z -= speedTranslate;
                break;
            case KeyEvent.VK_DOWN:
                z += speedTranslate;
                break;
            case KeyEvent.VK_LEFT:
                x += speedTranslate;
                break;
            case KeyEvent.VK_RIGHT:
                x -= speedTranslate;
                break;
            case KeyEvent.VK_P:
                y -= speedTranslate;
                break;
            case KeyEvent.VK_M:
                y += speedTranslate;
                break;
            case KeyEvent.VK_A:
                rZ -= speedRotation;
                break;
            case KeyEvent.VK_E:
                rZ += speedRotation;
                break;
            case KeyEvent.VK_Z:
                rX -= speedRotation;
                break;
            case KeyEvent.VK_S:
                rX += speedRotation;
                break;
            case KeyEvent.VK_Q:
                rY += speedRotation;
                break;
            case KeyEvent.VK_D:
                rY -= speedRotation;
                break;
        }
    }
}
