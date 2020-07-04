/**
 * BIT - Bit Indexed Tree (a.k.a. Fenwick Tree)
 */
import java.util.Arrays;

public class BIT {

    private int[] data;
    private int[] tree;

    public BIT(int[] data) {
        this.data = new int[data.length];
        this.tree = new int[data.length+1];
        for (int i = 0; i < data.length; i++) {
            addDelta(i, data[i]);
        }
    }

    /**
     * i - 0 based index
     */
    public void set(int i, int value) {
        validateIndex(i);
        int delta = value - data[i];
        addDelta(i, delta);
    }

    /**
     * i - 0 based index
     */
    public void addDelta(int i, int delta) {
        validateIndex(i);
        data[i] = data[i] + delta;
        i++; // change to 1 based index
        while (i < tree.length) {
            tree[i] += delta;
            i += Integer.lowestOneBit(i);
        }
    }

    /**
     * i - 0 based index
     */
    public int sum(int i) {
        validateIndex(i);
        i++; // change to 1 based index
        int s = 0;
        while (i > 0) {
            s += tree[i];
            i -= Integer.lowestOneBit(i);
        }
        return s;
    }

    /**
     * i, j - 0 based index
     * i - inclusive
     * j - exclusive
     */
    public int sum(int i, int j) {
        int base = i == 0? 0 : sum(--i);
        return sum(--j) - base;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("data: %s%n", Arrays.toString(data)));
        sb.append("sums: [");
        for (int i = 0; i < data.length; i++) {
            if (i > 0) sb.append(", ");
            sb.append(sum(i));
        }
        sb.append("]").append(System.lineSeparator());
        return sb.toString();
    }

    private void validateIndex(int i) throws ArrayIndexOutOfBoundsException {
        if (i < 0 || i >= data.length) {
            throw new ArrayIndexOutOfBoundsException(
                String.format("Index %d is out of range [%d, %d]",
                    i, 0, data.length-1));
        }
    }

}
