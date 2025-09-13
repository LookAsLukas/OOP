package ru.nsu.nmashkin.task113;

/**
 * Subtraction.
 */
public class Sub extends Expression {

    /**
     * Construct an operator.
     *
     * @param op1 operand 1
     * @param op2 operand 2
     */
    public Sub(Expression op1, Expression op2) {
        super(op1, op2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double eval(String vars) {
        return children[0].eval(vars) - children[1].eval(vars);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Expression derivative(String var) {
        return new Sub(children[0].derivative(var), children[1].derivative(var));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Expression simplify() {
        Expression newExp = new Sub(children[0].simplify(), children[1].simplify());
        Expression[] newExpChildren = newExp.getChildren();
        if (newExpChildren[0].getClass() == Number.class
            && newExpChildren[1].getClass() == Number.class) {
            return new Number(newExpChildren[0].eval("") - newExpChildren[1].eval(""));
        }
        if (newExpChildren[1].equals(new Number(0))) {
            return newExpChildren[0];
        }
        return newExp;
    }

    /**
     * Compare to an object.
     *
     * @param obj   the reference object with which to compare.
     * @return true if equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != getClass()) {
            return false;
        }

        Sub o = (Sub) obj;
        return children[0].equals(o.children[0])
                && children[1].equals(o.children[1]);
    }
}
