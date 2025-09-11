package ru.nsu.nmashkin.task113;

/**
 * Division.
 */
public class Div extends Expression {

    /**
     * Set operands.
     *
     * @param e1 operand 1
     * @param e2 operand 2
     */
    public Div(Expression e1, Expression e2) {
        children = new Expression[2];
        children[0] = e1;
        children[1] = e2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double eval(String vars) {
        if (children[0] == null || children[1] == null) {
            System.err.println("WARNING: Evaluating expression with null operands "
                               + "results in the value being infinite");
            return Double.POSITIVE_INFINITY;
        }
        if (children[0].eval(vars) == 0 && children[1].eval(vars) == 0) {
            System.err.println("WARNING: Zero by zero division is defined to result in infinity");
            return Double.POSITIVE_INFINITY;
        }
        if (children[1].eval(vars) == 0) {
            System.err.println("WARNING: Zero division has occurred");
        }
        return children[0].eval(vars) / children[1].eval(vars);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void print() {
        System.out.print("(");
        if (children[0] == null) {
            System.out.print("null");
        } else {
            children[0].print();
        }
        System.out.print(" / ");
        if (children[1] == null) {
            System.out.print("null");
        } else {
            children[1].print();
        }
        System.out.print(")");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Expression derivative(String var) {
        if (children[0] == null || children[1] == null) {
            System.err.println("WARNING: Taking derivative of a null");
            return null;
        }
        return new Div(new Sub(new Mul(children[0].derivative(var), children[1]),
                               new Mul(children[0], children[1].derivative(var))),
                       new Mul(children[1], children[1]));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Expression simplify() {
        if (children[0] == null || children[1] == null) {
            System.err.println("WARNING: Simplifying a null");
            return null;
        }

        Expression newExp = new Div(children[0].simplify(), children[1].simplify());
        Expression[] newExpChildren = newExp.getChildren();
        if (newExpChildren[0].getClass() == Number.class
            && newExpChildren[1].getClass() == Number.class) {
            return new Number(newExpChildren[0].eval("") / newExpChildren[1].eval(""));
        }
        if (newExpChildren[0].getClass() == Number.class
            && newExpChildren[0].eval("") == 0) {
            return new Number(0);
        }
        if (newExpChildren[1].getClass() == Number.class
            && newExpChildren[1].eval("") == 1) {
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

        Div o = (Div) obj;
        return children[0].equals(o.children[0])
               && children[1].equals(o.children[1]);
    }
}
