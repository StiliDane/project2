public class Parser {

    // PUBLIC METHODS

    /** Parses the human made math string */
    public static AST parseInfix(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("empty input");
        }

        // parse string into tokens
        String[] tokens = input.trim().split("\\s+");
        ArrayStack<AST> exprStack = ArrayStack.emptyStack();
        ArrayStack<String> opStack = ArrayStack.emptyStack();

        for (String token : tokens) {
            if (isOperator(token)) {
                // while the list isn't empty and the current operation is not inside a parenthesis check for precedence
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

                // add operator to stack
                opStack.push(token);
            } else if (token.equals("(")) {
                // add parenthesis to stack
                opStack.push(token);
            } else if (token.equals(")")) {
                while (!opStack.isEmpty() && !opStack.peek().equals("(")) {
                    // close current parenthesis
                    applyOperator(exprStack, opStack.pop());
                }
                if (opStack.isEmpty()) {
                    throw new IllegalArgumentException("mismatched close paren");
                }
                opStack.pop(); // Discard '('
            } else {
                // assume the number is a double and try to parse it
                try {
                    double num = Double.parseDouble(token);
                    exprStack.push(new NumNode(num));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("invalid token");
                }
            }
        }

        // evaluate the tree and apply operators
        while (!opStack.isEmpty()) {
            String op = opStack.pop();
            if (op.equals("(")) {
                throw new IllegalArgumentException("mismatched open paren");
            }
            applyOperator(exprStack, op);
        }

        // exprStack should end with 1 number exactly if operators match numbers properly
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
            // if it is an operator evaluate it
            if (isOperator(token)) {
                applyOperator(stack, token);
            } else {
                // assume the number is a double and try to parse it
                try {
                    double num = Double.parseDouble(token);
                    stack.push(new NumNode(num));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("invalid token");
                }
            }
        }

        // exprStack should end with 1 number exactly if operators match numbers properly
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

    /** Creates a tree of the given operator to be evaluated */
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
        return switch (op) {
            case "^" -> 3;
            case "*", "/" -> 2;
            default -> 1;
        };
    }

    /** ^ should be right associative while other operations are left associative */
    private static boolean isLeftAssociative(String op) {
        return !op.equals("^");
    }
}