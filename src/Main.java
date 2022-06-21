import org.opencv.core.Core;

import java.awt.*;

public class Main {
    public static void main(String[] args){
        //Load OpenCV
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //Call new selection window on Swing's EventQueue
        EventQueue.invokeLater(SelectionWindow::new);
    }
}
