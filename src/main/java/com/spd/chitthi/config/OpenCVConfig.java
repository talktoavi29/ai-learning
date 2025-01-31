package com.spd.chitthi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.bytedeco.opencv.global.opencv_core;

@Configuration
public class OpenCVConfig {
    static {
        try {
            org.bytedeco.javacpp.Loader.load(opencv_core.class);
            System.out.println("OpenCV Loaded: " + opencv_core.CV_VERSION);
        } catch (Exception e) {
            System.err.println("Failed to load OpenCV: " + e.getMessage());
        }
    }

    @Bean
    public String initializeOpenCV() {
        return "OpenCV Initialized Successfully!";
    }
}
