import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Quantizer {
    public static LinkedList<Integer>[] quantizedData;

    public static void buildQuantizedData(int mi, int mx, int bits) {
        int codes = (1 << bits);
        quantizedData = new LinkedList[codes];
        for (int i = 0; i < codes; i++)
            quantizedData[i] = new LinkedList<>();
        int step = (mx - mi + 1) / codes, mod = (mx - mi + 1) % codes;
        int start = mi;
        for (int i = 0; i < codes; i++) {
            for (int j = 0; j < step + Math.min(mod, 1); j++) {
                quantizedData[i].add(start++);
                if (j == step)
                    mod--;
            }
        }
    }

    public static int getQuantizedCode(int val) {
        for (int i = 0; i < quantizedData.length; i++)
            for (int j = 0; j < quantizedData[i].size(); j++)
                if (quantizedData[i].get(j) == val)
                    return i;
        return Integer.MAX_VALUE;// any value because it will never happen
    }

    public static int getDeQuantizedCode(int code) {
        return (quantizedData[code].getLast() + quantizedData[code].getFirst()) / 2;
    }
}
