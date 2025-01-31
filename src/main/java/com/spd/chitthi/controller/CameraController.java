package com.spd.chitthi.controller;

import com.spd.chitthi.service.CameraService;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.opencv_core.Mat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@RestController
@RequestMapping("/api/camera")
public class CameraController {

    @Autowired
    CameraService cameraService;

    @GetMapping("/detect")
    public String detectObjects() throws Exception {
        FrameGrabber grabber = FrameGrabber.createDefault(0);
        grabber.start();

        // Capture a frame
        Frame frame = grabber.grab();
        if (frame == null) {
            grabber.stop();
            return "No frame captured!";
        }

        // Convert Frame to Mat
        OpenCVFrameConverter.ToMat converter = new OpenCVFrameConverter.ToMat();
        Mat mat = converter.convert(frame);

        // Detect objects in the frame
        Mat processedMat = cameraService.detectObjects(mat);

        // Convert the processed Mat back to Frame
        Frame processedFrame = converter.convert(processedMat);

        // Convert Frame to BufferedImage
        Java2DFrameConverter java2DFrameConverter = new Java2DFrameConverter();
        BufferedImage bufferedImage = java2DFrameConverter.convert(processedFrame);

        // Convert BufferedImage to base64 string
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", baos);
        String base64String = Base64.getEncoder().encodeToString(baos.toByteArray());

        // Save the image and return the path
        String savedPath = saveBase64Image(base64String, "detected_image_" + UUID.randomUUID() + ".jpg");

        // Stop the grabber
        grabber.stop();

        return "Image saved at: " + savedPath;
    }

    private String saveBase64Image(String base64String, String fileName) {
        try {
            byte[] imageBytes = Base64.getDecoder().decode(base64String);

            String outputDir = "src/main/resources/static/images";
            File outputFile = new File(outputDir, fileName);

            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                fos.write(imageBytes);
            }

            System.out.println("Image saved successfully: " + outputFile.getAbsolutePath());
            return outputFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error saving image: " + e.getMessage();
        }
    }
}

