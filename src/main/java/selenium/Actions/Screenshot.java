package selenium.Actions;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

public class Screenshot {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

    public static void takeScreenshot() {
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

        try {
            BufferedImage capture = new Robot().createScreenCapture(screenRect);
            ImageIO.write(capture, "png", new File("src/test/resources/screenshots/" + sdf.format(new Date()) + ".png"));
        } catch (IOException | AWTException e) {
            e.printStackTrace();
        }
    }
}
