import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedList;

abstract public class Compressor {

    private final int MAXQUNTIZEDARRAYHEIGHTSIZE = 3 * 8;
    private final int MAXQUNTIZEDARRAYWIDTHSIZE = 3 * 8;
    private final int MAXQUNTIZEDARRAYCELLSIZE = 2 * 8;

    private final int MAXQUNTIZATIONTableLENSIZE = 8;

    private final int MAXQUNTIZATIONTableROWLENSIZE = 8;

    private final int MAXQUNTIZATIONTableCELLSIZE = 2 * 8;

    protected abstract int[][] Compress(int[][] image, int bits);

    protected abstract int[][] Decompress(int[][] compressedImage);

    public void compress(String inputFileName, String ouputFileName, int bits) {
        int[][] pixelArray = ImageConverter.imageToArray(inputFileName);

        int[][] red = new int[pixelArray.length][pixelArray[0].length],
                green = new int[pixelArray.length][pixelArray[0].length],
                blue = new int[pixelArray.length][pixelArray[0].length];

        for (int i = 0; i < pixelArray.length; ++i)
            for (int j = 0; j < pixelArray[0].length; ++j)
                blue[i][j] = (int) 0x0 | (pixelArray[i][j] & 255);

        for (int i = 0; i < pixelArray.length; ++i)
            for (int j = 0; j < pixelArray[0].length; ++j)
                green[i][j] = (int) 0x0 | (pixelArray[i][j] & (255 << 8));

        for (int i = 0; i < pixelArray.length; ++i)
            for (int j = 0; j < pixelArray[0].length; ++j)
                red[i][j] = (int) 0x0 | (pixelArray[i][j] & (255 << 16));

        int[][] quntizedArray = Compress(blue, bits);
        LinkedList<Integer>[] quantizationTable = Quantizer.quantizedData;

        BinaryHandler binaryHandler = new BinaryHandler();

        binaryHandler.pushFrontBits(blue.length, MAXQUNTIZEDARRAYHEIGHTSIZE);
        binaryHandler.pushFrontBits(blue[0].length, MAXQUNTIZEDARRAYWIDTHSIZE);

        for (int i = 0; i < quntizedArray.length; i++) {
            for (int j = 0; j < quntizedArray[i].length; j++) {
                binaryHandler.pushFrontBits(quntizedArray[i][j], MAXQUNTIZEDARRAYCELLSIZE);
            }
        }

        binaryHandler.pushFrontBits(quantizationTable.length, MAXQUNTIZATIONTableLENSIZE);

        int mini, maxi;
        for (int i = 0; i < quantizationTable.length; i++) {
            mini = quantizationTable[i].getFirst();
            maxi = quantizationTable[i].getLast();
            binaryHandler.pushFrontBits(mini, MAXQUNTIZATIONTableCELLSIZE);
            binaryHandler.pushFrontBits(maxi, MAXQUNTIZATIONTableCELLSIZE);
        }

        LinkedList<Byte> compressedStream = binaryHandler.toByteArray();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ouputFileName + ".bin"))) {

            for (Byte byte1 : compressedStream) {
                writer.write(byte1);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("writing compressed stream on file done");

    }

    public void deCompress(String inputFileName, String outputFilename) {

    }

}
