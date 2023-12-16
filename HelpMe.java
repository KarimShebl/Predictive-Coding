import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class HelpMe {
    public static int[][] deepCopy(int[][] original) {
        int rows = original.length;
        int columns = original[0].length;
        int[][] copy = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                copy[i][j] = original[i][j];
            }
        }
        return copy;
    }

    private static HashMap<Integer, Integer> calculateFrequency(int[][] array) {
        HashMap<Integer, Integer> frequencyMap = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                int number = array[i][j];
                frequencyMap.put(number, frequencyMap.getOrDefault(number, 0) + 1);
            }
        }

        return frequencyMap;
    }
    public static List<Integer> calcExisting(int[][] array) {
        HashMap<Integer, Integer> frequencyMap = calculateFrequency(array);
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i < array.length; i++) {
            for (int j = 1; j < array[i].length; j++) {
                if(!list.contains(array[i][j]) && (double)frequencyMap.get(array[i][j]) / (double)frequencyMap.size() >= 0.02)
                    list.add(array[i][j]);
            }
        }
        list.sort((a, b) -> a.compareTo(b));
        return list;
    }

    public static int getMax(int[][] array)
    {
        int mx = array[1][1];
        for (int i = 1; i < array.length; i++)
            for (int j = 1; j < array.length; j++)
                mx = Math.max(mx, array[i][j]);
        return mx;
    }

    public static int getMin(int[][] array)
    {
        int mi = array[1][1];
        for (int i = 1; i < array.length; i++)
            for (int j = 1; j < array.length; j++)
                mi = Math.min(mi, array[i][j]);
        return mi;
    }
}
