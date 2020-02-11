import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FunctionTest {
    @Test
    public void testEquals() {
        Number zero = new Number(0);
        Number one = new Number(1);
        Variable x = new Variable();
        Polynomial xSquared = new Polynomial(x, 2);

        //check true and false cases of equals() in the Number type
        assertEquals(new Number(0), zero);
        assertNotEquals(new Number(1), zero);

        //check true and false cases of equals() in the Variable type
        assertEquals(new Variable(), x);
        assertNotEquals(new Number(1), x);

        //check true and false cases of equals() in BinaryOp
        BinaryOp xPlusOne = new BinaryOp(x, one, BinaryOp.Op.PLUS);
        assertEquals(new BinaryOp(new Variable(), new Number(1), BinaryOp.Op.PLUS), xPlusOne);
        assertNotEquals(xPlusOne, new BinaryOp(new Variable(), new Variable(), BinaryOp.Op.PLUS));

        //check true and false case where binaryOp has flipped left and right operands but is still equivalent
        // (1 + x) = (x + 1) but (1 / x) != (x / 1)
        BinaryOp oneDivX = new BinaryOp(new Number(1), new Variable(), BinaryOp.Op.DIV);
        assertEquals(new BinaryOp(new Number(1), new Variable(), BinaryOp.Op.PLUS), xPlusOne);
        assertNotEquals(new BinaryOp(new Variable(), new Number(1), BinaryOp.Op.DIV), oneDivX);

        //check equals() from UnaryOP
        //check true and false cases of equals() in a UnaryOp type
        UnaryOp sinX = new Sin(x);
        assertEquals(new Sin(new Variable()), sinX);
        assertNotEquals(new Sin(new Number(1)), sinX);

        //check true and false cases of equals() in Polynomial type since it overrides UnaryOp
        assertEquals(new Polynomial(new Variable(), 2), xSquared);
        assertNotEquals(x, xSquared);

        //check equals() in a composition of functions
        Polynomial xPlusOneSquared = new Polynomial(xPlusOne, 2);
        Log log = new Log(xPlusOneSquared);
        Cos cos = new Cos(log);
        Sin sin = new Sin(cos);
        Exp composite = new Exp(sin);

        assertEquals(new Exp(new Sin(new Cos(new Log(new Polynomial(new BinaryOp(new Variable(), new Number
                (1), BinaryOp.Op.PLUS), 2))))), composite);

        assertNotEquals(new Number(2), composite);
    }

    @Test
    public void testValueWithoutInput() {
        //represents the number Pi
        Number pi = new Number(Math.PI);

        //represents the number 1
        Number one = new Number(1);

        //represents a variable x
        Variable x = new Variable();

        //represents the function x^2
        Polynomial xSquared = new Polynomial(new Variable(), 2);

        //test a number value with no input with .0001 error to account for floating point
        assertEquals(1, one.value(), .0001);

        //test a variable value with no input to verify UnsupportedOperationException is thrown
        try {
            x.value();
            fail("UnsupportedOperationException should be thrown");
        } catch (Exception e) {
            assertTrue(e instanceof UnsupportedOperationException);
        }

        //test a non-numeric UnaryOp with no input
        //this behavior will occur for all non-numeric UnaryOps when calling value()
        UnaryOp sinX = new Sin(x);
        try {
            sinX.value();
            fail("The exception was not thrown.");
        } catch (Exception e) {
            assertTrue(e instanceof UnsupportedOperationException);
        }

        //test numeric outputs of each type:

        //Sin
        Sin sinPi = new Sin(pi);
        assertEquals(0, sinPi.value(), .0001);

        //Cos
        Cos cosPi = new Cos(pi);
        assertEquals(-1, cosPi.value());

        //Exp
        Exp exp0 = new Exp(new Number(0));
        assertEquals(1, exp0.value());

        //Log
        Log log1 = new Log(new Number(1));
        assertEquals(0, log1.value());

        //BinaryOp
        BinaryOp onePlusOne = new BinaryOp(one, one, BinaryOp.Op.PLUS);
        assertEquals(2, onePlusOne.value());

        //composite function
        Cos cos = new Cos(new Number(0));
        Log log = new Log(cos);
        Sin sin = new Sin(log);
        Exp exp = new Exp(sin);
        assertEquals(1, exp.value());
    }

    @Test
    public void testValueWithInput() {
        Number pi = new Number(Math.PI);
        Number one = new Number(1);
        Variable x = new Variable();
        Cos cos = new Cos(x);
        Log log = new Log(cos);
        Sin sin = new Sin(log);

        //test value of a Number at an input value
        assertEquals(1, one.value(123));
        ///this should also equal the value of Number with no input
        assertEquals(one.value(), one.value(123));

        //test value of a Variable at an input value
        assertEquals(1, x.value(1));

        //test Numeric UnaryOp value at an input 0
        //all other UnaryOps will behave in this manner since value() and value(double input) are defined identically
        //for Numbers
        UnaryOp test = new Sin(new Number(0));
        assertEquals(0, test.value(0));

        //test non-numeric sin value at an input 0
        assertEquals(0, new Sin(x).value(0));

        //test non-numeric Cos at an input 0
        assertEquals(1, new Cos(x).value(0));

        //test non-numeric Exp at an input 0
        assertEquals(1, new Exp(x).value(0));

        //test non-numeric Log at an input 0 and 1
        assertEquals(Double.NEGATIVE_INFINITY, new Log(x).value(0));
        assertEquals(0, new Log(x).value(1));
    }

    @Test
    public void testDerivative() {
        //test derivative of a Number
        Number six = new Number(6);
        assertEquals(new Number(0), six.derivative());

        //test derivative of a Variable
        Variable x = new Variable();
        assertEquals(new Number(1), x.derivative());

        //test derivative of a Sin Function
        Sin sin = new Sin(x);
        assertEquals(new BinaryOp(new Cos(x), x.derivative(), BinaryOp.Op.MULT), sin.derivative());

        //test derivative of Cos function
        Cos cos = new Cos(x);
        assertEquals(new BinaryOp(new BinaryOp(new Number(-1), new Sin(x), BinaryOp.Op.MULT), x.derivative(), BinaryOp
                .Op.MULT), cos.derivative());

        //test derivative of a Log function
        Log log = new Log(x);
        assertEquals(new BinaryOp(new BinaryOp(new Number(1), x, BinaryOp.Op.DIV), new Number(1), BinaryOp.Op.MULT),
                log.derivative());

        //test derivative of an Exp function
        Exp exp = new Exp(x);
        assertEquals(new BinaryOp(exp, new Number(1), BinaryOp.Op.MULT), exp.derivative());

        //test derivative of a Polynomial function
        Polynomial poly = new Polynomial(x, 2);
        assertEquals(new BinaryOp(new BinaryOp(new Number(2), new Polynomial(x, 1), BinaryOp.Op.MULT),
                new Number(1), BinaryOp.Op.MULT), poly.derivative());

        //test BinaryOp derivatives including addition, subtraction, product, and quotient rules
        Number one = new Number(1);
        BinaryOp add = new BinaryOp(x, one, BinaryOp.Op.PLUS);
        BinaryOp sub = new BinaryOp(x, one, BinaryOp.Op.SUB);
        BinaryOp product = new BinaryOp(add, sub, BinaryOp.Op.MULT);
        BinaryOp quotient = new BinaryOp(add, sub, BinaryOp.Op.DIV);

        //add
        assertEquals(new BinaryOp(new Number(1), new Number(0), BinaryOp.Op.PLUS), add.derivative());
        //sub
        assertEquals(new BinaryOp(new Number(1), new Number(0), BinaryOp.Op.SUB), sub.derivative());
        //product
        assertEquals(new BinaryOp(new BinaryOp(add.derivative(), sub, BinaryOp.Op.MULT), new BinaryOp(add,
                sub.derivative(), BinaryOp.Op.MULT), BinaryOp.Op.PLUS), product.derivative());
        //quotient
        assertEquals(new BinaryOp(new BinaryOp(new BinaryOp(add.derivative(), sub, BinaryOp.Op.MULT), new BinaryOp(add,
                        sub.derivative(), BinaryOp.Op.MULT), BinaryOp.Op.SUB), new Polynomial(sub, 2), BinaryOp.Op.DIV),
                quotient.derivative());

    }

    @Test
    public void testToString() {
        //test BinaryOp toString() where neither operand is a BinaryOp
        BinaryOp xPlusOne = new BinaryOp(new Variable(), new Number(1), BinaryOp.Op.PLUS);
        assertEquals("x + 1.0", xPlusOne.toString());

        //test BinaryOp toString() where only left operand is a BinaryOp
        BinaryOp xPlusOnePlusOne = new BinaryOp(new BinaryOp(new Variable(), new Number(1.0), BinaryOp.Op.PLUS), new
                Number(1), BinaryOp.Op.PLUS);
        assertEquals("(x + 1.0) + 1.0", xPlusOnePlusOne.toString());

        //test BinaryOp toString() where only the right operand is a BinaryOp
        BinaryOp onePlusOnePlusX = new BinaryOp(new Number(1.0), new BinaryOp(new Number(1.0), new Variable(),
                BinaryOp.Op.PLUS),
                BinaryOp.Op.PLUS);
        assertEquals("1.0 + (1.0 + x)", onePlusOnePlusX.toString());

        //test BinaryOp toString() where both operands are BinaryOp and the operator of this and right Operand are
        // the same
        BinaryOp leftOperand = new BinaryOp(new Variable(), new Number(1.0), BinaryOp.Op.PLUS);
        BinaryOp rightOperand = new BinaryOp(new Variable(), new Number(3.0), BinaryOp.Op.PLUS);
        assertEquals("(x + 1.0) + x + 3.0", new BinaryOp(leftOperand, rightOperand, BinaryOp.Op.PLUS).toString());

        //test BinaryOp toString() where both operands are BinaryOp and the operator of this and rightOperand are
        // different
        assertEquals("(x + 1.0) / (x + 3.0)", new BinaryOp(leftOperand, rightOperand, BinaryOp.Op.DIV).toString());

        //test UnaryOp toString(), all UnaryOps except Polynomial will behave this way
        UnaryOp unaryOp = new Exp(new Variable());
        assertEquals("Exp[x]", unaryOp.toString(), "The output of Exp[x] was not \"Exp[x]\"");

        //test Polynomial toString() since it overrides that which is in UnaryOp
        //test where operand is a UnaryOp (poly1) and BinaryOp (poly2)
        Polynomial poly1 = new Polynomial(new Variable(), 2);
        Polynomial poly2 = new Polynomial(new BinaryOp(new Variable(), new Number(1.0), BinaryOp.Op.PLUS), 2);

        assertEquals("(x + 1.0)^2.0", poly2.toString());
    }

    @Test
    public void testUnaryOp() {
        //create a new UnaryOp equal to Exp[x]
        UnaryOp unaryOp = new Exp(new Variable());

        //test the getFunctionName() method of UnaryOp
        assertEquals("Exp", unaryOp.getFunctionName(), "The function name was not \"Exp\"");

        //test the getOperand() method
        assertEquals(unaryOp.getOperand(), new Variable(), "The operand was not equal to a variable");
    }

    @Test
    public void testBinaryOp() {
        //represents the operation x + 1
        BinaryOp xPlusOne = new BinaryOp(new Variable(), new Number(1), BinaryOp.Op.PLUS);

        //check getOperator() method of BinaryOp
        assertEquals(BinaryOp.Op.PLUS, xPlusOne.getOperator());

        //check getLeftOperand() method of BinaryOp
        assertEquals(xPlusOne.getLeftOperand(), new Variable());

        //check getRightOperand() method of BinaryOp
        assertEquals(xPlusOne.getRightOperand(), new Number(1));

        //check toString() method of Op in BinaryOp
        assertEquals("+", xPlusOne.getOperator().toString());
    }

    @Test
    public void testPolynomial() {
        //represents the function x^2
        Polynomial poly = new Polynomial(new Variable(), 2);

        //test the getPower() method of Polynomial with .0001 error due to floating point
        assertEquals(2, poly.getPower(), .0001);
    }
}
