import java.util.ArrayDeque;
import java.util.Deque;

public class InfixToPostfix {

    public static void main(String[] args) {
        InfixToPostfix i2p = new InfixToPostfix();
        String infix;
        if (args.length == 0) {
            infix = "30/((2+1)*(3-2))"; 
        } else {
            infix = args[0];
        }
        String postfix = i2p.toPostfix(infix); 
        System.out.printf("infix %s -> postfix %s%n", infix, postfix);
    }

    /**
     * converts an infix arithmetic expression to postfix 
     */
    public String toPostfix(String infix) {
        if (infix == null) return null;
        int n = infix.length();
        if (n <= 1) {
            return infix;
        }
        Deque<Character> stack = new ArrayDeque<Character>();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < n; i++) {
            char c = infix.charAt(i);
            if (Character.isDigit(c)) {
                sb.append(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') sb.append(stack.pop());
                if (stack.isEmpty()) throw new IllegalArgumentException("invalid infix expression: " + infix);
                stack.pop();
            } else if (isOp(c)) {
                while (!stack.isEmpty() && isOp(stack.peek()) && compareOpPriority(stack.peek(), c) >= 0) {
                    sb.append(stack.pop());
                }
                stack.push(c);
                sb.append(' ');
            }
        }
        while (!stack.isEmpty()) sb.append(stack.pop());
        return sb.toString();
    }

    private int compareOpPriority(char op1, char op2) {
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
            return 1;
        }
        if ((op1 == '-' || op1 == '+') && (op2 == '*' || op2 == '/')) {
            return -1;
        }
        return 0;
    }

    private boolean isOp(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

}
