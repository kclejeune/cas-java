/**
 * Represents a Function which takes two inputs and produces a single output
 * Functions may be added, subtracted, multiplied, and divided
 *
 * @author Kennan LeJeune
 */
public class BinaryOp extends Function {
    //stores the operator to be performed by the BinaryOp (PLUS, SUB, DIV, MULT)
    private final Op operator;

    //stores the function which is the left operand of a binary function
    private Function leftOperand;

    //stores the function which is the right operand of a binary function
    private Function rightOperand;

    /**
     * the Op enum defines the four operators (addition, subtraction, multiplication, division) which may be used
     * in a BinaryOp instance
     */
    enum Op {

        PLUS("+"), //represents the addition operator
        SUB("-"), //represents the subtraction operator
        MULT("*"), //represents the multiplication operator
        DIV("/"); //represents the division operator

        //contains the String representation of the operator
        private final String operator;

        /**
         * initialize the enum types and their string representations
         *
         * @param operator the string representation of a given operator
         */
        Op(String operator) {
            this.operator = operator;
        }

        /**
         * @return the String representation of the Op instance
         */
        @Override
        public String toString() {
            return operator;
        }
    }

    /**
     * Initializes the operands and operator of a Binary Operation
     *
     * @param leftOperand  The left operand of the BinaryOp
     * @param rightOperand The right operand of the BinaryOp
     * @param operator     The operation to be performed by the the BinaryOp
     */
    public BinaryOp(Function leftOperand, Function rightOperand, Op operator) {
        //stores the left operand of the BinaryOp instance
        this.leftOperand = leftOperand;

        //stores the right operand of the BinaryOp instance
        this.rightOperand = rightOperand;
        this.operator = operator;
        simplifyOperation();
    }

    /**
     * Helper method to simplify nested binary operations without compromising the mathematical integrity of the
     * function.  Avoids unnecessary complication of BinaryOp String representations
     * Eliminates unnecessary addition/multiplication of zeros and multiplication/division of ones in functions
     * For example,
     * (1.0 + 0.0) * x becomes 1.0 * x
     * (1.0 * x) + x becomes x + x
     */
    private void simplifyOperation() {
        //if the left operand of this instance is an instance of a BinaryOp
        if (this.getLeftOperand() instanceof BinaryOp) {
            BinaryOp operand = (BinaryOp) this.getLeftOperand();
            Function leftOperand = operand.getLeftOperand();
            Function rightOperand = operand.getRightOperand();
            Number zero = new Number(0);
            Number one = new Number(1);

            //switch with the operator of the left operand of this BinaryOp
            //all logic in switch statement refers to the BinaryOp instance that is the left Operand of the current
            // BinaryOp instance
            switch (operand.getOperator()) {
                case MULT:
                    //if either the left or right operands are zero, simplify the entire expression to zero.
                    if (leftOperand.equals(zero) || rightOperand.equals(zero)) {
                        setLeftOperand(zero);
                        break;
                    }
                    //if the left operand or right operand are 1, simplify the entire expression to the other operand
                    if (leftOperand.equals(one)) {
                        setLeftOperand(rightOperand);
                        break;
                    }
                    if (rightOperand.equals(one)) {
                        setLeftOperand(leftOperand);
                        break;
                    }
                    break;
                case DIV:
                    //if zero is divided by anything, simplify the expression to zero
                    if (leftOperand.equals(zero)) {
                        setLeftOperand(zero);
                        break;
                    }
                    //if anything is divided by one, simplify the entire expression to the left operand
                    if (rightOperand.equals(one)) {
                        setLeftOperand(leftOperand);
                        break;
                    }
                    break;
                case SUB:
                    //if zero is subtracted from anything, simplify to the left operand
                    if (rightOperand.equals(zero)) {
                        setLeftOperand(leftOperand);
                        break;
                    }
                    //if anything is subtracted from zero, simplify to the negative of the right operand
                    if (leftOperand.equals(zero)) {
                        setLeftOperand(new BinaryOp(new Number(-1), rightOperand, Op.MULT));
                        break;
                    }
                    break;
                case PLUS:
                    //if zero is added to either operand, simplify to the other operand
                    if (leftOperand.equals(zero)) {
                        setLeftOperand(rightOperand);
                        break;
                    }
                    if (rightOperand.equals(zero)) {
                        setLeftOperand(leftOperand);
                        break;
                    }
                    break;
            }
        }

        if (this.getRightOperand() instanceof BinaryOp) {
            BinaryOp operand = (BinaryOp) this.getRightOperand();
            Function leftOperand = operand.getLeftOperand();
            Function rightOperand = operand.getRightOperand();
            Number zero = new Number(0);
            Number one = new Number(1);

            switch (operand.getOperator()) {
                case MULT:
                    if (leftOperand.equals(zero) || rightOperand.equals(zero)) {
                        setRightOperand(zero);
                        break;
                    }
                    if (leftOperand.equals(one)) {
                        setRightOperand(rightOperand);
                        break;
                    }
                    if (rightOperand.equals(one)) {
                        setRightOperand(leftOperand);
                        break;
                    }
                    break;
                case DIV:
                    if (leftOperand.equals(zero)) {
                        setRightOperand(zero);
                        break;
                    }
                    if (rightOperand.equals(one)) {
                        setRightOperand(leftOperand);
                        break;
                    }
                    break;
                case SUB:
                    if (rightOperand.equals(zero)) {
                        setRightOperand(leftOperand);
                        break;
                    }
                    if (leftOperand.equals(zero)) {
                        setRightOperand(new BinaryOp(new Number(-1), rightOperand, Op.MULT));
                        break;
                    }
                    break;
                case PLUS:
                    if (leftOperand.equals(zero)) {
                        setRightOperand(rightOperand);
                        break;
                    }
                    if (rightOperand.equals(zero)) {
                        setRightOperand(leftOperand);
                        break;
                    }
                    break;
            }
        }
    }

    /**
     * Retrieves the left operand of a BinaryOp
     *
     * @return The left operand of a BinaryOp instance
     */
    public Function getLeftOperand() {
        return this.leftOperand;
    }

    /**
     * Sets the left operand of the BinaryOp instance equal to the input parameter
     *
     * @param leftOperand the updated left operand for a BinaryOp
     */
    private void setLeftOperand(Function leftOperand) {
        this.leftOperand = leftOperand;
    }

    /**
     * Retrieves the right operand of a BinaryOp
     *
     * @return The right operand of a BinaryOp instance
     */
    public Function getRightOperand() {
        return this.rightOperand;
    }

    /**
     * Sets the right operand of the BinaryOp instance equal to the input parameter
     *
     * @param rightOperand the updated right operand for a BinaryOp
     */
    private void setRightOperand(Function rightOperand) {
        this.rightOperand = rightOperand;
    }

    /**
     * Retrieves the operator of a BinaryOp
     *
     * @return The operator of a BinaryOp instance
     */
    public Op getOperator() {
        return this.operator;
    }

    /**
     * Evaluates a BinaryOp composed of variables at a given input value
     *
     * @param input The value at which to evaluate the BinaryOp
     * @return The value of the BinaryOp at the specified input value
     */
    @Override
    public double value(double input) {
        switch (this.getOperator()) {
            case PLUS:
                return getLeftOperand().value(input) + getRightOperand().value(input);
            case SUB:
                return getLeftOperand().value(input) - getRightOperand().value(input);
            case DIV:
                return getLeftOperand().value(input) / getRightOperand().value(input);
            case MULT:
                return getLeftOperand().value(input) * getRightOperand().value(input);
            default:
                return 0;
        }
    }

    /**
     * Computes the value of a strictly numerical Binary Operation (contains no variables)
     *
     * @return The value of the function
     */
    @Override
    public double value() {
        switch (this.getOperator()) {
            case PLUS:
                return getLeftOperand().value() + getRightOperand().value();
            case SUB:
                return getLeftOperand().value() - getRightOperand().value();
            case DIV:
                return getLeftOperand().value() / getRightOperand().value();
            case MULT:
                return getLeftOperand().value() * getRightOperand().value();
            default:
                return 0;
        }
    }

    /**
     * Determines if a BinaryOp instance is equivalent to an input parameter
     *
     * @param input The function to be compared
     * @return Whether or not the functions are equal
     */
    @Override
    public boolean equals(Object input) {
        switch (this.getOperator()) {
            //for subtraction and division, left and right operands must match exactly
            case SUB:
            case DIV:
                return input instanceof BinaryOp && this.getOperator() ==
                        ((BinaryOp) input).getOperator() && this.getLeftOperand().equals(((BinaryOp) input)
                        .getLeftOperand())
                        && this.getRightOperand().equals(((BinaryOp) input).getRightOperand());
            //for addition and multiplication, left and right operands may be swapped and still be equivalent
            case PLUS:
            case MULT:
                return input instanceof BinaryOp && this.getOperator() ==
                        ((BinaryOp) input).getOperator() && ((this.getLeftOperand().equals(((BinaryOp) input)
                        .getLeftOperand())
                        && this.getRightOperand().equals(((BinaryOp) input).getRightOperand())) || (this
                        .getLeftOperand().equals(((BinaryOp) input).getRightOperand())
                        && this.getRightOperand().equals(((BinaryOp) input).getLeftOperand())));
            default:
                return false;
        }
    }

    /**
     * Computes the derivative of a BinaryOp instance
     * May include any composition of two functions, incorporating the chain, product, and quotient rules
     *
     * @return The function which is the derivative of the current BinaryOp instance
     */
    @Override
    public Function derivative() {
        switch (operator) {
            case PLUS:
                return new BinaryOp(getLeftOperand().derivative(), getRightOperand().derivative(), Op.PLUS);
            case SUB:
                return new BinaryOp(getLeftOperand().derivative(), getRightOperand().derivative(), Op.SUB);
            case MULT:
                return new BinaryOp(new BinaryOp(getLeftOperand().derivative(), getRightOperand(), Op.MULT), new
                        BinaryOp(getRightOperand().derivative(), getLeftOperand(), Op.MULT), Op.PLUS);
            case DIV:
                return new BinaryOp(new BinaryOp(new BinaryOp(getLeftOperand().derivative(), getRightOperand(), Op
                        .MULT), new
                        BinaryOp(getLeftOperand(), getRightOperand().derivative(), Op.MULT), Op.SUB), new Polynomial
                        (this.getRightOperand(), 2), Op.DIV);
            default:
                return null;
        }
    }

    /**
     * Computes the String representation of a BinaryOp instance
     *
     * @return The String representation of the BinaryOp
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        //parentheses around leftOperand if it is a BinaryOp
        if (this.getLeftOperand() instanceof BinaryOp) {
            builder.append('(');
            builder.append(getLeftOperand().toString());
            builder.append(")");
        } else {
            builder.append(this.getLeftOperand().toString());
        }

        builder.append(' ');
        builder.append(this.getOperator().toString());
        builder.append(' ');

        if (this.getRightOperand() instanceof BinaryOp) {
            //if rightOperand and leftOperand are both BinaryOp and they have the same operator, no parentheses around
            //the rightOperand
            if (this.getLeftOperand() instanceof BinaryOp && this.getOperator() == ((BinaryOp) this.getRightOperand())
                    .getOperator()) {
                builder.append(this.getRightOperand().toString());
            } else {
                builder.append('(');
                builder.append(this.getRightOperand().toString());
                builder.append(')');
            }
        } else {
            builder.append(this.getRightOperand().toString());
        }

        return builder.toString();
    }
}
