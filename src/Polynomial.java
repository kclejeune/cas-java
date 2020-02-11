/**
 * Represents a function raised to a power
 *
 * @author Kennan LeJeune
 */
public class Polynomial extends UnaryOp {
    //stores the power to which the operand of the Polynomial instance is raised
    private final double power;

    /**
     * @param operand The function argument to be taken by a Polynomial
     * @param power   The power to which the operand is raised in a Polynomial instance
     */
    public Polynomial(Function operand, double power) {
        super(operand);
        this.power = power;
    }

    /**
     * Compute the value of a Polynomial function composed of variables at a given input value
     *
     * @param input The value at which to evaluate the function
     * @return The value of a Polynomial function at the value of the input parameter
     */
    @Override
    public double value(double input) {

        return Math.pow(getOperand().value(input), this.getPower());
    }

    /**
     * Compute the value of a strictly numeric Polynomial function
     *
     * @return The value of a Polynomial function instance
     */
    @Override
    public double value() {
        return Math.pow(getOperand().value(), this.getPower());
    }

    /**
     * Retrieves the power to which a Polynomial function operand is raised
     *
     * @return The power of the polynomial
     */
    public double getPower() {
        return this.power;
    }

    /**
     * Checks whether a Polynomial instance is equivalent to another Function
     *
     * @param input The function to be compared
     * @return Whether or not a Polynomial instance is equivalent to the input parameter
     */
    @Override
    public boolean equals(Object input) {
        return input instanceof Polynomial && this.getOperand().equals(((Polynomial) input).getOperand()) && this
                .getPower() == ((Polynomial) input).getPower();
    }

    /**
     * Computes the derivative of a Polynomial function
     *
     * @return The function which is the derivative of a Polymomial function
     */
    @Override
    public Function derivative() {
        return new BinaryOp(new BinaryOp(new Number(power), new Polynomial(this.getOperand(), power - 1), BinaryOp.Op
                .MULT), this.getOperand().derivative(), BinaryOp.Op.MULT);
    }

    /**
     * Computes the String representation of a Polynomial function
     *
     * @return The String representation of the Polynomial function
     */
    @Override
    public String toString() {
        if (this.getOperand() instanceof BinaryOp) {
            return "(" + this.getOperand().toString() + ")^" + power;
        } else {
            return this.getOperand().toString() + "^" + power;
        }
    }
}
