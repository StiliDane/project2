public record BinopNode(String op, AST left, AST right) implements AST {
    /** Evaluates the base level operation of BinopNode */
    @Override
    public double eval() {
        double l = left.eval();
        double r = right.eval();
        return switch (op) {
            case "+" -> l + r;
            case "-" -> l - r;
            case "*" -> l * r;
            case "/" -> l / r;
            case "^" -> Math.pow(l, r);
            default -> throw new IllegalArgumentException("Unknown operator: " + op);
        };
    }
}
