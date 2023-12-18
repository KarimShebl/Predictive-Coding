
public class PredictiveCompressor extends Compressor {

    private int[][] computePredictedArray(int[][] image) {
        int height = image.length;
        int width = image[0].length;
        int[][] predictedArray = HelpMe.deepCopy(image);
        for (int i = 1; i < height; i++) {
            for (int j = 1; j < width; j++) {
                int val;
                if (Math.min(image[i][j - 1], image[i - 1][j]) >= image[i - 1][j - 1])
                    val = Math.max(image[i][j - 1], image[i - 1][j]);
                else if (Math.max(image[i][j - 1], image[i - 1][j]) <= image[i - 1][j - 1])
                    val = Math.min(image[i][j - 1], image[i - 1][j]);
                else
                    val = image[i][j - 1] + image[i - 1][j] - image[i - 1][j - 1];
                predictedArray[i][j] = val;
            }
        }
        return predictedArray;
    }

    private int[][] computeDifferenceArray(int[][] image, int[][] predicted) {
        int height = image.length;
        int width = image[0].length;
        int[][] differenceArray = HelpMe.deepCopy(image);
        for (int i = 1; i < height; i++)
            for (int j = 1; j < width; j++)
                differenceArray[i][j] = image[i][j] - predicted[i][j];
        return differenceArray;
    }

    private int[][] computeQuantizedArray(int[][] difference) {
        int height = difference.length;
        int width = difference[0].length;
        int[][] qauntizedArray = HelpMe.deepCopy(difference);
        for (int i = 1; i < height; i++)
            for (int j = 1; j < width; j++)
                qauntizedArray[i][j] = Quantizer.getQuantizedCode(difference[i][j]);
        return qauntizedArray;
    }

    @Override
    protected int[][] Compress(int[][] image, int bits) {
        int[][] predicted = computePredictedArray(image);
        int[][] difference = computeDifferenceArray(image, predicted);
        int mx = HelpMe.getMax(difference);
        int mi = HelpMe.getMin(difference);
        Quantizer.buildQuantizedData(mi, mx, bits);
        var temp = computeQuantizedArray(difference);
        return temp;
    }

    private int[][] computeDeQuantizedArray(int[][] quantizedDifference) {
        int height = quantizedDifference.length;
        int width = quantizedDifference[0].length;
        int[][] DeQuantizedArray = HelpMe.deepCopy(quantizedDifference);
        for (int i = 1; i < height; i++)
            for (int j = 1; j < width; j++) {
                if (quantizedDifference[i][j] != Integer.MAX_VALUE)
                    DeQuantizedArray[i][j] = Quantizer.getDeQuantizedCode(quantizedDifference[i][j]);
            }
        return DeQuantizedArray;
    }

    private int[][] computeDecodedImage(int[][] deQuantized) {
        int height = deQuantized.length;
        int width = deQuantized[0].length;
        int[][] decodedArray = HelpMe.deepCopy(deQuantized);
        for (int i = 1; i < height; i++) {
            for (int j = 1; j < width; j++) {
                if (Math.min(decodedArray[i][j - 1], decodedArray[i - 1][j]) >= decodedArray[i - 1][j - 1])
                    decodedArray[i][j] = Math.max(decodedArray[i][j - 1], decodedArray[i - 1][j]) + deQuantized[i][j];
                else if (Math.max(decodedArray[i][j - 1], decodedArray[i - 1][j]) <= decodedArray[i - 1][j - 1])
                    decodedArray[i][j] = Math.min(decodedArray[i][j - 1], decodedArray[i - 1][j]) + deQuantized[i][j];
                else
                    decodedArray[i][j] = decodedArray[i][j - 1] + decodedArray[i - 1][j] - decodedArray[i - 1][j - 1]
                            + deQuantized[i][j];

                if (decodedArray[i][j] > 255)
                    decodedArray[i][j] = 255;

                else if (decodedArray[i][j] < 0)
                    decodedArray[i][j] = 0;
            }
        }
        return decodedArray;
    }

    @Override
    protected int[][] Decompress(int[][] quantizedDifference) {
        int[][] deQuantized = computeDeQuantizedArray(quantizedDifference);
        return computeDecodedImage(deQuantized);
    }
}
