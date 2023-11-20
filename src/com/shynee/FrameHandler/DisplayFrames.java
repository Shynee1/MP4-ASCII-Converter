package com.shynee.FrameHandler;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DisplayFrames extends JPanel {

    int iterations = 0;
    ScheduledExecutorService exec;
    List<String> frames;
    String currentFrame;
    JFrame parentFrame;

    public DisplayFrames(List<String> frames, int screenWidth, int screenHeight, JFrame parentFrame) {
        this.setSize(new Dimension(screenWidth, screenHeight));
        this.frames = frames;
        this.parentFrame = parentFrame;

        exec = Executors.newSingleThreadScheduledExecutor();
    }

    public void startDisplay(){
        if (frames != null && !frames.isEmpty()) {

            Runnable displayFrames = () -> {
                if (iterations < frames.size()) {
                    this.currentFrame = frames.get(iterations);
                    repaint();
                    iterations++;
                } else {
                    //Make sure it displays the last frame
                    this.currentFrame = frames.get(frames.size() - 1);
                    //Shut down the executor loop
                    exec.shutdown();
                    //Close the window
                    parentFrame.dispose();
                    //Shut down the program
                    System.exit(0);
                }
            };
            exec.scheduleAtFixedRate(displayFrames, 0, 33, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if (currentFrame != null){
            //Clear the screen before displaying a new frame
            super.paintComponent(g);

            g2d.setFont(new Font("Consolas", Font.PLAIN, 10));

            int y = 0;
            for (String s : currentFrame.split("\n")){
                g2d.drawString(s, 0, y);
                //Displays new line
                y += 6;
            }

        }
    }

}
