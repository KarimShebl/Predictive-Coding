import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class BinaryHandler {
    public LinkedList<Boolean> bits;

    public static BinaryHandler sharedBinaryHandler;
    public static int myOffset;

    public static int fitBit(int num) {
        int bitsRequired = 0;

        while (num > 0) {
            num >>= 1;
            bitsRequired++;
        }

        return bitsRequired;
    }

    public static int bitsToInt(List<Boolean> bitList, int start, int steps) {
        int result = 0;
        int pos = bitList.size() - start - 1;
        for (int i = pos, j = 0; i > pos - steps; i--, j++) {
            if (i == -1)
                System.out.println("prob here");
            int num = bitList.get(i) ? 1 : 0;
            num <<= j;
            result |= num;
        }
        return result;
    }

    public static BinaryHandler getSharedHandler() {
        if (sharedBinaryHandler == null) {
            sharedBinaryHandler = new BinaryHandler();
            myOffset = 0;
        }
        return sharedBinaryHandler;
    }

    public BinaryHandler() {
        bits = new LinkedList<>();
    }

    public BinaryHandler(LinkedList<Boolean> x) {
        bits = new LinkedList<>(x);
    }

    public void pushBackBits(long element, int numOfBits) {
        for (int i = numOfBits - 1; i >= 0; i--) {
            boolean bit = ((element >> i) & 1) == 1;
            bits.addLast(bit);
        }
    }

    public void pushFrontBits(long element, int numOfBits) {
        for (int i = 0; i < numOfBits; i++) {
            boolean bit = ((element >> i) & 1) == 1;
            bits.addFirst(bit);
        }
    }

    public LinkedList<Byte> toByteArray() {
        LinkedList<Byte> bytes = new LinkedList<>();
        // LinkedList<Boolean> bits_linked = new LinkedList<>(bits);
        byte byteValue = 0;
        int bitIndex = 0;

        ArrayList<Boolean> bits_linked = new ArrayList<>(bits);

        for (int i = bits_linked.size() - 1; i >= 0; i--) {
            Boolean bit = bits_linked.get(i);

            if (bitIndex >= 8) {
                bytes.add(byteValue);
                byteValue = 0;
                bitIndex = 0;
            }
            byteValue |= (bit ? 1 : 0) << bitIndex;
            bitIndex++;
        }

        if (bitIndex > 0) {
            bytes.add(byteValue);
        }
        Collections.reverse(bytes);
        return bytes;
    }
}
