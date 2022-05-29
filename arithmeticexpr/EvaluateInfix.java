import java.util.Deque;
import java.util.ArrayDeque;

public class EvaluateInfix {
    public static void main(String[] args) {
        String infix;
        if (args.length == 0) {
            infix = "30/((2+1)*(3-2))"; 
        } else {
            infix = args[0];
        }
        InfixToPostfix ip = new InfixToPostfix();
        EvaluatePostfix ep = new EvaluatePostfix();
        String postfix = ip.toPostfix(infix);
        double result = ep.eval(postfix);
        System.out.printf("infix %s -> %f%n", infix, result);
    }
}
