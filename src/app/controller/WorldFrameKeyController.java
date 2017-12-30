package app.controller;

import app.modele.math.Matrix;
import app.modele.math.Vector;
import app.modele.shape.WorldObject;
import app.view.WorldFrame;
import app.view.panel.WorldPanel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class WorldFrameKeyController extends KeyAdapter {
    private final WorldFrame worldFrame;
    private WorldObject obj;
    private WorldPanel mp;

    private double x = -300, y = -100, z = 0;
    private double rX = -100, rY = 0, rZ = 0;

    private final Set<Integer> pressed = new HashSet<>();


    public WorldFrameKeyController(WorldFrame worldFrame) {
        this.worldFrame = worldFrame;
        obj = worldFrame.getObj();
        mp = worldFrame.getMp();
        /*
        new Thread(new Runnable() {
            @Override
            public synchronized void run() {
                while (true) {
                    for (Integer code : pressed) {
                        handleInput(code);
                        updateTransform();
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        */
    }


    private void handleInput(int code) {
        switch (code) {
            case KeyEvent.VK_UP:
                y--;
                break;
            case KeyEvent.VK_DOWN:
                y++;
                break;
            case KeyEvent.VK_LEFT:
                x--;
                break;
            case KeyEvent.VK_RIGHT:
                x++;
                break;
            case KeyEvent.VK_A:
                rZ--;
                break;
            case KeyEvent.VK_E:
                rZ++;
                break;
            case KeyEvent.VK_Z:
                rX--;
                break;
            case KeyEvent.VK_S:
                rX++;
                break;
            case KeyEvent.VK_Q:
                rY++;
                break;
            case KeyEvent.VK_D:
                rY--;
                break;
            case KeyEvent.VK_ESCAPE:
                worldFrame.dispose();
                break;
        }
    }

    private void updateTransform() {
        obj.resetTransform();
        obj.addTransform(Matrix.createTranslation(new Vector(x, y, z)));
        obj.addTransform(Matrix.createRotationX(Math.PI * 2 / 100 * rX / 3));
        obj.addTransform(Matrix.createRotationY(Math.PI * 2 / 100 * rY));
        obj.addTransform(Matrix.createRotationZ(Math.PI * 2 / 100 * rZ / 3));
        mp.setWorldObject(obj.getTransformedObject());
    }

    @Override
    public synchronized void keyPressed(KeyEvent keyEvent) {
        super.keyPressed(keyEvent);
        pressed.add(keyEvent.getKeyCode());
        handleInput(keyEvent.getKeyCode());
        updateTransform();
    }

    @Override
    public synchronized void keyReleased(KeyEvent keyEvent) {
        super.keyPressed(keyEvent);
        pressed.remove(keyEvent.getKeyCode());
    }
}
