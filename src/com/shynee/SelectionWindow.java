package com.shynee;

import com.shynee.FrameHandler.FrameRenderer;
import com.shynee.FrameHandler.Output;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SelectionWindow implements ActionListener {

    private final JFrame frame;

    private final JTextField field;
    private final JButton button;
    private final JProgressBar progressBar;

    private FrameRenderer renderer;
    private SwingWorker<Void, Integer> worker;

    //Creates basic menu for inputting filepath
    SelectionWindow(){
        frame = new JFrame();
        frame.setName("ASCII Converter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout(FlowLayout.LEFT));
        frame.setPreferredSize(new Dimension(425, 125));

        JLabel title = new JLabel("Filepath to mp4:", JLabel.RIGHT);
        title.setFont(new Font("Arial", Font.PLAIN, 13));
        title.setPreferredSize(new Dimension(100, 25));
        frame.add(title);

        field = new JTextField();
        field.setPreferredSize(new Dimension(200, 30));
        frame.add(field);

        button = new JButton("Submit");
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setPreferredSize(new Dimension(75, 25));
        button.addActionListener(this);
        frame.setFocusable(false);
        frame.add(button);

        JLabel progressLabel = new JLabel("Progress: ", JLabel.RIGHT);
        progressLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        progressLabel.setPreferredSize(new Dimension(100, 25));
        frame.add(progressLabel);

        progressBar = new JProgressBar();
        progressBar.setPreferredSize(new Dimension(280, 20));
        frame.add(progressBar);

        frame.pack();
        frame.setVisible(true);
    }

    //Checks for when the button is clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button){

            File file = new File(field.getText());

            if (file.exists() && file.getName().contains(".mp4")){
                create(file);
                Thread thread = new Thread(renderer);
                this.worker.execute();
                thread.start();
            } else {
                JOptionPane.showMessageDialog(null,
                        "The file you submitted does not exist or is not an mp4 file",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    void create(File videoFile) {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        this.renderer = new FrameRenderer(videoFile, d.height);
        progressBar.setMaximum((int) renderer.totalFrames);
        this.worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                while (renderer.framesRendered != renderer.totalFrames) {
                    publish(renderer.framesRendered);
                }
                return null;
            }

            @Override
            protected void process(java.util.List<Integer> chunks) {
                progressBar.setValue(chunks.get(chunks.size() - 1));
            }

            @Override
            protected void done() {
                frame.dispose();
                new Output(renderer.frameList);
            }
        };
    }
}
