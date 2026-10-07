public class Parser {

    // PUBLIC METHODS

    /** Parses the human made math string */
    public static AST parseInfix(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("empty input");
        }

        String[] tokens = input.trim().split("\\s+");
        ArrayStack<AST> exprStack = ArrayStack.emptyStack();
        ArrayStack<String> opStack = ArrayStack.emptyStack();

        for (String token : tokens) {
            if (isOperator(token)) {
                while (!opStack.isEmpty() && !opStack.peek().equals("(")) {
                    String topOp = opStack.peek();
                    int precToken = precedence(token);
                    int precTop = precedence(topOp);

                    if (precTop > precToken || (precTop == precToken && isLeftAssociative(token))) {
                        applyOperator(exprStack, opStack.pop());
                    } else {
                        break;
                    }
                }
                opStack.push(token);
            } else if (token.equals("(")) {
                opStack.push(token);
            } else if (token.equals(")")) {
                while (!opStack.isEmpty() && !opStack.peek().equals("(")) {
                    applyOperator(exprStack, opStack.pop());
                }
                if (opStack.isEmpty()) {
                    throw new IllegalArgumentException("mismatched close paren");
                }
                opStack.pop(); // Discard '('
            } else {
                try {
                    double num = Double.parseDouble(token);
                    exprStack.push(new NumNode(num));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("invalid token");
                }
            }
        }

        while (!opStack.isEmpty()) {
            String op = opStack.pop();
            if (op.equals("(")) {
                throw new IllegalArgumentException("mismatched open paren");
            }
            applyOperator(exprStack, op);
        }

        if (exprStack.isEmpty()) {
            throw new IllegalArgumentException("empty input");
        }
        if (exprStack.size() > 1) {
            throw new IllegalArgumentException("too many operands");
        }

        return exprStack.pop();
    }

    /** parses the String from an already structured math string */
    public static AST parsePostfix(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("empty input");
        }

        String[] tokens = input.trim().split("\\s+");
        ArrayStack<AST> stack = ArrayStack.emptyStack();

        // operate on tokens from left to right
        for (String token : tokens) {
            // if it is an operator create a tree for it
            if (isOperator(token)) {
                applyOperator(stack, token);
            } else {
                // if it is a number cast it to a double and add it to the stack
                try {
                    double num = Double.parseDouble(token);
                    stack.push(new NumNode(num));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("invalid token");
                }
            }
        }

        if (stack.size() > 1) {
            throw new IllegalArgumentException("too many operands");
        }

        return stack.pop();
    }

    //===============================================================================
    // PRIVATE METHODS

    /** tests if the given token is an operator */
    private static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*")
                || token.equals("/") || token.equals("^");
    }

    /** Creates a tree of the given operator to be saved for later */
    private static void applyOperator(ArrayStack<AST> stack, String op) {
        if (stack.size() < 2) {
            throw new IllegalArgumentException("insufficient operands");
        }

        // create the tree with the first object being the right and the second object being the left
        AST right = stack.pop();
        AST left = stack.pop();
        stack.push(new BinopNode(op, left, right));
    }

    /** returns the priority of what a value should be */
    private static int precedence(String op) {
        if (op.equals("^")) return 3;
        if (op.equals("*") || op.equals("/")) return 2;
        if (op.equals("+") || op.equals("-")) return 1;
        return 0;
    }

    /** ^ should be right associative while other operations are left associative */
    private static boolean isLeftAssociative(String op) {
        return !op.equals("^");
    }
}