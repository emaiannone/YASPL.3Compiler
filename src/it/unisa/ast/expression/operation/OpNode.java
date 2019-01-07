package it.unisa.ast.expression.operation;

import it.unisa.ast.expression.ExpressionNode;
import it.unisa.visitor.MyVisitor;

public abstract class OpNode extends ExpressionNode {
    public OpNode() {
        super(null);
    }

    // Method to be visited by a Visitor
    public Object accept(MyVisitor v) {
        return v.visit(this);
    }
}
