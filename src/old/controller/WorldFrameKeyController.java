package old.controller;

import old.modele.math.Matrix;
import old.modele.math.Vector;
import old.modele.shape.WorldObject;
import old.view.panel.WorldPanel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class WorldFrameKeyController extends KeyAdapter {
    private final Set<Integer> pressed = new HashSet<>();
    private WorldObject obj;
    private WorldPanel mp;
    private double x = -300, y = -100, z = 10;
    private double rX = -100, rY = 0, rZ = 0;


    public WorldFrameKeyController(WorldObject obj, WorldPanel mp) {
        this.obj = obj;
        this.mp = mp;
        new Thread(() -> {
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
        }).start();
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
        }
    }

    private synchronized void updateTransform() {
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
