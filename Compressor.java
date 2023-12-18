abstract public class Compressor {
    protected abstract int[][] Compress(int[][] image, int bits);

    protected abstract int[][] Decompress(int[][] compressedImage);

    public void compress(String inputFileName, String ouputFileName, int bits) {
    }

    public void decompress(String inputFileName, String outputFilename) {
    }

}
