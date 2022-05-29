import java.util.Deque;
import java.util.ArrayDeque;

public class EvaluatePostfix {

    public static void main(String[] args) {
        String postfix;
        if (args.length > 0) {
            postfix = args[0];
        } else {
            postfix = "30 2 1+ 3 2-*/";
        }
        EvaluatePostfix ep = new EvaluatePostfix();
        double result = ep.eval(postfix);
        System.out.printf("postfix %s -> %f%n", postfix, result);
    }


    public Double eval(String postfix) {
        if (postfix == null) return null;
        int n = postfix.length();
        Deque<Double> stack = new ArrayDeque<>();
        double num = 0;
        boolean prevIsNum = false;
        for (int i = 0; i < n; i++) {
            char c = postfix.charAt(i);
            if (Character.isDigit(c)) {
                num = 10*num + (c-'0');
                prevIsNum = true;
            } else if (c == ' ') {
                if (prevIsNum) {
                    stack.push(num);
                    // System.out.printf("pushed %f%n", num);
                    num = 0;
                }
                prevIsNum = false;
            } else if (isOp(c)) {
                if (prevIsNum) {
                    stack.push(num);
                    // System.out.printf("pushed %f%n", num);
                    num = 0;
                }
                prevIsNum = false;
                Double right = stack.pop();  
                Double left = stack.pop();  
                // System.out.printf("op: %c, left: %f, right: %f%n", c, left, right);
                if (c == '*') {
                    stack.push(left * right);
                } else if (c == '/') {
                    if (right == 0) throw new IllegalArgumentException("Invalid expression: " + postfix + "; division by 0 error");
                    stack.push(left / right);
                } else if (c == '+') {
                    stack.push(left + right);
                } else if (c == '-') {
                    stack.push(left - right);
                }
            }
        }
        return stack.pop();
    }

    private boolean isOp(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }
}
