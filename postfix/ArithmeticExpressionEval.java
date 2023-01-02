/**
 * eval arithmetic expression with operators +, -, *, /
 */
import java.util.*;

public class ArithmeticExpressionEval {

    public static void main(String[] args) {
        String s = "2+2*3-3/2";
        ArithmeticExpressionEval e = new ArithmeticExpressionEval();
        double ans = e.eval(s);
        System.out.printf("%s = %f%n", s, ans);
    }

    private double eval(String s) {
        String postfix = toPostfix(s);
        return evalPostfix(postfix);
    }

    private String toPostfix(String infix) {
        StringBuilder postfix = new StringBuilder(); 
        StringBuilder v = new StringBuilder();
        char[] a = infix.toCharArray();
        Deque<String> stack = new ArrayDeque<>();
        for (int i = 0; i < a.length; i++) {
            char c = a[i];
            if (Character.isDigit(c)) {
                v.append(c);
            } else if (c == '*' || c == '/') {
                postfix.append(' ').append(v.toString());
                v.delete(0, v.length());
                stack.push(String.valueOf(c));
            } else if (c == '+' || c == '-') {
                postfix.append(' ').append(v.toString());
                v.delete(0, v.length());
                while (!stack.isEmpty()) {
                    postfix.append(' ').append(stack.pop());
                }
                stack.push(String.valueOf(c));
            }
        }
        postfix.append(' ').append(v.toString());
        while (!stack.isEmpty()) {
            postfix.append(' ').append(stack.pop());
        }
        System.out.printf("infix=%s -> postfix=%s%n", infix, postfix.substring(1));
        return postfix.substring(1);
    }

    private double evalPostfix(String postfix) {
        String[] tokens = postfix.split(" ");
        Deque<Double> stack = new ArrayDeque<>();
        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            if (token.equals("*") || token.equals("/") || token.equals("+") || token.equals("-")) {
                double v2 = stack.pop();
                double v1 = stack.pop();
                char op = token.charAt(0);
                switch (op) {
                    case '*':
                        stack.push(v1*v2);
                        break;
                    case '/':
                        stack.push(v1/v2);
                        break;
                    case '+':
                        stack.push(v1+v2);
                        break;
                    case '-':
                        stack.push(v1-v2);
                        break;
                }
            } else {
                stack.push(Double.parseDouble(token));
            }
        }
        return stack.pop();
    }
}
