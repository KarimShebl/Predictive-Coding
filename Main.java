public class Main {
    public static void main(String[] args) {
        int[][] array = {{5, 7, 8, 10}, {6, 6, 9, 11}, {7, 8, 11, 13}, {9, 10, 11, 14}};
        PredictiveCompressor p = new PredictiveCompressor();
        int[][] pixelArray = ImageConverter.imageToArray("tst3.png");

        for (int i = 0; i < pixelArray.length; ++i)
            for (int j = 0; j < pixelArray[0].length; ++j)
                pixelArray[i][j] &= 255;

        int[][] compressed = p.Compress(pixelArray, 3);
        int[][] img = p.Decompress(compressed);
        for (int i = 0; i < img.length; ++i) {
            for (int j = 0; j < img[0].length; ++j) {
                int val = img[i][j];
                img[i][j] |= (val << 8);
                img[i][j] |= (val << 16);
                img[i][j] |= (int)0xff000000;
            }
        }
        ImageConverter.arrayToImage(img);
    }
}