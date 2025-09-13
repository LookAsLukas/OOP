package ru.nsu.nmashkin.task113;

/**
 * Division.
 */
public class Div extends Expression {

    /**
     * Construct an operator.
     *
     * @param op1 operand 1
     * @param op2 operand 2
     */
    public Div(Expression op1, Expression op2) {
        super(op1, op2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double eval(String vars) {
        if (children[0].eval(vars) == 0 && children[1].eval(vars) == 0) {
            throw new RuntimeException("NaN value has occurred in the process of evaluation");
        }
        return children[0].eval(vars) / children[1].eval(vars);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Expression derivative(String var) {
        return new Div(new Sub(new Mul(children[0].derivative(var), children[1]),
                               new Mul(children[0], children[1].derivative(var))),
                       new Mul(children[1], children[1]));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Expression simplify() {
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
