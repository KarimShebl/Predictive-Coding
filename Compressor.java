public interface Compressor {
    public int[][] Compress(int[][] image, int bits);
    public int[][] Decompress(int[][] compressedImage);
}
