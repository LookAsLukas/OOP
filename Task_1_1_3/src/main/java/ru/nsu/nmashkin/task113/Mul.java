package ru.nsu.nmashkin.task113;

/**
 * Multiplication.
 */
public class Mul extends Expression {

    /**
     * {@inheritDoc}
     */
    public Mul(Expression op1, Expression op2) {
        super(op1, op2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double eval(String vars) {
        return children[0].eval(vars) * children[1].eval(vars);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Expression derivative(String var) {
        return new Add(new Mul(children[0].derivative(var), children[1]),
                       new Mul(children[0], children[1].derivative(var)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Expression simplify() {
        Expression newExp = new Mul(children[0].simplify(), children[1].simplify());
        Expression[] newExpChildren = newExp.getChildren();
        if (newExpChildren[0].getClass() == Number.class
            && newExpChildren[1].getClass() == Number.class) {
            return new Number(newExpChildren[0].eval("") * newExpChildren[1].eval(""));
        }
        if (newExpChildren[0].getClass() == Number.class
            && newExpChildren[0].eval("") == 0
            || newExpChildren[1].getClass() == Number.class
            && newExpChildren[1].eval("") == 0) {
            return new Number(0);
        }
        if (newExpChildren[0].getClass() == Number.class
            && newExpChildren[0].eval("") == 1) {
            return newExpChildren[1];
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

        Mul o = (Mul) obj;
        return children[0].equals(o.children[0])
                && children[1].equals(o.children[1]);
    }
}
