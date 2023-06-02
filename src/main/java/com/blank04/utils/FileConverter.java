package com.blank04.utils;

import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@UtilityClass
public class FileConverter {

    public static BufferedImage convertMultipart(MultipartFile file) throws IOException {
        return ImageIO.read(file.getInputStream());
    }
}
