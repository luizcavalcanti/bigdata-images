package br.edu.ufam.icomp.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageUtils {

    public static Color getAverageColor(BufferedImage bitmap) {
        long redBucket = 0;
        long greenBucket = 0;
        long blueBucket = 0;
        long pixelCount = 0;
        for (int y = 0; y < bitmap.getHeight(); y++) {
            for (int x = 0; x < bitmap.getWidth(); x++) {
                Color c = new Color(bitmap.getRGB(x, y));
                pixelCount++;
                redBucket += c.getRed();
                greenBucket += c.getGreen();
                blueBucket += c.getBlue();
            }
        }
        return new Color((int) (redBucket / pixelCount), (int) (greenBucket / pixelCount),
                (int) (blueBucket / pixelCount));
    }

    public static int[] generateBWHistogram(BufferedImage bitmap, int bucketCount) {
        int[] histogram = new int[bucketCount];
        for (int y = 0; y < bitmap.getHeight(); y++) {
            for (int x = 0; x < bitmap.getWidth(); x++) {
                Color c = new Color(bitmap.getRGB(x, y));
                int grey = (c.getRed() + c.getBlue() + c.getGreen()) / 3;
                int index = grey % bucketCount;
                histogram[index]++;
            }
        }
        return histogram;
    }

    // TODO configure split number
    public static BufferedImage[] splitImage(BufferedImage img) {
        int chunkHeight = img.getHeight() / 2;
        int chunkWidth = img.getWidth() / 2;
        int verticalPieces = img.getHeight() / chunkHeight;
        int horizontalPieces = img.getWidth() / chunkWidth;
        BufferedImage result[] = new BufferedImage[4];
        int piece = 0;
        for (int x = 0; x < verticalPieces; x++) {
            for (int y = 0; y < horizontalPieces; y++) {
                BufferedImage chunk = new BufferedImage(chunkWidth, chunkHeight, img.getType());
                Graphics2D g = chunk.createGraphics();
                g.drawImage(img, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y
                        + chunkWidth, chunkHeight * x + chunkHeight, null);
                result[piece] = chunk;
                piece++;
            }
        }
        return result;
    }

}
