package com.shynee.FrameHandler;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FrameRenderer implements Runnable{

    public final double totalFrames;
    public int framesRendered = 0;
    public List<String> frameList = new ArrayList<>();

    private final VideoCapture capture;
    private final char[] asciiChars = {'@', '#', 'S', '%', '?', '*', '+', ';', ':', ',', '.'};
    private final int screenHeight;

    public FrameRenderer(File videoFile, int screenHeight){
        this.screenHeight = screenHeight;

        capture = new VideoCapture(videoFile.getAbsolutePath());
        totalFrames = capture.get(Videoio.CAP_PROP_FRAME_COUNT);
    }

    public void renderFrames(){
        if (capture.isOpened()){
            Mat frame = new Mat();
            while (capture.read(frame)) {
                Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);
                Mat resizedFrame = new Mat();
                double scaleFactor = screenHeight / frame.size().height / 6;
                //Resize image to make it fullscreen
                Imgproc.resize(frame, resizedFrame, new Size(), scaleFactor, scaleFactor, Imgproc.INTER_LINEAR);

                frameList.add(getASCII(resizedFrame));
                framesRendered++;
            }
        } else {
            System.err.println("Could not open capture");
        }
    }

    public String getASCII(Mat frame) {

        StringBuilder finalDisplay = new StringBuilder();

        for (int i = 0; i < frame.rows(); i++) {
            for (int j = 0; j < frame.cols(); j++) {

                double[] colorValues = frame.get(i, j);

                //Get the matching ascii character
                char c = asciiChars[(int) Math.round(colorValues[0] / 25)];

                finalDisplay.append(c);
            }
            finalDisplay.append("\n");
        }
        return finalDisplay.toString();
    }

    @Override
    public void run() {
        renderFrames();
    }
}


