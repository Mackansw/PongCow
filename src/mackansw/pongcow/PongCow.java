package mackansw.pongcow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class PongCow {

    //The cow image rendered onscreen
    private ImageIcon cow = new ImageIcon(getClass().getResource("cow.jpg"));

    //The window panel
    private JPanel windowPanel;

    //Returns if the image is moving downwards/falling
    private boolean falling = true;

    //Returns the current background
    private int background = 0;

    //Returns the bounds of the image
    private int cowBounds = 100;

    //Returns the x and y coordinates of the image
    private int cowX = 0, cowY = 0;

    //Returns the width and height of the window
    private int windowWidth, windowHeight;

    private void startPongLoop(boolean running) {
        int sleepTime = 10;
        while (running) {
            for (; cowX < windowWidth - cowBounds; cowX++) {
                moveCowY();
                if(cowX == 0) {
                    updateBackground();
                }
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (; cowX > 0; cowX--) {
                moveCowY();
                if(cowX == windowWidth - cowBounds) {
                    updateBackground();
                }
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void moveCowY() {
        if(falling) {
            if (cowY < windowHeight - cowBounds) {
                cowY++;
            }
            else {
                falling = false;
                updateBackground();
            }
        }
        else {
            if (cowY > 0) {
                cowY--;
            }
            else {
                falling = true;
                updateBackground();
            }
        }
        windowPanel.repaint();
    }

    private void updateBackground() {
        background++;
        switch (background) {
            case 1:
                windowPanel.setBackground(Color.pink);
                break;
            case 2:
                windowPanel.setBackground(Color.cyan);
                break;
            case 3:
                windowPanel.setBackground(Color.red);
                break;
            default:
                windowPanel.setBackground(Color.green);
                background = 0;
        }
    }

    private void buildGui() {
        JFrame window = new JFrame("Kossan göran spelar pong!");
        window.setSize(600, 500);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        windowPanel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                g.drawImage(cow.getImage(), cowX, cowY, cowBounds, cowBounds, null);
            }
        };

        window.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                windowHeight = windowPanel.getHeight();
                windowWidth = windowPanel.getWidth();
            }
        });

        window.add(windowPanel);
        window.setVisible(true);
    }

    private void start() {
        buildGui();
        startPongLoop(true);
    }

    //Main method
    public static void main(String args[]) {
        new PongCow().start();
    }
}