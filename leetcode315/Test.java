public class Test {

    public static void main(String[] args) {
        BIT bit = new BIT(new int[]{1, 2, 3, 4});
        System.out.print(bit.toString());
        bit.set(0, 2);
        System.out.print(bit.toString());

        for (int i = 1; i <= 16; i++) {
            System.out.printf("Integer.lowestOneBit(%d)=%d%n", i, Integer.lowestOneBit(i));
        }
    }
}
