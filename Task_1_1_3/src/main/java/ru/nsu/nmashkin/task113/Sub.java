package ru.nsu.nmashkin.task113;

/**
 * Subtraction.
 */
public class Sub extends Expression {

    /**
     * Set operands.
     *
     * @param e1 operand 1
     * @param e2 operand 2
     */
    public Sub(Expression e1, Expression e2) {
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
        return children[0].eval(vars) - children[1].eval(vars);
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
        System.out.print(" - ");
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
        return new Sub(children[0].derivative(var), children[1].derivative(var));
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

        Expression newExp = new Sub(children[0].simplify(), children[1].simplify());
        Expression[] newExpChildren = newExp.getChildren();
        if (newExpChildren[0].getClass() == Number.class
            && newExpChildren[1].getClass() == Number.class) {
            return new Number(newExpChildren[0].eval("") - newExpChildren[1].eval(""));
        }
        if (newExpChildren[1].getClass() == Number.class
            && newExpChildren[1].eval("") == 0) {
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
