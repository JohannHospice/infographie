package app;

import app.view.WorldFrame;

import javax.swing.*;

public class Application {

    private static void createUI() {
        WorldFrame worldFrame = new WorldFrame();
        worldFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        worldFrame.setVisible(true);
        worldFrame.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Application::createUI);
    }

}
