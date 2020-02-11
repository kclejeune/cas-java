/**
 * Represents the base e logarithmic function
 *
 * @author Kennan LeJeune
 */
public class Log extends UnaryOp {
    /**
     * Initializes the operand argument to be taken by a Log function instance
     *
     * @param operand The operand to be taken by the Log instance
     */
    public Log(Function operand) {
        super(operand);
    }

    /**
     * Compute the value of a Log function which contains a variable at a given input value
     *
     * @param input The value at which to evaluate the function
     * @return The value of the Log instance at the value of the input parameter
     */
    @Override
    public double value(double input) {
        return Math.log(getOperand().value(input));
    }

    /**
     * Compute the value of a strictly numeric Log function
     *
     * @return The value of the Log instance
     */
    @Override
    public double value() {
        return Math.log(getOperand().value());
    }

    /**
     * Computes the derivative of a Log function instance
     *
     * @return The Function that is the derivative of a Log function instance
     */
    @Override
    public Function derivative() {
        return new BinaryOp(new BinaryOp(new Number(1), this.getOperand(), BinaryOp.Op.DIV), this.getOperand()
                .derivative(), BinaryOp.Op.MULT);
    }
}
