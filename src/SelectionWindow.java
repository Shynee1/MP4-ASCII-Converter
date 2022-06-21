import FrameHandler.Output;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SelectionWindow extends JFrame implements ActionListener {

    JTextField field;
    JButton button;

    //Creates basic menu for inputting filepath
    SelectionWindow(){
        this.setName("ASCII Converter");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());


        JLabel label = new JLabel("Filepath to mp4:");
        label.setFont(new Font("Arial", Font.PLAIN, 13));
        label.setPreferredSize(new Dimension(100, 25));
        this.add(label);

        field = new JTextField();
        field.setPreferredSize(new Dimension(200, 30));
        this.add(field);

        button = new JButton("Submit");
        button.setPreferredSize(new Dimension(75, 25));
        button.addActionListener(this);
        this.setFocusable(false);
        this.add(button);

        this.pack();
        this.setVisible(true);
    }

    //Checks for when the button is clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button){

            File file = new File(field.getText());

            if (file.exists() && file.getName().contains(".mp4")){
                this.dispose();
                new Output(file);
            } else {
                JOptionPane.showMessageDialog(null,
                        "The file you submitted does not exist or is not an mp4 file",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
