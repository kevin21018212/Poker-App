package com.example.poker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith({SpringExtension.class, OutputCaptureExtension.class})
@SpringBootTest(classes = Main.class)
public class MainTest {

    @Test
    public void mainTest(CapturedOutput output) {
        // Execute the main method
        Main.main(new String[]{});

        // Verify that the application started successfully
        assertTrue(output.getOut().contains("Started Main"), "Application should start successfully");
    }
}
