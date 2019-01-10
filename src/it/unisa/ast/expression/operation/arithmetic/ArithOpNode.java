package it.unisa.ast.expression.operation.arithmetic;

import it.unisa.ast.expression.operation.OpNode;
import it.unisa.visitor.MyVisitor;

public abstract class ArithOpNode extends OpNode {
    // Method to be visited by a Visitor
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }
}
