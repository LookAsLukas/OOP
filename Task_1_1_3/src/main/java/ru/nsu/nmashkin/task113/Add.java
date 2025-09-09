package ru.nsu.nmashkin.task113;

public class Add extends Expression {
    public Add(Expression e1, Expression e2) {
        children = new Expression[2];
        children[0] = e1;
        children[1] = e2;
    }

    @Override
    public double eval(String vars) {
        return children[0].eval(vars) + children[1].eval(vars);
    }

    @Override
    public void print() {
        System.out.print("(");
        children[0].print();
        System.out.print(" + ");
        children[1].print();
        System.out.print(")");
    }

    @Override
    public Expression derivative(String var) {
        return new Add(children[0].derivative(var), children[1].derivative(var));
    }
}
