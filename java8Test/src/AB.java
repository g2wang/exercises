public class AB {
    public static void main(String[] args) {
        A a = new A(){};
        B b = new B(){};
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        A.bar();
        B.bar();
    }
}
