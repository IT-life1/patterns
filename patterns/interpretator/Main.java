interface Expression {
    int interpret();
}

class NumberExpression implements Expression {
    private int number;

    public NumberExpression(int number) {
        this.number = number;
    }

    public int interpret() {
        return number;
    }
}

class AddExpression implements Expression {
    private Expression left, right;

    public AddExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public int interpret() {
        return left.interpret() + right.interpret();
    }
}

class SubtractExpression implements Expression {
    private Expression left, right;

    public SubtractExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public int interpret() {
        return left.interpret() - right.interpret();
    }
}

public class Main {
    public static void main(String[] args) {
        // (5 + 10) - 2
        Expression expr = new SubtractExpression(
                new AddExpression(
                        new NumberExpression(5),
                        new NumberExpression(10)
                ),
                new NumberExpression(2)
        );

        int result = expr.interpret();
        System.out.println("Result: " + result); // 13
    }
}
