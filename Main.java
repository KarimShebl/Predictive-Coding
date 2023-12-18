public class Main {
    public static void main(String[] args) {
        Compressor com = new PredictiveCompressor();

        com.compress("grey.jpeg", "out", 8);
        com.deCompress("out.bin", "imgout");
    }
}