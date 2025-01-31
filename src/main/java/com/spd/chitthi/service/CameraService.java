package com.spd.chitthi.service;

import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Point;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.RectVector;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.bytedeco.opencv.global.opencv_imgproc; // Use this instead of org.opencv.imgproc.Imgproc
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public class CameraService {

    private static final String CASCADE_FILE = "haarcascades/haarcascade_fullbody.xml";

    public Mat detectObjects(Mat mat) {

        URL cascadeUrl = getClass().getClassLoader().getResource(CASCADE_FILE);
        if (cascadeUrl == null) {
            System.err.println("Error: Cascade file not found in classpath: " + CASCADE_FILE);
            return mat;
        }

        String cascadePath = cascadeUrl.getPath();
        CascadeClassifier objectDetector = new CascadeClassifier(cascadePath);

        if (objectDetector == null || objectDetector.empty()) {
            System.err.println("Error: Could not load cascade file at " + cascadePath);
            return mat;
        }

        if (objectDetector.isNull()) {
            throw new RuntimeException("Failed to load cascade classifier from path: " + cascadePath);
        }

        RectVector detectedObjects = new RectVector();
        objectDetector.detectMultiScale(mat, detectedObjects);

        for (long i = 0; i < detectedObjects.size(); i++) {
            Rect rect = detectedObjects.get(i);
            Point topLeft = new Point(rect.x(), rect.y());
            Point bottomRight = new Point(rect.x() + rect.width(), rect.y() + rect.height());
            opencv_imgproc.rectangle(mat, topLeft, bottomRight, new Scalar(0, 0, 255, 0), 2, opencv_imgproc.LINE_8, 0); // Red color
        }

        return mat;
    }
}