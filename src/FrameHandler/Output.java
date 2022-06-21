package FrameHandler;

import FrameHandler.FrameRenderer;
import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Dimension2D;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class Output  {
    
    JFrame frame;

    public Output(File videoFile){
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

        //Render the frames and store them as a list of strings
        FrameRenderer renderer = new FrameRenderer(videoFile, frame.getWidth(), frame.getHeight());
        List<String> frameList = renderer.renderFrames();

        //Display the list of strings in a JPanel
        DisplayFrames display = new DisplayFrames(frameList, frame.getWidth(), frame.getHeight(), frame);
        frame.add(display);

        frame.setVisible(true);

        display.startDisplay();

    }


}
