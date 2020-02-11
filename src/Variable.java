/**
 * Represents a variable x with no specific value
 *
 * @author Kennan LeJeune
 */
public class Variable extends Function {
    /**
     * Computes the string representation of a variable
     *
     * @return The string representation of a Variable instance
     */
    @Override
    public String toString() {
        return "x";
    }

    /**
     * @param input The value at which to evaluate the function
     * @return The value of the Variable at the given input value
     */
    @Override
    public double value(double input) {
        return input;
    }

    /**
     * Attempts to compute the value of a variable with no input parameter
     *
     * @return The value of the variable
     * @throws UnsupportedOperationException If the method is called on a Variable, since variables do not have
     *                                       inherent value
     */
    @Override
    public double value() {
        throw new UnsupportedOperationException();
    }

    /**
     * Compute the derivative of a Variable
     *
     * @return The Function that is the derivative of the Variable` function
     */
    @Override
    public Function derivative() {
        return new Number(1);
    }

    /**
     * Compares whether or not a Variable is equivalent to another input Function
     *
     * @param input the function to be compared
     * @return Whether or not the input function is a variable
     */
    @Override
    public boolean equals(Object input) {
        return input instanceof Variable;
    }
}

