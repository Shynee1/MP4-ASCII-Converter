package com.shynee.FrameHandler;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Output  {
    
    JFrame frame;

    public Output(java.util.List<String> frames){
        //Set up the output window
        frame = new JFrame();
        frame.setName("ASCII Converter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.white);
        //Set the window to fullscreen
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(d.getSize());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);

        DisplayFrames displayFrames = new DisplayFrames(frames, d.width, d.height, frame);
        frame.add(displayFrames);

        frame.setVisible(true);

        displayFrames.startDisplay();
    }


}
