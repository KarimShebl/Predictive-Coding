import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageConverter {
    public static void main(String[] args) throws IOException {
        int[][] x = imageToArray("grey.jpeg");

        int[][] x2 = new int[200][200];

        for (int i = 0; i < x2.length; i++) {
            for (int j = 0; j < x2[0].length; j++) {
                int vr, vg, vb;
                // vr = (x[i][j] >> 16) & 0xFF;
                // vg = (x[i][j] >> 8) & 0xFF;
                // vb = x[i][j] & 0xFF;
                // int vf = (vr + vg + vb) / 3;
                // int value = 0xff000000 | ((int) vf << 16) | ((int) vf << 8) | (int) vf;
                // // int value = x[i + 60][j + 150];
                // x2[i][j] = value;
                x2[i][j] = x[i][j];
            }
        }
        arrayToImage(x2);
    }
    public static int[][] imageToArray(String imagePath) {
        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            int width = image.getWidth();
            int height = image.getHeight();
            int[][] pixelArray = new int[height][width];

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int pixelValue = image.getRGB(j, i) & 0xFF;
                    pixelArray[i][j] = pixelValue;
                }
            }
            return pixelArray;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void arrayToImage(int[][] pixelArray) {
        int height = pixelArray.length;
        int width = pixelArray[0].length;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pixelValue = pixelArray[i][j];
                int rgb = (pixelValue << 16) | (pixelValue << 8) | pixelValue;
                image.setRGB(j, i, rgb);
            }
        }

        try {
            String projectPath = System.getProperty("user.dir");
            ImageIO.write(image, "png", new File(projectPath + File.separator + "output" + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}