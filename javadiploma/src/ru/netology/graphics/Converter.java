package ru.netology.graphics;

import ru.netology.graphics.image.BadImageSizeException;
import ru.netology.graphics.image.ColorSchema;
import ru.netology.graphics.image.TextColorSchema;
import ru.netology.graphics.image.TextGraphicsConverter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Converter implements ru.netology.graphics.image.TextGraphicsConverter {

    private int maxWidth;
    private int maxHeight;
    private double maxRatio;

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {
        BufferedImage img = ImageIO.read(new URL(url));

        double ratio;

        if (img.getWidth() > img.getHeight()) {
            ratio = (double) img.getWidth() / (double) img.getHeight();
        } else {
            ratio = (double) img.getHeight() / (double) img.getWidth();
        }

        if (Math.abs(ratio) > Math.abs(getMaxRatio())) {
            throw new BadImageSizeException(getMaxRatio(), ratio);
        }

        int newWidth = img.getWidth();
        int newHeight = img.getHeight();

        if (img.getWidth() > getMaxWidth()) {
            newWidth = getMaxWidth();
            newHeight = (newWidth * img.getHeight()) / img.getWidth();
        }

        if (newHeight > getMaxHeight()) {
            newHeight = getMaxHeight();
            newWidth = (newHeight * img.getWidth()) / img.getHeight();
        }

        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        var bwRaster = bwImg.getRaster();
        TextColorSchema colorSchema = new ColorSchema();
        char[][] chars = new char[bwImg.getHeight()][bwImg.getWidth()];
        for (int w = 0; w < bwImg.getWidth(); w++) {
            for (int h = 0; h < bwImg.getHeight(); h++) {
                int color = bwRaster.getPixel(w, h, new int[3])[0];
                char c = colorSchema.convert(color);
                chars[h][w] = c;
            }
        }

        StringBuilder captureToString = new StringBuilder();
        for (char[] pixelChars : chars) {
            for (char c : pixelChars) {
                captureToString.append(c).append(" ");
            }
            captureToString.append("\n");
        }
        return captureToString.toString();
    }

    @Override
    public void setMaxWidth(int width) {
        this.maxWidth = width;
    }

    @Override
    public void setMaxHeight(int height) {
        this.maxHeight = height;
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public double getMaxRatio() {
        return maxRatio;
    }
}

