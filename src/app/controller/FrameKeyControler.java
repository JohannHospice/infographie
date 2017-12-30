package app.controller;

import app.modele.map.DiamondSquare;
import app.view.Frame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FrameKeyControler extends KeyAdapter {

    private Frame frame;

    public FrameKeyControler(final Frame frame) {
        this.frame = frame;
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        int code = keyEvent.getKeyCode();
        switch (code) {
            case KeyEvent.VK_ESCAPE:
                frame.dispose();
                break;
            case KeyEvent.VK_P:
            case KeyEvent.VK_SPACE:
                frame.randomizeColor();
                break;
            case KeyEvent.VK_G:
            case KeyEvent.VK_ENTER:
                frame.generate();
                break;
            case KeyEvent.VK_UP:
                if (frame.getMapGen() instanceof DiamondSquare) {
                    DiamondSquare diamondSquare = ((DiamondSquare) frame.getMapGen());
                    diamondSquare.setN(diamondSquare.getN() + 1);
                    System.out.println(diamondSquare.getN());
                }
                frame.generate();
                break;
            case KeyEvent.VK_DOWN:
                if (frame.getMapGen() instanceof DiamondSquare) {
                    DiamondSquare diamondSquare = ((DiamondSquare) frame.getMapGen());
                    diamondSquare.setN(diamondSquare.getN() - 1);
                    System.out.println(diamondSquare.getN());

                }
                frame.generate();
                break;
        }
    }

}
