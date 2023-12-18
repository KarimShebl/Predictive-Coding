public class Main {
    public static void main(String[] args) {
        PredictiveCompressor p = new PredictiveCompressor();
        int[][] pixelArray = ImageConverter.imageToArray("grey.jpeg");

        for (int i = 0; i < pixelArray.length; ++i)
            for (int j = 0; j < pixelArray[0].length; ++j)
                pixelArray[i][j] = (int) 0x0 | (pixelArray[i][j] & 255);

        int[][] compressed = p.Compress(pixelArray, 3);
        // int[][] compressed = p.Compress(array, 1);

        int[][] img = p.Decompress(compressed);
        System.out.println(HelpMe.getMax(img));
        System.out.println(HelpMe.getMin(img));
        for (int i = 0; i < img.length; ++i) {
            for (int j = 0; j < img[0].length; ++j) {
                int blue = img[i][j];
                int green = (blue << 8);
                int red = (blue << 16);
                img[i][j] = (int) 0xff000000 | red | green | blue;
            }
        }
        ImageConverter.arrayToImage(img, "output");

    }
}